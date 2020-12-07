package com.mer.common.Filter;



import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author：zq
 * Description：过滤器配置
 * WebFilter 配置过滤器名和url策略
 * implement filter接口，并重写方法init（初始化），doFilter（调用时），destory(销毁时)
 */

/**
 * 参数处理验过滤器（针对ajax、表单等请求） 1.获取请求参数； 2.对获取到的请求参数进行处理（解密、字符串替、请求参数分类截取等等）； 3.把处理后的参数放回到请求列表里面
 */
@Component//声明为注入类
@WebFilter(filterName="MyFilter",urlPatterns="/*")
@Order(1)
@Slf4j
public class MyFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
//        System.out.println("Filter:{}"+request.getRequestURI());
//        PrintWriter out = response.getWriter();
//        out.println("您还未登陆，三秒钟后跳转至登录页面"+request.getContextPath()+"/logins");
//        out.println("<script language='javascript'>alert('您还未登陆，三秒钟后跳转至登录页面');");

//        //sql,xss过滤
//      logger.info("XssFilter.......orignal url:{},ParameterMap:{}",request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()));
        XssHttpServletRequestWrapper xssHttpServletRequestWrapper=new XssHttpServletRequestWrapper( request);
        filterChain.doFilter(xssHttpServletRequestWrapper, response);
//        filterChain.doFilter(request, response);
//      logger.info("XssFilter..........doFilter url:{},ParameterMap:{}",xssHttpServletRequestWrapper.getRequestURI(), JSONObject.toJSONString(xssHttpServletRequestWrapper.getParameterMap()));

    }

    @Override
    public void destroy() {
    }
}
