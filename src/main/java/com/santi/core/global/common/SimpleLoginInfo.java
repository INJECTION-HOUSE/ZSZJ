package com.santi.core.global.common;

import java.util.Date;

import com.santi.core.entity.UserEntity;


public class SimpleLoginInfo implements LoginInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3979426895930039531L;

	private UserEntity loginUser;
	
	private String loginIp;
	
	private Date loginTime;
	
	private boolean signIn; 
	
	public SimpleLoginInfo(){
		this(new UserEntity(),false);
	}
	
	public SimpleLoginInfo(UserEntity loginUser,String loginIp){
		this(loginUser,true);
		this.loginIp = loginIp;
	}
	
	public SimpleLoginInfo(UserEntity loginUser,boolean signIn){
		this.loginUser = loginUser;
		loginIp = "unknow";
		loginTime = new Date();
		this.signIn = signIn;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public boolean isSignIn() {
		return signIn;
	}


	@Override
	public UserEntity getLoginUser() {
		return loginUser;
	}

	@Override
	public String getLoginName() {
		if(isSignIn()&&getLoginUser()!=null){
			return getLoginUser().getUserAccount();
		}
		return "system";
	}

}
