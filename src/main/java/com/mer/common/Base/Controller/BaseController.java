package com.mer.common.Base.Controller;

import com.mer.project.Pojo.*;

import com.mer.project.Pojo.WebPage;
import net.sf.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoaqi 15701556037
 */
public class BaseController {
    //Page分页对象
    private WebPage webPage;

    //视图父类文件 VIEW_PATH
    final String VIEW_PATH = "/View/";

    //返回数据
    public static JSONObject json = new JSONObject();

    //用于数据 new ArrayList<Map<String,Object>>()
    public static List<Map<String,Object>> list = null;

    public HttpServletRequest request;
    public HttpServletResponse response;

    public static String msg = "";
    /**
     * 跳转指定视图
     * */
    public String toAction(){
        String[] class_str=(this.getClass().getName()).split("\\.");
        String action=class_str[class_str.length-1].replace("Controller", "");
//        String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
        String method = new Exception().getStackTrace()[1].getMethodName().toString();	//方法名称
        //WebUser/add
        this.setAttribute("fromAction","/"+action+"/"+ method);
        String to_do = VIEW_PATH+action+"/"+ method;
//        System.out.println(to_do);
        return to_do;
    }

    /***
     *  获取 request
     * @return request
     */
    public HttpServletRequest getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 获取Session
     * @return
     */
    public HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取参数
     * @param argName
     * @return
     */
    public String getParameter(String argName){
        return this.getRequest().getParameter(argName);
    }

    /**
     * 获取setAttribute
     * @param name
     * @param value
     */
    public void setAttribute(String name , Object value){
        this.getRequest().setAttribute(name,value);
    }

    /**
     * session 用户
     * @return
     */
    public WebUser getSysUser(){
        WebUser user = (WebUser)getSession().getAttribute("loginUser");
        return user;
    }

    public WebPage getPage(Map map) {
        webPage =new WebPage();
//        Map Params = new HashMap();
//        Params.put("agent_status","2");
//        webPage.setParams(Params);
        if(map!=null){
            this.webPage.getParams().putAll(map);
        }
//        System.out.println(webPage.getParams().toString());
        return webPage;
    }

    public void setWebPage(WebPage webPage) {
        this.webPage = webPage;
    }



}
