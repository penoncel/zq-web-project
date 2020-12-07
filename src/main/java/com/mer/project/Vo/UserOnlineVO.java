package com.mer.project.Vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserOnlineVO {
    private String user_name;
    private String user_id;
    private String host;
    private String sessiond;
    private Date startAccessTime;
    private Date lastAccessTime;
    private boolean expired;

}
