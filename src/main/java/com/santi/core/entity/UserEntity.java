package com.santi.core.entity;

import java.util.Date;

import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santi.core.common.entity.DataEntity;

public class UserEntity extends DataEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1266288869648392001L;
	//@Length(min=6,max=20, message="{module.user.username.error}")
	private String username;
	
	//@Length(min=6,max=20, message="{module.user.username.error}")
	private String userAccount;
	
	@JsonIgnore
	private String password;

	@Past
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date birthday;

	private Integer gender;
	
	private String picture;
	
	private String openId;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
