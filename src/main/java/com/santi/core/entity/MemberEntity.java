package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class MemberEntity extends DataEntity {

	private static final long serialVersionUID = 1L;
	private String role;
	private String identifierId;
	private boolean verified;
	private String cellphone;
	private String password;
	private String avatarName;
	private Integer gender;
	private Integer certificationType;
	private String idencardfgImg;
	private String idencardbgImg;
	private String enterpriseImg;
	
	private String realName;
	private String nickName;
	private Integer totalCash;
	private Integer avaCash;
	private String qqNumber;
	private String weixinId;
	private Integer score;
	private Integer level;
	private String ipAddress;
	private boolean lock;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getIdentifierId() {
		return identifierId;
	}

	public void setIdentifierId(String identifierId) {
		this.identifierId = identifierId;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public boolean isLock() {
		return lock;
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

	public String getAvatarName() {
		return avatarName;
	}

	public void setAvatarName(String avatarName) {
		this.avatarName = avatarName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(Integer totalCash) {
		this.totalCash = totalCash;
	}

	public Integer getAvaCash() {
		return avaCash;
	}

	public void setAvaCash(Integer avaCash) {
		this.avaCash = avaCash;
	}

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getCertificationType() {
		return certificationType;
	}

	public void setCertificationType(Integer certificationType) {
		this.certificationType = certificationType;
	}

	public String getIdencardfgImg() {
		return idencardfgImg;
	}

	public void setIdencardfgImg(String idencardfgImg) {
		this.idencardfgImg = idencardfgImg;
	}

	public String getIdencardbgImg() {
		return idencardbgImg;
	}

	public void setIdencardbgImg(String idencardbgImg) {
		this.idencardbgImg = idencardbgImg;
	}

	public String getEnterpriseImg() {
		return enterpriseImg;
	}

	public void setEnterpriseImg(String enterpriseImg) {
		this.enterpriseImg = enterpriseImg;
	}

}
