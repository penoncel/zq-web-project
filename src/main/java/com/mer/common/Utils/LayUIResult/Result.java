package com.mer.common.Utils.LayUIResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoqi
 * @date 2020/5/20 17:20
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    private static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    private static final String DATA_TAG = "data";

    /**
     * 总条数
     */
    private static final String COUNT = "count";


    /**
     * 初始化一个新创建的 Result 对象，使其表示一个空消息。
     */
    public Result() {
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public Result(String code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public Result(String code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 初始化
     * @param code 状态码
     * @param msg  返回内容
     * @param count  其他参数
     * @param data 数据对象
     */
    public Result(String code, String msg, Object count,Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (count != null) {
            super.put(COUNT, count);
        }
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }







    /**
     * 返回成功消息
     * @return 成功消息
     */
    public static Result success() {
        return Result.success(CodeState.Success);
    }

    /**
     * 返回成功消息
     * @param errorState  息枚举类
     * @return 成功消息
     */
    public static Result success(CodeState errorState) {
        return Result.success(errorState.getCode(), errorState.getMsg());
    }

    /**
     * 自定义成功消息 和响应码
     * @param code
     * @param msg
     * @return
     */
    public static Result success(String code,String msg) {
        return new Result(code, msg, null);
    }

    /**
     * 自定义成功消息
     * @param msg 返回内容
     * @return 成功消息
     */
    public static Result success(String msg) {
        return Result.success(CodeState.Success.getCode(), msg);
    }

    /**
     * 返回成功消息
     * @param data 数据对象
     * @return 成功消息
     */
    public static Result success(Object data) {
        return new Result(CodeState.Success.getCode(), CodeState.Success.getMsg(), data);
    }

    /**
     * 返回数据模板
     * @param map
     * @return
     */
    public static Result success(Map<String,Object> map) {
        return new Result(CodeState.TableSuccess.getCode(), CodeState.TableSuccess.getMsg(), map.get("count"),map.get("data"));
    }

    public static Result success(List<Map<String,Object>> list) {
        return new Result(CodeState.TableSuccess.getCode(), CodeState.TableSuccess.getMsg(), 0,list);
    }


    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Result error() {
        return Result.error(CodeState.Error.getCode(), CodeState.Error.getMsg());
    }

    /**
     * 自定义错误信息返回
     * @param msg
     * @return
     */
    public static Result error(String msg) {
        return new Result(CodeState.Error.getCode(), msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Result error(String msg, Object data) {
        return new Result(CodeState.Error.getCode(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param errorState  错误信息枚举类
     * @return 警告消息
     */
    public static Result error(CodeState errorState) {
        return Result.error(errorState.getCode(), errorState.getMsg());
    }

    /**
     * 返回错误消息
     *
     * @param errorState  错误信息枚举类
     * @param data  返回内容
     * @return 警告消息
     */
    public static Result error(CodeState errorState, Object data) {
        return new Result(errorState.getCode(), errorState.getMsg(), data);
    }
}
