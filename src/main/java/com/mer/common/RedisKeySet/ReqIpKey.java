package com.mer.common.RedisKeySet;

import com.mer.common.Constant.Constant;
import com.mer.framework.Config.Redis.KeySet.BasePrefix;

/**
 * ip key
 */
public class ReqIpKey extends BasePrefix {

    public ReqIpKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 用户请求的链接信息
     */
    public static ReqIpKey  reqIpKey = new ReqIpKey(Constant.redisOutTime_ReqIpKey,"reqIpKey:");
}
