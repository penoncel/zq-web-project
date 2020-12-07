package com.mer.project.Pojo.Dtree;

import lombok.Data;

/** response返回类*/
@Data
public class DTreeResponse {
    /** 状态码*/
    private int code;
    /** 信息标识*/
    private String msg;
    /** 状态类*/
    private Status status;
    /** 数据*/
    private Object data;

}
