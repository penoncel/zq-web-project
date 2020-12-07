package com.mer.project.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * ip信息
 * </p>
 *
 * @author zhaoqi
 * @since 2020-10-12
 */
@Data
@TableName("web_ip")
public class WebIp implements Serializable {
    private static final long serialVersionUID = 1L;
    // 
    private Integer id;
    // ip地址
    private String ip;
    // 添加时间
    private String addTime;
    // 修改时间
    private String upTime;
    // 状态1允许2拒绝
    private Integer status;
    // 说明
    private String note;
    // 类型1白名单2黑名单
    private Integer type;


    //
    private String old_ip;
}
