package com.mer.project.Service;


import com.mer.common.Base.Service.BaseService;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.project.Pojo.WebUser;

import java.util.Set;

/**
 * @author zhoaqi 15701556037
 */
public interface WebUserService extends BaseService {

    /**
     * 修改用户密码
     * **/
    void updateUserPwd(WebUser webUser);
    /**
     * 用户信息
     * **/
    WebUser findById(int id);
    WebUser findByUsername(String username);

    /**
     * 认证时密码错误,修改错误次数
     * **/
    void update_login_errors(String username);

    /**
     * 认证成功后,重置登入错误次数为0
     * **/
    void reset_login_errors(String username);

    /**
     * 添加用户信息
     * **/
    int insert_one_user(WebUser webUser);

    /**
     * 修改用户信息
     * **/
    int update_one_user(WebUser webUser);

    /**
     * 修改状态
     * */
    Result updateStatus(String string);

    /**
     * 删除用户信息
     * **/
    Result delete_one_user(int id);

    /**
     *查看用户数量
     * **/
    int findCountUser(String username);

    /**
     * 查看用户角色
     * */
    Set<String> fingUserRole(String username);

    /**
     * 查看用户按钮权限
     * **/
    Set<String> fingUserpsermission(String username);
}
