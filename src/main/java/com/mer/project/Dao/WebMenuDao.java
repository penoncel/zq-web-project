package com.mer.project.Dao;

import com.mer.common.Base.Dao.BaseDao;
import com.mer.project.Pojo.WebMenu;
import com.mer.project.Pojo.WebUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface WebMenuDao extends BaseDao {
    /**
     * 获取最大的 下级编号
     * **/
    @Select("select max(lowerlevel) lowerlevel from web_menu web_menu where belogin=1 and superior=#{superior}")
    String getMax_Lowerlevel(@Param("superior") String superior);

    /**
     * 获取最大的 上级编号
     * */
    @Select("select if( max(superior) is null,'00000000',max(superior)) superior from web_menu web_menu where belogin=1")
    String getMax_Superior();

    /**
     * 获取用户菜单权限
     * */
    @Select("select wm.* " +
            "from web_menu wm  " +
            "left join web_role_permission wrp on wm.id = wrp.menu_id " +
            "left join (select id from web_role where id=(select lever from web_user where username=#{username})) tmp on wrp.role_id = tmp.id " +
            "where wrp.role_id = tmp.id and wm.type !=2 order by wm.lowerlevel ")
    List<Map<String,Object>> getUserMenu(WebUser webUser);

    /**
     * 添加菜单
     * */
    @Insert(" insert into web_menu(menu_name,parents,css,href,lever,note,type,superior,lowerlevel,belogin)values (#{menu_name},#{parents},#{css},#{href},#{lever},#{note},#{type},#{superior},#{lowerlevel},1)")
    int insert(WebMenu webMenu);

    /**
     * 修改菜单
     * */
    @Update("update web_menu set menu_name=#{menu_name},parents=#{parents},css=#{css},href=#{href},lever=#{lever},note=#{note},superior=#{superior},lowerlevel=#{lowerlevel},type=#{type} where id=#{id}")
    int update(WebMenu webMenu);

    /**
     * 删除菜单
     * */
    @Delete(" delete from web_menu where id=#{id}")
    int delete(@Param("id") int id);


    /**
     * 查询菜单
     * */
    @Select("select id,  parents as pid, menu_name,css,href,lever,note,type from web_menu where belogin=1 order by id asc")
    List<Map<String,Object>> getList();

    /**
     * 查询菜单
     * */
    @Select(" select id ,menu_name,parents as pid,css,href,lever,type from web_menu where type !=2 and belogin=1 order by id asc")
    List<Map<String,Object>> getTree();

    /**
     * 单个
     * */
    @Select("select * from web_menu where id=#{id}")
    WebMenu getOneInt(@Param("id") int id);

    /**
     * 数量
     * */
    @Select("select COUNT(*) from  web_menu where parents =#{id} and belogin=1")
    int getNumber(@Param("id") int id);



}
