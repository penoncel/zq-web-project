package com.mer.project.Dao;

import com.mer.common.Base.Dao.BaseDao;
import com.mer.project.Pojo.WebUser;
import org.apache.ibatis.annotations.*;

import java.util.Map;
import java.util.Set;


/**
 * @author zhoaqi 15701556037
 */
@Mapper
public interface WebUserDao extends BaseDao {

    /**
     * 修改用户密码
     * **/
    @Update("update web_user set userpass=#{userpass},status=1,login_errors=0  where id=#{id}")
    void updateUserPwd(WebUser webUser);

    /**
     *查看用户数量
     * **/
    @Select("select count(*) from web_user where username = #{username}")
    int findCountUser(@Param("username") String username);

    /**
     * 添加用户信息
     * **/
    @Insert("insert into web_user(username,userpass,nickname,phone,lever,creat_time,status) VALUES (#{username},#{userpass},#{nickname},#{phone},#{lever},sysdate(),#{status})")
    int insert_one_user(WebUser webUser);

    /**
     * 修改用户信息
     * **/
    @Update("update web_user set nickname=#{nickname},phone=#{phone},lever=#{lever},status=1,login_errors=0 where id=#{id}")
    int update_one_user(WebUser webUser);

    /**
     * 修改状态
     * */
    @Update(" update web_user set status =#{status} where id =#{id}")
    int updateStatus(@Param("status") String status, @Param("id") String id);

    /***
     * 修改状态
     * */
    @Update("update web_user set status =#{user_status} where agent_num =#{user_agent_num}")
    int updateStatusToMap(Map map);

    /**
     * 删除用户信息
     * **/
    @Delete("delete from web_user where id=#{id}")
    int delete_one_user(@Param("id") int id);

    /**
     * 根据用户名查看用户对象
     * */
    @Select("select * from web_user where username = #{username}")
    WebUser findByUsername(@Param("username") String username);

    /**
     * 根据用户id查看用户对象
     * */
    @Select("select * from web_user where id=#{userId}")
    WebUser findById(@Param("userId") int id);


    /**
     * 认证时密码错误,修改错误次数
     * **/
    void update_login_errors(WebUser webUser);

    /**
     * 认证成功后,重置登入错误次数为0
     * **/
    @Update("update web_user set login_errors=0 where username = #{username}")
    void reset_login_errors(@Param("username") String username);

    /**
     * 查看用户角色
     * */
    @Select("select role from web_role where id=(select lever as roleid from web_user where username=#{username})")
    Set<String> fingUserRole(@Param("username") String username);

    /**
     * 获取用户按钮权限
     * **/
    @Select("select wm.href  " +
            "from web_menu wm  " +
            "left join web_role_permission wrp on wm.id = wrp.menu_id " +
            "left join (select id from web_role where id=(select lever from web_user where username=#{username})) tmp on wrp.role_id = tmp.id " +
            "where wrp.role_id = tmp.id ")
    Set<String> fingUserpsermission(@Param("username") String username);

}
