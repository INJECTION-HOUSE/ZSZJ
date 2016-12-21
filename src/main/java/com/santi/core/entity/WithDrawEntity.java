package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class WithDrawEntity extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer memberId;
	private int withdrawMoney;
	private String withdrawTime;
	private int status;
	private String desc;
	private Integer checkAdminId;
	private String auditor; // 审核人
	private String checkDate;
	private String payTime;
	private Integer payAdminId;
	private String payor; // 付款人

	// 前台返回
	private String nickName;
	private String realName;
	private String mobile;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public int getWithdrawMoney() {
		return withdrawMoney;
	}

	public void setWithdrawMoney(int withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
	}

	public String getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(String withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getCheckAdminId() {
		return checkAdminId;
	}

	public void setCheckAdminId(Integer checkAdminId) {
		this.checkAdminId = checkAdminId;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Integer getPayAdminId() {
		return payAdminId;
	}

	public void setPayAdminId(Integer payAdminId) {
		this.payAdminId = payAdminId;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getPayor() {
		return payor;
	}

	public void setPayor(String payor) {
		this.payor = payor;
	}

}
