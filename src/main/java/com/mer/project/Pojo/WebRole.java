package com.mer.project.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author  zhoaqi 15701556037
 * 系统用户角色
 */
@TableName("web_role")
@Data
public class WebRole implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String role;//角色类型
    private String name;//角色名称
    private String add_time;//添加时间
    private String edit_time;//修改时间

    private String note;//说明

}
