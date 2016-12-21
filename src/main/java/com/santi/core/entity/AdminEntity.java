package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class AdminEntity extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String role;
	private String cellphone;
	private String password;
	private String userName;
	private String workCardNum;
	private int gender;
	private boolean valid;
	private String birthday;
	private String loginTime;
	private String loginIP;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWorkCardNum() {
		return workCardNum;
	}

	public void setWorkCardNum(String workCardNum) {
		this.workCardNum = workCardNum;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

}
