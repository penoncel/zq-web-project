package com.mer.framework;

import com.mer.project.Pojo.WebUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

public class UserUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    /**
     * 获取用户信息
     * @return
     */
    public static WebUser getUserInfo() {
        return (WebUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取登陆IP
     * @return
     */
    public static String getIp(){
        return getSubject().getSession().getHost();
    }

    /**
     * 重新装载用户信息--> 修改了当前用户信息需要调用
     * @param user
     */
    public static void reloadUser(WebUser user){
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }
    public static void removeSessionAttribute(String key){
        getSession().removeAttribute(key);
    }
}
