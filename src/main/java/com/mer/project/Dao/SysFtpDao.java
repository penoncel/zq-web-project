package com.mer.project.Dao;

import com.mer.common.Base.Dao.BaseDao;
import com.mer.project.Pojo.SysFtp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysFtpDao extends BaseDao {

    /**
     * 获取用户对象
     * **/
    @Select("select * from sys_ftp where id=#{id}")
    SysFtp getOne(@Param("id") Integer id);




    /**
     * 添加诺漫斯交易
     * @param parameter
     */
    void insert_nuomansitrad(Object parameter);







}
