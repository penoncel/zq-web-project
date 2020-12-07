//package com.mer.common.Interceptor;
//
//import com.mer.framework.UserUtils;
//import com.mer.project.Pojo.WebUser;
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Map;
//
///**
// * 锁屏拦截器
// */
//@Component
//public class LockInterceptor extends HandlerInterceptorAdapter {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		String requestUrl = request.getServletPath();
//		try{
//			if(UserUtils.getSubject().isAuthenticated()){
//				String ___unlock = "__unlock";
//				String _unlock   = "unlock";
//				String __unlock  = "_unlock";
//				String outLock   = "/Lock";
//				//是否屏保登陆页面跳转来
//				String unlock = (String) UserUtils.getSessionAttribute(__unlock);
//				if(StringUtils.isNotEmpty(unlock) && unlock.equals(_unlock)){
//					request.setAttribute(__unlock, "1");
//					UserUtils.removeSessionAttribute(___unlock);
//				}
//				WebUser loginUser = UserUtils.getUserInfo();
//
//				if(StringUtils.isNotEmpty(String.valueOf(loginUser.getLockStatus())) && String.valueOf(loginUser.getLockStatus()).equals(-1) && !requestUrl.equals(outLock)){
//					response.sendRedirect( request.getContextPath() +outLock);
//					return false;
//				}else{
//					return super.preHandle(request,response,handler);
//				}
//			}
//			return super.preHandle(request,response,handler);
//		}catch (Exception e){
//			return super.preHandle(request,response,handler);
//		}
//    }
//}
