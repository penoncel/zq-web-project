package com.mer.common.RedisKeySet;

import com.mer.common.Constant.Constant;
import com.mer.framework.Config.Redis.KeySet.BasePrefix;

/**
 * 角色权限 key
 */
public class RoleKey extends BasePrefix {

    public RoleKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 角色 权限  key
     */
    public static RoleKey roleKey = new RoleKey(-1,"roleKey:");

}
