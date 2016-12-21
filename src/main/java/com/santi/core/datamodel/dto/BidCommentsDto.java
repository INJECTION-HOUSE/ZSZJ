package com.santi.core.datamodel.dto;

public class BidCommentsDto {
	// level - 1 表示不满意
	// level - 2 表示满意
	// level - 3表示十分满意
	private String comments;
	private int level;
	private Integer taskId;
	private Integer taskOwnerId;
	private Integer bidId;
	private String updatedTime;

	public BidCommentsDto() {

	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskOwnerId() {
		return taskOwnerId;
	}

	public void setTaskOwnerId(Integer taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}

}
