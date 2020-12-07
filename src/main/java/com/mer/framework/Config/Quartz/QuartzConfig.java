package com.mer.framework.Config.Quartz;

import com.mer.common.QuartzJob.*;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 配置类
 */
@Configuration
public class QuartzConfig {


    /**
     *testTime
     * */
    @Bean(name = "testTime_job")
    public MethodInvokingJobDetailFactoryBean testTime_job(ATestTimes times){
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        factoryBean.setConcurrent(true);
        // 使用哪个对象
        factoryBean.setTargetObject(times);
        // 使用哪个方法
        factoryBean.setTargetMethod("getNowTimes");
        return  factoryBean;
    }
    @Bean(name = "testTime_cron")
    public CronTriggerFactoryBean testTime_cron(@Qualifier("testTime_job") MethodInvokingJobDetailFactoryBean job5){
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        // 获取 job对象
        factoryBean.setJobDetail( job5.getObject() );
        // 设置 时间表达式
        factoryBean.setCronExpression("0/10 * * * * ?");
//        factoryBean.setCronExpression("0 15 14 ? * *");
        return  factoryBean;
    }



    // 定义 任务，传入 triggers
    @Bean
    public SchedulerFactoryBean scheduler1(Trigger... triggers){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        // 设置 triggers
        factoryBean.setTriggers( triggers );
        // 自动运行
        factoryBean.setAutoStartup(true);
        return factoryBean;
    }


}
