package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class WeiXinUserEntity extends DataEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String openid;
	private String nickname;
	private int gender;
	private String address;
	private String headimgurl;
	private boolean isValid;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int sex) {
		this.gender = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean valid) {
		this.isValid = valid;
	}
	
	
}
