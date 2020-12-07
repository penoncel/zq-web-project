package com.mer.framework.Config;


import com.mer.common.Interceptor.IPIntercept;
import com.mer.common.Interceptor.RequestLimitIntercept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhoaqi 15701556037
 * **/
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLimitIntercept requestLimitIntercept;

    @Autowired
    private IPIntercept ipIntercept;

    /**
     * @author xiaobu
     * @param registry  registry
     * @descprition  等价于 http://localhost:9001/1.txt 依次在static upload目录下找1.txt文件
     * @version 1.0
     *
     * 解决 Springboot 整合 shiro 时静态资源被拦截的问题
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

//    /***
//     * 添加ViewController，不走Controller 进行跳转
//     * 添加映射路径
//     * @param registry
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/index").setViewName("index");
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipIntercept).addPathPatterns("/**").excludePathPatterns();
        log.info("###################################################################################### Init IP ipIntercept~ ##################################################################################");
        registry.addInterceptor(requestLimitIntercept).addPathPatterns("/**").excludePathPatterns();
        log.info("###################################################################################### Init IP requestLimitIntercept~##################################################################################");
    }
}
