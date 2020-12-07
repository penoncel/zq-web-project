package com.mer.common.Advice;


import com.mer.common.Utils.LayUIResult.Result;
import com.mer.common.Utils.WriteFrom;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhoaqi 15701556037
 * Description：Controller 异常通知
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    private static final String logExceptionFormat = "Capture Exception By GlobalExceptionHandler: Code: %s Detail: %s";

    @ExceptionHandler
    @ResponseBody
    public Result exception(Exception ex) {
        log.error(WriteFrom.WriterEx(ex));
        return Result.error("操作失败,系统内部错误");
    }

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public String requestNotReadable(HttpMessageNotReadableException ex) {
        System.out.println("400..requestNotReadable");
        return resultFormat(7, ex,"4xx");
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    public String requestTypeMismatch(TypeMismatchException ex) {
        System.out.println("400..TypeMismatchException");
        return resultFormat(8, ex,"4xx");
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public String requestMissingServletRequest(MissingServletRequestParameterException ex) {
        System.out.println("400..MissingServletRequest");
        return resultFormat(9, ex,"4xx");
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public String request405(HttpRequestMethodNotSupportedException ex) {
        return resultFormat(10, ex,"4xx");
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public String request406(HttpMediaTypeNotAcceptableException ex) {
        return resultFormat(11, ex,"4xx");
    }

    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public String server500(RuntimeException ex) {
        return resultFormat(12, ex,"5xx");
    }

    //栈溢出
    @ExceptionHandler({StackOverflowError.class})
    public String requestStackOverflow(StackOverflowError ex) {
        return resultFormat(13, ex,"5xx");
    }

    //权限不足
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public Result UnauthorizedException(UnauthorizedException ue) {
        System.err.println("权限不足！");
        return Result.error("403","权限不足,如需要此权限请联系管理员！");
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
        return "权限认证失败";
    }

    private <T extends Throwable> String resultFormat(Integer code, T ex,String errorPage) {
        ex.printStackTrace();
        log.error(String.format(logExceptionFormat, code, ex.getMessage()));
        return "error/"+errorPage;
    }

}
