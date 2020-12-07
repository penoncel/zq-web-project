package com.mer.project.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author  zhoaqi 15701556037
 * 系统用户信息
 */
@TableName("web_user")
@Data
public class WebUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String userpass;
    private String salt;
    private String nickname;
    private String phone;
    private String lever;
    private Integer status;
    private String creat_time;
    private Integer login_errors;
    private String agent_num;
    private Integer blog_form;

    private Integer lockStatus;//锁屏状态 0 未锁   1已锁
}
