package com.mer.project.Dao;

import com.mer.common.Base.Dao.BaseDao;
import com.mer.project.Pojo.WebRole;
import com.mer.project.Pojo.WebUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface WebRoleDao extends BaseDao {

    /**
     * 查看已设置权限
     * */
    @Select("select menu_id from web_role_permission where role_id =#{role_id}")
    List<Map<String,Object>> getRole_CheckPerimssions(@Param("role_id") int role_id);

    /**
     * 查看角色数据
     * */
    @Select("select * from web_role ")
    List<Map<String,Object>> getSysRoleList();

    /**
     * 删除角色权限
     * */
    @Delete("delete from web_role_permission where role_id=#{role_id}")
    void delete_permission(@Param("role_id") int role_id);

    /**
     * 添加角色权限
     * */
    void insert_bath_permission(List<Map<String, Object>> list);

    /**
     * 查询菜单
     * */
    @Select(" select id ,menu_name,parents as pid,css,href,lever,type from web_menu where belogin=1 order by id asc")
    List<Map<String,Object>> getTree();

    /**
     * 统计
     * */
    @Select("select count(*) from web_role where role=#{role}")
    int getCountString_Role(@Param("role") String role);

    /**
     * 统计
     * */
    @Select("select count(*) from web_role where id=#{id}")
    int getCount_Role(@Param("id") int id);

    /**
     * 查看单个
     * */
    @Select("select * from web_role where id=#{id}")
    WebRole getOne_Role(@Param("id") int id);

    /**
     * 修改
     * */
    @Delete("delete from web_role where id=#{id}")
    void delete_Role(@Param("id") int id);

    /**
     * 修改
     * */
    @Update("update web_role set name=#{name},edit_time=sysdate(),note=#{note} where id=#{id}")
    void update_Role(WebRole webRole);

    /**
     * 添加
     * */
    @Insert("insert into web_role (role,name,add_time,edit_time,note)values(#{role},#{name},sysdate(),sysdate(),#{note})")
    void insert_Role(WebRole webRole);

    /**
     * 角色 权限
     * **/
    @Select("  SELECT wm.href   " +
            "  FROM web_menu wm  " +
            "  LEFT JOIN web_role_permission wrp ON wm.id = wrp.menu_id      " +
            "  WHERE wrp.role_id = #{role_id} ")
    Set<String> fingRolePsermission(@Param("role_id") int role_id);

    /**
     * 角色 菜单权限
     * */
    @Select(" SELECT wm.* " +
            "  FROM web_menu wm " +
            "  LEFT JOIN web_role_permission wrp ON wm.id = wrp.menu_id " +
            "  WHERE wrp.`role_id`=  #{role_id}  AND wm.type !=2  ORDER BY wm.lowerlevel ASC ")
    List<Map<String,Object>> findRoleMenus(@Param("role_id") int role_id);

    /**
     * 获得所有 角色
     * @return
     */
    @Select("select * from web_role")
    List<WebRole> getAllRole();
}
