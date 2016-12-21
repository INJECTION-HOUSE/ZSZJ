package com.santi.core.global.common;

import javax.servlet.http.HttpServletRequest;


/**
 * ip获取
 * @author hl
 */
public class IPUtil {

	
	/**
	 * 先从反向代理里找ip
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
		if(request==null){
			return "";
		}
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || "".equals(ip)){
			ip = request.getRemoteAddr();
		}
		return ip;
		
	}
	
}
