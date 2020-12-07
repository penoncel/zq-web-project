package com.mer.common.RedisKeySet;

import com.mer.common.Constant.Constant;
import com.mer.framework.Config.Redis.KeySet.BasePrefix;

/**
 * Session key
 */
public class RedisSessionKey extends BasePrefix {

    public RedisSessionKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


    /**
     * session code
     */
    public static RedisSessionKey sessionCode = new RedisSessionKey(Constant.LOGIN_VALIDATE_CODE_Expire,Constant.LOGIN_VALIDATE_CODE);

}
