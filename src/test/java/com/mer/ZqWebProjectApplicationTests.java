package com.mer;

import com.mer.common.RedisKeySet.RedisSessionKey;
import com.mer.project.Pojo.WebUser;
import com.mer.project.Service.WebUserService;
import com.mer.project.Vo.UserOnlineVO;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class ZqWebProjectApplicationTests {


    @Autowired
    WebUserService sysWebUserService;
    @Test
    void contextLoads() {
        System.out.println(String.valueOf(new SimpleHash("md5", "zq123456", null, 2)));
    }

    @Test
    void contextLoadssss() {
//        System.out.println(RedisSessionKey.shiroRedisCachePrefix.getPrefix());
//       WebUser webUser;
//        for (int i = 0; i < 15; i++) {
//            webUser = new WebUser();
//            webUser.setUsername("test"+i);
//            webUser.setUserpass("01a1a22a6c17cafc32102357911edb48");
//            webUser.setNickname("NCtest"+i);
//            webUser.setPhone("1570155603"+i);
//            webUser.setLever("8");
//            webUser.setStatus(1);
//            webUser.setAgent_num("");
//            webUser.setBlog_form(1);
//            sysWebUserService.insert_one_user(webUser);
//        }
    }

//    @Test
//    void getSession() {
//
//        List<UserOnlineVO> list = new ArrayList<>();
//        Collection<Session> sessions = sessionDAO.getActiveSessions();
//        for (Session session : sessions) {
//            UserOnlineVO userOnlineVO = new UserOnlineVO();
//            WebUser userVO;
//            SimplePrincipalCollection principalCollection;
//            if(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY )== null){
//                continue;
//            }else {
//                principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//                userVO = (WebUser) principalCollection.getPrimaryPrincipal();
//                userOnlineVO.setUserName(userVO.getUsername());
//                userOnlineVO.setUserId(userVO.getId().toString());
//            }
//            userOnlineVO.setHost(session.getHost());
//            userOnlineVO.setSessionId((String) session.getId());
//            userOnlineVO.setStartAccessTime(session.getStartTimestamp());
//            userOnlineVO.setLastAccessTime(session.getLastAccessTime());
//            list.add(userOnlineVO);
//        }
//
//
//    }
}
