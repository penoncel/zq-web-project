package com.mer.project.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mer.common.RedisKeySet.RoleKey;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.project.Dao.WebRoleDao;
import com.mer.project.Dao.WebUserDao;
import com.mer.project.Domain.RoleMain;
import com.mer.project.Pojo.WebPage;
import com.mer.project.Pojo.WebRole;
import com.mer.project.Service.WebRoleService;
import com.mer.project.Service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@Transactional
@SuppressWarnings("all")
public class WebRoleServiceImpl implements WebRoleService {

    @Autowired
    private WebRoleDao webRoleDao;

    @Autowired
    private WebUserDao webUserDao;

    @Autowired
    private RedisService redisService;

    @Override
    public List<Map<String, Object>> getRole_CheckPerimssions(int role_id) {
        return webRoleDao.getRole_CheckPerimssions(role_id);
    }

    @Override
    public List<Map<String, Object>> getSysRoleList() {
        return webRoleDao.getSysRoleList();
    }

    @Override
    public void setPermission(int role_id, List<Map<String,Object>> list) {
        webRoleDao.delete_permission(role_id);
        webRoleDao.insert_bath_permission(list);

        String roleId = role_id+"";
        if(redisService.exists(RoleKey.roleKey,roleId)){
            redisService.delete(RoleKey.roleKey,roleId);
        }
        RoleMain roleMain = new RoleMain();
        roleMain.setRolePsermissions( webRoleDao.fingRolePsermission(role_id));
        roleMain.setRoleMenus(webRoleDao.findRoleMenus(role_id));
        redisService.set(RoleKey.roleKey,roleId,roleMain);
    }

    @Override
    public List<Map<String, Object>> getTree() {
        return webRoleDao.getTree();
    }

    @Override
    public int getCountString_Role(String role) {
        return webRoleDao.getCountString_Role(role);
    }

    @Override
    public int getCount_Role(int id) {
        return webRoleDao.getCount_Role(id);
    }

    @Override
    public WebRole getOne_Role(int id) {
      return webRoleDao.getOne_Role(id);
    }

    @Override
    public void delete_Role(int id) {
        webRoleDao.delete_permission(id);
        webRoleDao.delete_Role(id);
        redisService.delete(RoleKey.roleKey,String.valueOf(id));
    }

    @Override
    public void update_Role(WebRole webRole) {
        webRoleDao.update_Role(webRole);
    }

    @Override
    public void insert_Role(WebRole webRole) {
        webRoleDao.insert_Role(webRole);
    }

    @Override
    public Map<String, Object> getPage(WebPage webPage) {
        //PageHelper分页插件使用 页码,每页显示数量
        PageHelper.startPage(webPage.getPage(), webPage.getLimit());
        //得到所有数据
        List<Map<String,Object>> listDATA  = webRoleDao.getPageList(webPage);
        //将数据库查出的值扔到PageInfo里实现分页效果,传入Pagehelper提供的类获取参数信息
        PageInfo<Map<String, Object>> pageInfo = new PageInfo(listDATA);
        //将结果展示到map里
        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("code", "0");
        jsonMap.put("msg", "SUCCESS");
        jsonMap.put("data", listDATA);//数据结果
        jsonMap.put("count", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }
}
