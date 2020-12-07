package com.mer.framework.Aop;

import com.alibaba.druid.support.json.JSONUtils;
import com.mer.common.Utils.Ip.IPUtil;
import com.mer.common.Utils.RequestToMap;
import com.mer.framework.Annotction.LOG;
import com.mer.project.Pojo.WebOperationLog;
import com.mer.project.Service.WebOperationLogService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 切面处理类，操作日志异常日志记录处理
 * @author zq
 */
@Aspect
@Component
@Slf4j
@SuppressWarnings("all")
public class LOGAop {

    @Resource
    WebOperationLogService logService;

    /**
     * 进入方法时间戳
     */
    private Long startTime;
    /**
     * 方法结束时间戳(计时)
     */
    private Long endTime;


    /**
     * 定义切点,使用了 @OperLog 注解的类 或 使用了@Secret注解的方法
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@within(com.mer.framework.Annotction.LOG) || @annotation(com.mer.framework.Annotction.LOG)")
    public void operLogPoinCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.mer.project.Controller..*.*(..))")
    public void operExceptionLogPoinCut() {
    }


    private static WebOperationLog steMsg(JoinPoint joinPoint){
        //行为记录对象
        WebOperationLog operationLog = new WebOperationLog();
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //获取请求头中的User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //获取用户名
        String name = request.getSession().getAttribute("loginName").toString().equals(null)?"":request.getSession().getAttribute("loginName").toString();
        String timsss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求的参数
            Map<String, String> rtnMap = RequestToMap.converMap(request.getParameterMap());
            rtnMap.remove("password");
            rtnMap.remove("oldPassword");
            rtnMap.remove("repassword");
            String req_param=JSONUtils.toJSONString(rtnMap);
            // 获取操作注解
            LOG opLog = method.getAnnotation(LOG.class);

            operationLog.setUserName(name);//操作员
            operationLog.setDevice(userAgent.getBrowser().toString());//硬件设备
            operationLog.setDeviceSys(userAgent.getOperatingSystem().toString());//设备系统
            operationLog.setDeviceV(userAgent.getBrowserVersion().toString());//设备版本
            operationLog.setOperModule(opLog.operModul());//操作模块
            operationLog.setOperType(opLog.operType());//操作类型
            operationLog.setOperMsg(opLog.operDesc());//操作描述
            operationLog.setReqUri(request.getRequestURI());//请求URI
            operationLog.setReqIp(IPUtil.getIpAddr(request));//请求I P
            operationLog.setOperMethod(methodName);//操作方法
            operationLog.setOperTimes(timsss);//操作时间
            operationLog.setReqParameter(req_param);//请求参数

            log.info("操作时间          |->  "+timsss);
            log.info("操作员            |->  "+name);
            log.info("操作模块          |->  "+opLog.operModul());
            log.info("操作类型          |->  "+opLog.operType());
            log.info("操作描述          |->  "+opLog.operDesc());
            log.info("硬件设备          |->  "+ userAgent.getBrowser().toString());
            log.info("设备系统          |->  " +userAgent.getOperatingSystem().toString());
            log.info("设备版本          |->  " +userAgent.getBrowserVersion());
            log.info("请求I P           |->  "+IPUtil.getIpAddr(request));
            log.info("请求URI           |->  "+request.getRequestURI());
            log.info("请求类型          |->  "+request.getMethod());
            log.info("操作方法          |->  "+methodName);
            log.info("请求参数          |->  "+req_param);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return operationLog;
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        //开始时间
        startTime = System.currentTimeMillis()/1000;
        //执行请求环绕
        WebOperationLog operationLog = steMsg(joinPoint);
        // 切入后 获取 controller返回值进行josn格式
        String resq_param=JSONUtils.toJSONString(keys);
        //结束时间
        String out_tims = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //结束时间
        endTime = System.currentTimeMillis()/1000;
        //耗时 秒
        String ss  = String.valueOf(((endTime-startTime) % 60));

        operationLog.setTakeUpTime(ss);
        operationLog.setRespParameter(resq_param);
        operationLog.setRespTimes(out_tims);
        operationLog.setLogType(joinPoint.getSignature().getName().equals("goLogin")?1:2);
        operationLog.setLogStatus(1);
        logService.insertOne(operationLog);

        log.info("响应结果          |->  "+ resq_param);
        log.info("结束时间          |->  "+ out_tims);
        log.info("耗    时          |->  "+ ss);
        log.info("######################################END###########################################\n");

    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        //开始时间
        startTime = System.currentTimeMillis()/1000;
        //执行请求环绕
        WebOperationLog operationLog = steMsg(joinPoint);
        // 切入后 获取 controller 返回异常 ：异常名称 异常信息
        String errMsg=e.getClass().getName()+":"+e.getMessage();
        //结束时间
        String out_tims = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //结束时间
        endTime = System.currentTimeMillis()/1000;
        //耗时 秒
        String ss  = String.valueOf(((endTime-startTime) % 60));

        operationLog.setRespParameter(errMsg);
        operationLog.setRespTimes(out_tims);
        operationLog.setLogType(joinPoint.getSignature().getName().equals("goLogin")?1:2);
        operationLog.setLogStatus(2);
        operationLog.setTakeUpTime(ss);
        logService.insertOne(operationLog);

        log.info("响应结果          |->  "+ errMsg);
        log.info("结束时间          |->  "+ out_tims);
        log.info("耗    时          |->  "+ ss);
        log.info("######################################END###########################################\n");

    }

}