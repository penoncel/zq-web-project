package com.mer.framework.Config.OnleSession;

import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.framework.Config.Shiro.RedisShiro.RedisCacheManager;
import com.mer.framework.Config.Shiro.RedisShiro.RedisSessionDAO;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 在线用户 serssion
 */
@Service
@SuppressWarnings("all")
public class OnleUser {
    @Autowired
    private RedisSessionDAO sessionDAO;

    @Autowired
    private RedisService redisService;

    /**
     * 删除用户信息时，同时 删除 用户serssion 且 强制下线。
     * @param name
     * @return
     */
    public boolean deleteOnleUser(String name){
        try{
            Collection<Session> sessions = sessionDAO.getActiveSessions();
            for (Session session : sessions) {
                if(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY )== null){
                    continue;
                }else {
//                    //用户名
//                    session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//                    //session id
//                    session.getId();
                    Object sessionName  =session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                    if(name.equals(sessionName.toString())){
                        sessionDAO.delete(session);// 干掉  shiro:serssion
                        redisService.delete(RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX,name.toString());//干掉 shiro:cache
                        break;
                    }

                }
            }
            return Boolean.TRUE;
        }catch (Exception e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
