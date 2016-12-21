package com.santi.core.global.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.santi.core.entity.UserEntity;



/**
 * 登陆类
 * @author hl 2015-12-23
 *
 */
public class LoginInfoUtil {

	public static final String LOGININFO = "LOGININFO";
	
	public static final String USERNAME = "USERNAME";
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginInfoUtil.class);
	
	private static final ThreadLocal<LoginInfo> SESSION_MAP = new ThreadLocal<LoginInfo>();
	
	private static LoginInfo createLoginInfo(UserEntity user,String ip){
		return new SimpleLoginInfo(user,ip);
	}
	
	public static LoginInfo getLoginInfo() {
		LoginInfo infoObj = SESSION_MAP.get();
		if(infoObj == null) {
			return new SimpleLoginInfo(); 
		}
		return infoObj;
	}
	
	public static void setLogInfo(LoginInfo userInfo) {
		SESSION_MAP.set(userInfo);
	}
	
	/**
	 * 把登陆信息记录到session里
	 * @param request
	 * @param username
	 * @param ip 若不填则为request里取
	 */
	public static void setLoginInfoToSession(HttpServletRequest request,UserEntity user,String ip){
		if(ip == null || "".equals(ip.trim())){
			ip = IPUtil.getIp(request);
		}
		LoginInfo info = createLoginInfo(user, ip);
		LOG.info("用户登陆，用户名为{},ip为{},登陆时间为{}",info.getLoginUser().getUserAccount(),info.getLoginIp(),info.getLoginTime());
		HttpSession session = request.getSession();
		session.setAttribute(LOGININFO, info);
		session.setAttribute(USERNAME, user.getUsername());
	}

	/**
	 * 登出
	 * @param request
	 */
	public static void signOut(HttpServletRequest request){
		if(request!=null){
			HttpSession session = request.getSession();
			LoginInfo info = (LoginInfo)session.getAttribute(LOGININFO);
			LOG.info("用户主动登出，用户名为{},ip为{},登出时间为{}",info.getLoginUser().getUserAccount(),IPUtil.getIp(request) ,new Date());	
			session.removeAttribute(LOGININFO);
			session.removeAttribute(USERNAME);
			session.invalidate();
		}
	}
	
	public static LoginInfo getLoginInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object userinfo = session.getAttribute(LOGININFO);
		if(userinfo instanceof LoginInfo) {
			return (LoginInfo)userinfo;
		}
		return new SimpleLoginInfo();
	}
	
	public static String getUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object username = session.getAttribute(USERNAME);
		if(username == null) {
			return "system";
		}
		return (String)username;
	}
	
	/**
	 * 判断是否登陆，并把登陆信息放到线程中
	 * @param request
	 * @return
	 */
	public static boolean isSignIn(HttpServletRequest request){
		HttpSession session = request.getSession();
		Object userinfo = session.getAttribute(LOGININFO);
		Object username = session.getAttribute(USERNAME);
		if(userinfo == null && username == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
