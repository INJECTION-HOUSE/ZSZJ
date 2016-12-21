package com.santi.core.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;



public class DataEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3439833810593326959L;

	@JsonIgnore
    private String createBy;

	@JsonIgnore
    private String createTime;

	@JsonIgnore
    private String updateBy;
	
	@JsonIgnore
    private String updateTime;


	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}



}