package com.mer.framework.Config.Druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 说明：配置监控统计功能
 */
@Configuration
public class DruidConfig {

    // http://127.0.0.1:6039/druid/login.html

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    //配置Druid的监控
    //1.配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){

        // 创建servlet注册实体 记得加上"/druid/*",否则在进行登录页面的重定向过多而无法访问的问题(记得在Google浏览器才会报这个错)
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean  = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");

        Map<String,String> initParams = new HashMap<>();
//        // 设置ip白名单
//        initParams.put("allow", "127.0.0.12");
//        // 设置ip黑名单，如果allow与deny共同存在时,deny优先于allow
//        initParams.put("deny", "192.168.13.100");
        // 设置控制台管理用户
//        initParams.put("loginUsername","admin");
//        initParams.put("loginPassword","123456");

        // 是否可以重置数据
        initParams.put("resetEnable", "false");
        servletRegistrationBean.setInitParameters(initParams);
        return servletRegistrationBean;
    }
    //2.配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        // 创建过滤器
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>(new WebStatFilter());
        filterRegistrationBean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();
        //配置拦截时需要排除的请求
        initParams.put("exclusions","*.js,*.css,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));

        return filterRegistrationBean;

    }


}
