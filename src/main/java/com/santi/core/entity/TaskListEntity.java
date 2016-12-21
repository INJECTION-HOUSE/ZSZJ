package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class TaskListEntity  extends DataEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String geoPosition;
	private String category;
	private int prepayFee;
	private String taskTitle;
	private String deadline;
	private int status;
	private int taskOwner;
	private String joiner;
	
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
	public int getTaskOwner() {
		return taskOwner;
	}
	public void setTaskOwner(int taskOwner) {
		this.taskOwner = taskOwner;
	}
	public String getJoiner() {
		return joiner;
	}
	public void setJoiner(String joiner) {
		this.joiner = joiner;
	}
	public String getGeoPosition() {
		return geoPosition;
	}
	public void setGeoPosition(String geoPosition) {
		this.geoPosition = geoPosition;
	}
	
}
