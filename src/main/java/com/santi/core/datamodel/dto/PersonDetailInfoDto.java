package com.santi.core.datamodel.dto;

import com.santi.core.common.entity.DataEntity;

public class PersonDetailInfoDto extends DataEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private Integer memeberId;
	private String birthday;
	private int gender;
	private String qqNumber;
	private String telephone;
	private String workAddress;
	private String occupy;
	private String introdDesc;
	private String skills;
	private String currentAddress;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getOccupy() {
		return occupy;
	}

	public void setOccupy(String occupy) {
		this.occupy = occupy;
	}

	public String getIntrodDesc() {
		return introdDesc;
	}

	public void setIntrodDesc(String introdDesc) {
		this.introdDesc = introdDesc;
	}

	public Integer getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(Integer memeberId) {
		this.memeberId = memeberId;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

}
