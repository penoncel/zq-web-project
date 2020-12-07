package com.mer.common.Interceptor;

import com.mer.common.RedisKeySet.ReqIpKey;
import com.mer.common.Utils.Ip.IPUtil;
import com.mer.framework.Config.Redis.RedisService.RedisService;
import com.mer.project.Pojo.WebIp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 用户 redis session 拦截
 */
@Slf4j
@Component
public class RedisSessionIntercept implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = IPUtil.getIpAddr(request);
        if(!StringUtils.isNotBlank(ipAddress)){
            return false;
        }
        //如果没有获取到 缓存ip 则是非法请求
        if(!redisService.exists(ReqIpKey.reqIpKey,ipAddress)){
            getOutHtml(response.getWriter());
            log.warn("Error IP :" + ipAddress);
            return false;
        }
        //如果 ip 信息存在 确认是否 允许访问
        WebIp webIp = redisService.get(ReqIpKey.reqIpKey,ipAddress, WebIp.class);
        //如果 是黑名单，或者状态 为拒绝访问 则 非法请求
        if(webIp.getType()==2 || webIp.getStatus()==2){
            getOutHtml(response.getWriter());
            log.warn("Error IP :" + ipAddress);
            return false;
        }
        return true;
    }

    public static PrintWriter getOutHtml(PrintWriter out){
        out.print("<!DOCTYPE html>                                                                                                          ");
        out.print("<html lang=\"en\">                                                                                                         ");
        out.print("<head>                                                                                                                   ");
        out.print("    <meta charset=\"UTF-8\">                                                                                               ");
        out.print("    <title>Not allowed</title>                                                                                                   ");
        out.print("    <style>                                                                                                              ");
        out.print("        html, body {                                                                                                     ");
        out.print("            padding: 0;                                                                                                  ");
        out.print("            margin: 0;                                                                                                   ");
        out.print("            height: 100%;                                                                                                ");
        out.print("        }                                                                                                                ");
        out.print("        .box {                                                                                                           ");
        out.print("            width: 100%;                                                                                                 ");
        out.print("            height: 100%;                                                                                                ");
        out.print("            background-color: #C0C0C0;                                                                                     ");
        out.print("            text-align: center;                                                                   ");
        out.print("            line-height: 600px;                                                                          ");
        out.print("        }                                                                                                                ");
        out.print("    </style>                                                                                                             ");
        out.print("</head>                                                                                                                  ");
        out.print("<body>                                                                                                                   ");
        out.print("                                                                                                                         ");
        out.print("<div class=\"box\">                                                                                                        ");
        out.print("    <h1 style=\"display: inline\">Access Denied. You Do Not Have The Permission To Access This Page On This Server</h1>                      ");
        out.print("</div>                                                                                                                   ");
        out.print("                                                                                                                         ");
        out.print("</body>                                                                                                                  ");
        out.print("</html>                                                                                                                  ");
        return out;
    }
}
