package com.santi.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.santi.core.global.common.IPUtil;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;

public class CommonInterceptor extends HandlerInterceptorAdapter{

	private static final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		log.debug("请求url:{},ip:{}", request.getRequestURI(),IPUtil.getIp(request));
		if(request.getSession().getAttribute(LoginInfoUtil.USERNAME) != null) {
			MDC.put("user", (String)request.getSession().getAttribute(LoginInfoUtil.USERNAME));
		}
		Object obj = request.getSession().getAttribute(LoginInfoUtil.LOGININFO);
		if(obj != null && obj instanceof LoginInfo) {
			LoginInfoUtil.setLogInfo((LoginInfo)obj);
		}
		if(LoginInfoUtil.isSignIn(request)){
			return true;
		}
		log.debug("用户未登陆");
		
		// TODO:zhigang
		request.getRequestDispatcher("/WEB-INF/views/pclogin/login.jsp").forward(request, response);  
        return false;  
	}
	
}
