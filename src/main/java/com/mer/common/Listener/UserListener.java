package com.mer.common.Listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
@Slf4j
public class UserListener implements HttpSessionListener {

    public static long onlineUserCount = 0;//在线人数

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("进入Session创建事件,当前在线用户数："+(++onlineUserCount));
        log.info("进入Session创建事件,当前在线用户数："+(++onlineUserCount));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("进入Session销毁事件,当前在线用户数："+(--onlineUserCount));
        log.info("进入Session销毁事件,当前在线用户数："+(--onlineUserCount));
    }
}
