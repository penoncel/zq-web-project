package com.mer.framework.Config.Shiro;

import com.alibaba.fastjson.JSON;
import com.mer.framework.Config.Shiro.RedisShiro.RedisCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 思路：
 * 1.读取当前登录用户名，获取在缓存中的sessionId队列
 * 2.判断队列的长度，大于最大登录限制的时候，按踢出规则 将之前的sessionId中的session域中存入kickout：true，并更新队列缓存
 * 3.判断当前登录的session域中的kickout如果为true， 想将其做退出登录处理，然后再重定向到踢出登录提示页面
 */
@Slf4j
public class KickoutSessionFilter extends AccessControlFilter {

    private String kickoutUrl; // 踢出后到的地址

    private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认false踢出之前登录的用户

    private int maxSession = 1; // 同一个帐号最大会话数 默认1

    private SessionManager sessionManager;

    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        //必须和ehcache缓存配置中的缓存name一致
        this.cache = cacheManager.getCache(RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
          //没有登录授权 且没有记住我
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }

        // 获得用户请求的URI
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
//        log.warn("===当前请求的uri：==" + path);
        String contextPath = req.getContextPath();
//        log.warn("===当前请求的域名或ip+端口：==" + contextPath);
        //执行的登入
        if (path.equals("/WebUser/goLogin")) {
            return true;
        }
        Session session = subject.getSession();
//        log.warn("==session 时间设置：" + String.valueOf(session.getTimeout())+ "===========");

        // 当前用户
        String username = subject.getPrincipal().toString();
//        log.warn("=== 当前用户username：  ==" + username);
        Serializable sessionId = session.getId();
//        log.warn("=== 当前用户sessionId： ==" + sessionId);

        // 读取缓存用户 没有就存入
        Deque<Serializable> deque = cache.get(username);
//        log.warn("===当前deque：==" + deque);
        if (deque == null) {
            // 初始化队列
            deque = new ArrayDeque<Serializable>();
        }
        // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            // 将sessionId存入队列
            deque.push(sessionId);
            // 将用户的sessionId队列缓存
            cache.put(username, deque);
        }
        // 如果队列里的 sessionId 数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
//            log.warn("===deque队列长度：==" + deque.size());
            Serializable kickoutSessionId = null;
            // 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
            if (kickoutAfter) {
                // 如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else {
                // 否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            // 踢出后再更新下缓存队列
            cache.put(username, deque);
            try {
                // 获取被踢出的 sessionId 的 session 对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
//                    Session readSession = redisSessionDAO.readSession(kickoutSessionId);
//                    redisSessionDAO.delete(readSession);
                    // 设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {// ignore exception
            }
        }

        // 如果被踢出了，(前者或后者) 直接退出，重定向到踢出后的地址
        if ((Boolean) session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout") == true) {
            // 会话被踢出了
            try {
                // 退出登录
                subject.logout();
            } catch (Exception e) { // ignore
            }
            saveRequest(request);

            Map<String, String> resultMap = new HashMap<>();
            //判断是不是Ajax请求
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                resultMap.put("code", "TimeOut");
                resultMap.put("msg", "您的账号已在别地登入，如非本人操作，请修改密码，重新登录！");
                //输出json串
                out(response, resultMap);
            }else{
                //重定向
                log.warn("== 踢出后用户重定向的路径kickoutUrl:" + kickoutUrl);
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }
        return true;

    }

    private void out(ServletResponse hresponse, Map<String, String> resultMap)
            throws IOException {
        try {
            hresponse.setCharacterEncoding("UTF-8");
            PrintWriter out = hresponse.getWriter();
            out.println(JSON.toJSONString(resultMap));
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
        }
    }


}
