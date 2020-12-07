package com.mer.common.Constant;

/**
 * 常量类
 * @author zhaoqi
 * @date 2020/5/20 17:20
 */
public class Constant {

    /**
     * 链接 防刷
     * 限制每个用户每个api 在 second 秒内，最大只能请求 maxCount 次
     */
    public static final int second_RequestLimit   = 60;
    public static final int maxCount_RequestLimit = 20;

    /***
     *  ip 缓存
     */
    public static final int redisOutTime_ReqIpKey =-1;// 永不过期


    /**
     * 登录验证码SessionKey
     */
    public static  String LOGIN_VALIDATE_CODE = "sessionCode";
    public static  int LOGIN_VALIDATE_CODE_Expire = 60 * 3;//3 分钟过期



    /**
     *  用户信息 过期时间
     *  用户菜单 过期时间
     *  用户权限 过期时间
     *  public static final int TOKEN_EXPIRE = 3600*24 * 2;
     */
    public static final int loginUserMsg_timeOut = 60 * 60 * 60 * 24 * 7;// 信息存 7 天


    /**
     * 盐
     */
    public static final String salt ="c46d05c7a12249cda67e20232ba2b459";


    /**
     * 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
     */
    public static final boolean KickoutAfter = false;
    /**
     * 同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
     */
    public static final int MaxSession = 1;
    /**
     * 被踢出后重定向到的地址；
     */
    public static final String KickoutUrl = "/login?kickout=1";


    /**
     * shiro:cache
     * shiro:session
     * time out
     *  建议与 session 超时设置一致
     */
    public static final int RedisShiroKeyExpire =60 * 60 * 2 ;// 1 = 1 S ，key 过期策略

    /**
     *  全局 session 超时时间
     *  1000 * 60 * 60 * 2
     */
    public static final long sessionTimeout = 1000 * 60 * 60 * 2 ;// 1000 = 1 S    ， 一个小时没有操作 则session失效 ，继续操作 则顺延一小时

    /**
     * 是否删除过期 session
     */
    public static final boolean DeleteInvalidSessions = true;

    /**
     * 自定义cookie // true 启用自定义的SessionIdCookie
     */
    public static final boolean SessionIdCookieEnabled = true;


    /**
     * 是否定时检查session
     */
    public static final boolean SessionValidationSchedulerEnabled = true;
}
