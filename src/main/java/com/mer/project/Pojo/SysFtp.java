package com.mer.project.Pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoaqi 15701556037
 */
@TableName("sys_ftp")
@Data
public class SysFtp implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer Id;

    private String no;

    private String ip;

    private Integer port;

    private String userName;

    private String userPwd;

    private String ftp_filepath;

    private String ftp_filename;

    private String downloadpath;

    private String dateformat;

    private String suffix;

    private Integer date_sub;


}
