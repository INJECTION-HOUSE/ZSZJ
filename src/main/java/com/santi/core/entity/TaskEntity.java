package com.santi.core.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.santi.core.common.entity.DataEntity;

public class TaskEntity extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer taskOwner;
	private int memberID;
	private String category;
	private Integer cashFund;
	
	@NotNull(message="{module.membercenter.newtask.common.notnull}")
	@NotBlank(message="{module.membercenter.newtask.common.notnull}")
	private String taskTitle;
	
	private String taskDesc;
	private String geoPosition;
	private String errorTime;
	private String deadline;
	
	@NotNull(message="{module.membercenter.newtask.common.notnull}")
	@NotBlank(message="{module.membercenter.newtask.common.notnull}")
	private String cellphone;
	
	@NotNull(message="{module.membercenter.newtask.common.notnull}")
	@NotBlank(message="{module.membercenter.newtask.common.notnull}")
	private String addressDetail;
	
	@NotNull(message="{module.membercenter.newtask.common.notnull}")
	@Range(min=50,max=200,message="{module.membercenter.newtask.prepay}")
	private Integer prepayFee;
	
	private Integer status;
	private Integer totalFee;
	private String imageFiles;
	private String voiceFiles;
	private String orderNumber;
	
	// UI display info
	private String enterpriser; // 企业名称
	private String nickname; // 会员昵称
	
	// UI form elements
	private String province;
	private String city;
	private String county;
	private String imgfile1;
	private String imgfile2;
	private String imgfile3;
	private String imgfile4;
	private String imgfile5;
	
	public Integer getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(Integer taskOwner) {
		this.taskOwner = taskOwner;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getCashFund() {
		return cashFund;
	}

	public void setCashFund(Integer cashFund) {
		this.cashFund = cashFund;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getGeoPosition() {
		return geoPosition;
	}

	public void setGeoPosition(String geoPosition) {
		this.geoPosition = geoPosition;
	}

	public String getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(String errorTime) {
		this.errorTime = errorTime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Integer getPrepayFee() {
		return prepayFee;
	}

	public void setPrepayFee(Integer prepayFee) {
		this.prepayFee = prepayFee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getImageFiles() {
		return imageFiles;
	}

	public void setImageFiles(String imageFiles) {
		this.imageFiles = imageFiles;
	}

	public String getVoiceFiles() {
		return voiceFiles;
	}

	public void setVoiceFiles(String voiceFiles) {
		this.voiceFiles = voiceFiles;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getImgfile1() {
		return imgfile1;
	}

	public void setImgfile1(String imgfile1) {
		this.imgfile1 = imgfile1;
	}

	public String getImgfile2() {
		return imgfile2;
	}

	public void setImgfile2(String imgfile2) {
		this.imgfile2 = imgfile2;
	}

	public String getImgfile3() {
		return imgfile3;
	}

	public void setImgfile3(String imgfile3) {
		this.imgfile3 = imgfile3;
	}

	public String getImgfile4() {
		return imgfile4;
	}

	public void setImgfile4(String imgfile4) {
		this.imgfile4 = imgfile4;
	}

	public String getImgfile5() {
		return imgfile5;
	}

	public void setImgfile5(String imgfile5) {
		this.imgfile5 = imgfile5;
	}

	public String getEnterpriser() {
		return enterpriser;
	}

	public void setEnterpriser(String enterpriser) {
		this.enterpriser = enterpriser;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
