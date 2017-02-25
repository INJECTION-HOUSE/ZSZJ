package com.santi.core.service;

import javax.servlet.http.HttpServletRequest;

import com.santi.core.common.entity.RespJson;
import com.santi.core.entity.UserEntity;
import com.santi.core.param.ResetPasswordParam;

public interface LoginService {

	/**
	 * 
	 * @param request
	 * @param entity
	 * @return
	 */
	public RespJson login(HttpServletRequest request,UserEntity entity);
	
	/**
	 * 
	 * @param request
	 * @param entity
	 * @return
	 */
	public RespJson loginAdmin(HttpServletRequest request,UserEntity entity);
	/**
	 * 
	 * @param request
	 * @param password
	 * @param newpassword
	 * @return
	 */
	public RespJson passwordUpdate(HttpServletRequest request, String password, String newpassword);

	public void resetPassword(ResetPasswordParam param);
	
	
}
