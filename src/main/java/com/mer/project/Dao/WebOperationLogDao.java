package com.mer.project.Dao;

import com.mer.common.Base.Dao.BaseDao;
import com.mer.project.Pojo.WebOperationLog;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志记录 Mapper 接口
 * </p>
 *
 * @author zhaoqi
 * @since 2020-10-09
 */
@Mapper
public interface WebOperationLogDao extends BaseDao{

    /**
    * 查询单个 系统日志记录
    **/
//    @Select("select * from web_operation_log where id =#{id}")
    WebOperationLog getOne(@Param("id") int id);

    /**
    * 添加单个 系统日志记录
    **/
    @Insert("insert into web_operation_log" +
            "(`user_name`,`device`,`device_sys`,`device_v`,`oper_module`,`oper_type`,`oper_msg`,`req_uri`,`req_ip`,`oper_method`,`oper_times`,`req_parameter`,`resp_parameter`,`resp_times`,`take_up_time`,`log_type`,`log_status`)values" +
            "(#{userName}, #{device}, #{deviceSys}, #{deviceV}, #{operModule}, #{operType}, #{operMsg}, #{reqUri}, #{reqIp}, #{operMethod}, #{operTimes}, #{reqParameter}, #{respParameter}, #{respTimes}, #{takeUpTime}, #{logType}, #{logStatus} )")
    int insertOne(WebOperationLog webOperationLog);

    /**
    * 修改单个 系统日志记录
    **/
    @Update("update web_operation_log set id=#{id}, userName=#{userName}, device=#{device}, deviceSys=#{deviceSys}, deviceV=#{deviceV}, operModule=#{operModule}, operType=#{operType}, operMsg=#{operMsg}, reqUri=#{reqUri}, reqIp=#{reqIp}, operMethod=#{operMethod}, operTimes=#{operTimes}, reqParameter=#{reqParameter}, respParameter=#{respParameter}, respTimes=#{respTimes}, takeUpTime=#{takeUpTime}, logType=#{logType}, logStatus=#{logStatus}  where id=#{id}")
    int updateOne(WebOperationLog webOperationLog);

    /**
    * 删除单个 系统日志记录
    **/
    @Delete("delete from web_operation_log where id=#{id}")
    int deleteOne(@Param("id") int id);

}

