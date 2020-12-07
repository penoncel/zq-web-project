package com.mer.framework.Annotction;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解 @LOG
 * @description: 自定义注解，用来标识请求类 或者方法是否使用AOP加密解密
 * @author zq
 */
@Target({ElementType.METHOD, ElementType.TYPE})//注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface LOG {
    String operModul() default ""; // 操作模块
    String operType() default "";  // 操作类型
    String operDesc() default "";  // 操作说明
}
