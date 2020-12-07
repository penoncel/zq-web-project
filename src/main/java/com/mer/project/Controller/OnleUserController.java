package com.mer.project.Controller;

import com.mer.common.Base.Controller.BaseController;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.common.Utils.ListPageUtils;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.framework.Config.Shiro.RedisShiro.RedisCacheManager;
import com.mer.framework.Config.Shiro.RedisShiro.RedisSessionDAO;
import com.mer.project.Pojo.WebPage;
import com.mer.project.Pojo.WebUser;
import com.mer.project.Vo.UserOnlineVO;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/OnleUser")
@SuppressWarnings("all")
public class OnleUserController extends BaseController {

    @Autowired
    private RedisSessionDAO sessionDAO;
    @Autowired
    private RedisService redisService;

    @GetMapping("/list")
    public String list(){
        return this.toAction();
    }

    //如果用户非正常退出登入，页面清楚缓存，在次登入时，就会变成两个 数据展示。 在 KicKout中已经将队列结果清空。
    @GetMapping("/listData")
    @ResponseBody
    public Result listData(){
        List<Map<String,Object>> list = new ArrayList<>();

        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            UserOnlineVO userOnlineVO = new UserOnlineVO();
            WebUser userVO;
            SimplePrincipalCollection principalCollection;
            if(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY )== null){
                continue;
            }else {
//                principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                userVO = (WebUser) session.getAttribute("loginUser");
                userOnlineVO.setUser_name(userVO.getUsername());
                userOnlineVO.setUser_id(userVO.getId().toString());
            }
            userOnlineVO.setHost(session.getHost());
            userOnlineVO.setSessiond((String) session.getId());
            userOnlineVO.setStartAccessTime(session.getStartTimestamp());
            userOnlineVO.setLastAccessTime(session.getLastAccessTime());
//            userOnlineVO.setExpired(session.);
            Map ma =BeanMap.create(userOnlineVO);
            list.add(ma);
        }
        WebPage webPage = this.getPage(null);
        String name = webPage.getParams().get("user_name").toString();
        Map map = ListPageUtils.setDataList( webPage.getLimit(),webPage.getPage(),list,name);
        return Result.success(map);
    }



    /**
     *  让登录用户下线
     * @param sessionId
     * @return
     */
    @PostMapping("/outOnleUser")
    @ResponseBody
    public Result delete(@RequestParam("sessionId")String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        Object name  =session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        sessionDAO.delete(session);
        redisService.delete(RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX,name.toString());
        return Result.success();
    }
}
