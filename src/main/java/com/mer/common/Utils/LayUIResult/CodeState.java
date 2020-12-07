package com.mer.common.Utils.LayUIResult;


/**
 * 接口异常信息 枚举类
 * @author zhaoqi
 * @date 2020/5/20 17:20
 */
public enum CodeState {
    TableSuccess("0","获取数据"),
    Success("200","操作成功"),
    Error("500","操作失败"),

    // 服务器内部错误
    REQUEST_LIMIT("9001", "访问过于频繁，请稍后再试。")
    ;


    private String code;
    private String msg;

    CodeState(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
