package com.mer.common.RedisKeySet;


import com.mer.common.Constant.Constant;
import com.mer.framework.Config.Redis.KeySet.BasePrefix;

/**
 * 重复提交 key
 */
public class RequestLimitKey extends BasePrefix {

    private RequestLimitKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    /**
     * 用户请求的链接信息
     */
    public static RequestLimitKey requestLimit = new RequestLimitKey(Constant.second_RequestLimit,"UrlLimit:");


}