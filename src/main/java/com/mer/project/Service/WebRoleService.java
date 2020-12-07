package com.mer.project.Service;

import com.mer.common.Base.Service.BaseService;
import com.mer.project.Pojo.WebRole;

import java.util.List;
import java.util.Map;

public interface WebRoleService extends BaseService {

    /**
     * 查看已设置权限
     * */
    List<Map<String,Object>> getRole_CheckPerimssions(int role_id);

    /**
     * 查看角色数据
     * */
    List<Map<String,Object>> getSysRoleList();

    /**
     * 设置权限
     * */
    void setPermission(int user_id, List<Map<String, Object>> list);

    /**
     * 查询菜单
     * */
    List<Map<String,Object>> getTree();

    /**
     * 统计
     * */
    int getCountString_Role(String role);

    /**
     * 统计
     * */
    int getCount_Role(int id);
    /**
    * 查看单个
    * */
    WebRole getOne_Role(int id);

   /**
     * 修改
     * */
   void delete_Role(int id);

    /**
     * 修改
     * */
    void update_Role(WebRole webRole);

    /**
     * 添加
     * */
    void insert_Role(WebRole webRole);
}
