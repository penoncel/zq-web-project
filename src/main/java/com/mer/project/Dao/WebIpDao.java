package com.mer.project.Dao;

import com.mer.common.Base.Dao.BaseDao;
import com.mer.project.Pojo.WebIp;
import com.mer.project.Pojo.WebPage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * ip信息 Mapper 接口
 * </p>
 *
 * @author zhaoqi
 * @since 2020-10-12
 */
@Mapper
public interface WebIpDao extends BaseDao{

    /**
    * 查询单个 ip信息
    **/
    @Select("select * from web_ip where id =#{id}")
    WebIp getOne(@Param("id") int id);

    /**
    * 添加单个 ip信息
    **/
    @Insert("insert into web_ip( ip, add_time, up_time, status, note, type )values( #{ip}, #{addTime}, #{upTime}, #{status}, #{note}, #{type} )")
    int insertOne(WebIp webIp);

    /**
    * 修改单个 ip信息
    **/
    @Update("update web_ip set ip=#{ip},  up_time=#{upTime}, status=#{status},  note=#{note}, type=#{type}  where id=#{id}")
    int updateOne(WebIp webIp);

    /**
    * 删除单个 ip信息
    **/
    @Delete("delete from web_ip where id=#{id}")
    int deleteOne(@Param("id") int id);

    /**
     *查看ip数量
     * **/
    @Select("select count(*) from web_ip where ip = #{ip}")
    int findCountip(@Param("ip") String ip);

    /**
     * 获取所有 ip 信息
     * @return
     */
    @Select(" select * from web_ip order by id desc ")
    List<WebIp> getList();

}

