//package com.mer.framework.Aop;
//
//import com.mer.project.Pojo.WebOperationLog;
//
//import eu.bitwalker.useragentutils.UserAgent;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Date;
//
///**
// * @author zhoaqi 15701556037
// * 系统日志切面
// */
//@Aspect// 表示该类是一个通知类
//@Component//spring注解方式bean注入 交给spring管理
//@Slf4j
//public class WebLog {
//
//
//
//
//    /**
//     * 进入方法时间戳
//     */
//    private Long startTime;
//    /**
//     * 方法结束时间戳(计时)
//     */
//    private Long endTime;
//
//
//    /**
//     * 切点
//     * 配置需要添加切面通知的包路径
//     */
//    @Pointcut("execution(* com.mer.project.Controller.*.*(..))")
//    public void webLog() {}
//
//    /**
//     * 前置通知
//     * @param joinPoint 切点
//     */
//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) {
//        //得到的结果为： /项目名
//        startTime = System.currentTimeMillis();
//        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        if (!request.getRequestURL().toString().equals("/api/file/ShowImg")){
//            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//            //AOP代理类的类（class）信息
//            Signature signature = joinPoint.getSignature();
//            signature.getDeclaringType();
//            MethodSignature methodSignature = (MethodSignature) signature;
//            String[] strings = methodSignature.getParameterNames();
//
//            //打印请求的内容
//            log.info("请求时间          |->  " + LocalDateTime.now()+ request.getSession().getAttribute("loginName"));
//            log.info("请求地址          |->  " + request.getRequestURL().toString());
//            log.info("请求类型          |->  " + request.getMethod());
//            log.info("请 求 IPIntercept          |->  " + request.getRemoteAddr());
//            log.info("请求方法          |->  " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//            log.info("请求参数-KEY      |->  " +Arrays.toString(strings));
//            log.info("请求参数-VALUE    |->  " +Arrays.toString(joinPoint.getArgs()));
//            // 系统信息
//            //获取请求头中的User-Agent
//            log.info("浏览器            |->  "+ userAgent.getBrowser().toString());
//            log.info("浏览器版本        |->  " +userAgent.getBrowserVersion());
//            log.info("操作系统          |->  " +userAgent.getOperatingSystem().toString());
//
//
//            if(joinPoint.getSignature().getName().equals("goLogin")){
//
//            }else{
//                //操作记录
//            }
//
//        }
//
//    }
//
//    /**
//     * [方法] getIpAddr
//     * [描述] 得到真实的IP
//     * [参数] HttpServletRequest
//     * [返回] String
//     */
//    public static String getIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("X-Forwarded-For");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IPIntercept");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IPIntercept");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
//
//    /**
//     * 处理完请求返回内容
//     * @param ret
//     * @throws Throwable
//     */
//    @AfterReturning(returning = "ret", pointcut = "webLog()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
////        logger.info("响应信息          |->  " +ret.toString());
//        log.info("\r\r");
//    }
//
//
//    /**
//     * 异常通知：
//     * 1. 在目标方法非正常结束，发生异常或者抛出异常时执行
//     * 1. 在异常通知中设置异常信息，并将其保存
//     *
//     * @param throwable
//     */
//    @AfterThrowing(value = "webLog()",throwing = "throwable")
//    public void throwss(Throwable throwable){
//        log.error("异常时间         |->  " + LocalDateTime.now());
//        log.error("抛出异常         |->  " + throwable.getMessage());
//    }
//
//
//    @After("webLog()")
//    public void doAfter() {
//        endTime = System.currentTimeMillis();
//        log.info("请求结束时间      |->  " + LocalDateTime.now());
//        log.info("请求耗时          |->  " + (endTime - startTime));
//    }
//
//}
