package com.mer.framework.Config.MybatisPlus;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhoaqi 15701556037
 *
 * Description：MybatisPlus 配置文件
 */
@Configuration
@MapperScan("com.mer.project.Dao")
public class MybatisPlusConfig {
//    /*
//     * 分页插件，自动识别数据库类型
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }

    /*
     * 数据源
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }
}