package com.santi.core.global.common;

import java.io.Serializable;
import java.util.Date;

import com.santi.core.entity.UserEntity;

public interface LoginInfo extends Serializable{

	/**
	 * @return 登录用户基类
	 */
	UserEntity getLoginUser();
	
	
	/**
	 * @return 登录名
	 */
	String getLoginName();
	
	/**
	 * @return 登陆ip
	 */
	String getLoginIp();
	
	/**
	 * @return 登陆时间
	 */
	Date getLoginTime();
	
	/**
	 * @return 是否已登录
	 */
	boolean isSignIn();
	
}
