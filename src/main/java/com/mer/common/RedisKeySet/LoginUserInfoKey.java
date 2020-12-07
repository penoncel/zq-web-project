package com.mer.common.RedisKeySet;

import com.mer.common.Constant.Constant;
import com.mer.framework.Config.Redis.KeySet.BasePrefix;

/**
 * 用户信息 key
 */
public class LoginUserInfoKey extends BasePrefix {

    public LoginUserInfoKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 用户 基本 信息 key
     */
    public static LoginUserInfoKey UserInfo = new LoginUserInfoKey(Constant.loginUserMsg_timeOut,"UserInfo:");

}
