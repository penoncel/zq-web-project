package com.mer.project.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mer.common.RedisKeySet.LoginUserInfoKey;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.framework.Config.OnleSession.OnleUser;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.project.Dao.WebUserDao;
import com.mer.project.Pojo.WebPage;
import com.mer.project.Pojo.WebUser;
import com.mer.project.Service.WebUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhoaqi 15701556037
 */
@Service
@Transactional
@SuppressWarnings("all")
public class WebUserServiceImpl implements WebUserService {
    @Autowired
     WebUserDao webUserDao;

    @Autowired
    OnleUser onleUser;

    @Autowired
     RedisService redisService;

    @Override
    public void updateUserPwd(WebUser webUser) {
        webUserDao.updateUserPwd(webUser);
    }

    @Override
    public Set<String> fingUserRole(String username) {
        return webUserDao.fingUserRole(username);
    }

    @Override
    public Set<String> fingUserpsermission(String username) {
        return webUserDao.fingUserpsermission(username);
    }

    @Override
    public int findCountUser(String username) {
        return webUserDao.findCountUser(username);
    }

    @Override
    public Result delete_one_user(int id) {
        WebUser webUser = this.findById(id);
        if(!Objects.isNull(webUser)){
            boolean flage = onleUser.deleteOnleUser(webUser.getUsername());
            if(flage){
                redisService.delete(LoginUserInfoKey.UserInfo,webUser.getUsername());
                webUserDao.delete_one_user(id);
                return Result.success();
            }else {
                return Result.error("无法强制下线操作,请联系管理员");
            }
        }
        return Result.error("用户不存在无法删除");
    }

    @Override
    public Result updateStatus(String string) {
        String[] str = string.split(",");
        WebUser webUser = this.findById(Integer.valueOf(str[0]));
        webUser.setStatus(Boolean.parseBoolean(str[2])==true?1:2);

        if(webUser.getStatus()==2){
            boolean flage = onleUser.deleteOnleUser(webUser.getUsername());
            if(!flage){
                return Result.error("无法强制下线操作,请联系管理员");
            }
        }
        redisService.delete(LoginUserInfoKey.UserInfo,webUser.getUsername());
        redisService.set(LoginUserInfoKey.UserInfo,webUser.getUsername(),webUser);
        webUserDao.updateStatus(webUser.getStatus()+"",webUser.getId()+"");
        return Result.success();
    }

    @Override
    public int insert_one_user(WebUser webUser) {

        //散列模式，2次md5 可加盐做混淆
        webUser.setUserpass(String.valueOf(new SimpleHash("md5", "sand123456", null, 2)));
        return webUserDao.insert_one_user(webUser);
    }

    @Override
    public int update_one_user(WebUser webUser) {
        return webUserDao.update_one_user(webUser);
    }

    @Override
    public WebUser findById(int id) {
        return webUserDao.findById(id);
    }

    @Override
    public Map<String, Object> getPage(WebPage webPage) {
        //PageHelper分页插件使用 页码,每页显示数量
        PageHelper.startPage(webPage.getPage(), webPage.getLimit());
        //得到所有数据
        List<Map<String,Object>> listDATA  = webUserDao.getPageList(webPage);
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

    @Override
    public WebUser findByUsername(String username) {
        return webUserDao.findByUsername(username);
    }

    @Override
    public void update_login_errors(String username) {
        webUserDao.update_login_errors(webUserDao.findByUsername(username));
    }

    @Override
    public void reset_login_errors(String username) {
        webUserDao.reset_login_errors(username);
    }

}
