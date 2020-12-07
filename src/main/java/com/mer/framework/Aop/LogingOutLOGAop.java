package com.mer.framework.Aop;

import com.alibaba.druid.support.json.JSONUtils;
import com.mer.common.Utils.Ip.IPUtil;
import com.mer.common.Utils.LayUIResult.Result;
import com.mer.common.Utils.RequestToMap;
import com.mer.framework.Annotction.LogingOutLOG;
import com.mer.project.Pojo.WebOperationLog;
import com.mer.project.Service.WebOperationLogService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
 * 退出登入切面
 * @author zq
 */
@Aspect
@Component
@Slf4j
@SuppressWarnings("all")
public class LogingOutLOGAop {

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


    // 定义切点,使用了@Secret注解的类 或 使用了@Secret注解的方法
    @Pointcut("@within(com.mer.framework.Annotction.LogingOutLOG) || @annotation(com.mer.framework.Annotction.LogingOutLOG)")
    public void pointcut(){}

    // 环绕切面
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint){
        //开始时间
        startTime = System.currentTimeMillis()/1000;
        //设定一个系统时间
        String timsss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //获取请求头中的User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //获取用户名
        String name = request.getSession().getAttribute("loginName").toString();
        //行为记录对象
        WebOperationLog operationLog = new WebOperationLog();
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
            //json格式化 请求参数
            String req_param= JSONUtils.toJSONString(rtnMap);
            // 获取操作注解
            LogingOutLOG opLog = method.getAnnotation(LogingOutLOG.class);

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
            // 执行请求
            Object result =  joinPoint.proceed();

            // 切入后 获取 controller返回值进行josn格式
            String resq_param=JSONUtils.toJSONString(result);
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

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.error();
    }




}
