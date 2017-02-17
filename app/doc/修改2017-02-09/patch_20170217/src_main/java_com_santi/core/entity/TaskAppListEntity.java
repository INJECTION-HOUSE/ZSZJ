package com.santi.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santi.core.common.entity.BaseEntity;

public class TaskAppListEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private int type;
	private String category;
	private int prepayFee;
	private String taskTitle;
	private String deadline;
	private int status;
	private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrepayFee() {
		return prepayFee;
	}
	public void setPrepayFee(int prepayFee) {
		this.prepayFee = prepayFee;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
