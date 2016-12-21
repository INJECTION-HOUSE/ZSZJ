package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class MemberDetailInfoEntity extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer memberId;
	private String telephone;
	private String instroduceDesc;
	private String occupy;
	private String birthday;
	private String skillDesc;
	private String currentCity;
	private String contactAddress;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getInstroduceDesc() {
		return instroduceDesc;
	}

	public void setInstroduceDesc(String instroduceDesc) {
		this.instroduceDesc = instroduceDesc;
	}

	public String getOccupy() {
		return occupy;
	}

	public void setOccupy(String occupy) {
		this.occupy = occupy;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSkillDesc() {
		return skillDesc;
	}

	public void setSkillDesc(String skillDesc) {
		this.skillDesc = skillDesc;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

}
