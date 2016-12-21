package com.santi.common.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;


/**
 * session过期监听
 * @author Administrator
 *
 */
public class SessionExpireListener implements HttpSessionListener{

	private static final Logger logger = Logger.getLogger(SessionExpireListener.class);
	
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.debug("session created : " + se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		logger.debug("session: " + se.getSession().getId() + " destory...");
		Object login = session.getAttribute(LoginInfoUtil.LOGININFO);
		if(login == null||!((LoginInfo)login).isSignIn()){
			return ;
		}
		logger.info("用户: " + ((LoginInfo)login).getLoginName() + " 退出");
	}

}
