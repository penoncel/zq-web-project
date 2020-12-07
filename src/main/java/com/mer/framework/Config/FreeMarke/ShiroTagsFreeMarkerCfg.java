package com.mer.framework.Config.FreeMarke;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * freemarker集成Shiro标签
 */
@Configuration
public class ShiroTagsFreeMarkerCfg {


    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException, TemplateException {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates/");
        freemarker.template.Configuration configuration = freeMarkerConfigurer.createConfiguration();
        configuration.setDefaultEncoding("UTF-8");
        //这里可以添加其他共享变量 比如sso登录地址
        configuration.setSharedVariable("shiro", new ShiroTags());

        //扫描整个项目包括子项目     - --- -- - - - - - -
        freeMarkerConfigurer.setPreferFileSystemAccess(false);

        freeMarkerConfigurer.setConfiguration(configuration);
        return freeMarkerConfigurer;
    }


}