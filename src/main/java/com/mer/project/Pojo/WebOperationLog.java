package com.mer.project.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 系统日志记录
 * </p>
 *
 * @author zhaoqi
 * @since 2020-10-09
 */
@Data
@TableName("web_operation_log")
public class WebOperationLog implements Serializable {
    private static final long serialVersionUID = 1L;
    // 
    private Integer id;
    // 用户名
    private String userName;
    // 硬件设备
    private String device;
    // 设备系统
    private String deviceSys;
    // 设备版本
    private String deviceV;
    // 操作模块
    private String operModule;
    // 操作类型
    private String operType;
    // 操作描述
    private String operMsg;
    // 请求URI
    private String reqUri;
    // 请求ip
    private String reqIp;
    // 操作方法
    private String operMethod;
    // 操作时间
    private String operTimes;
    // 请求参数
    private String reqParameter;
    // 返回结果
    private String respParameter;
    // 结束时间
    private String respTimes;
    // 耗时
    private String takeUpTime;



    // 日志类型1登入,2操作--
    private Integer logType;
    // 日志状态1正常2异常--
    private Integer logStatus;

}
