package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class TaskBidEntity extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String bidNumber;
	private int taskId;
	private int memberId;
	private String bidContent;
	private int isView;
	private int price;
	private int arriveTime;
	private int cashFund;
	//	0 - 选标竞争中, 1 - 中标, 2 - 没有中标
	private boolean bingBid;
	private String taskTitle;
	private int taskstatus;
	private String bidTime;
	
	private String comments;
	private int  satisfaction;

	public String getBidNumber() {
		return bidNumber;
	}

	public void setBidNumber(String bid_number) {
		this.bidNumber = bid_number;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int task_id) {
		this.taskId = task_id;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getBidContent() {
		return bidContent;
	}

	public void setBidContent(String bid_content) {
		this.bidContent = bid_content;
	}

	public int getIsView() {
		return isView;
	}

	public void setIsView(int is_view) {
		this.isView = is_view;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(int arrive_time) {
		this.arriveTime = arrive_time;
	}

	public int getCashFund() {
		return cashFund;
	}

	public void setCashFund(int cash_fund) {
		this.cashFund = cash_fund;
	}


	public boolean isBingBid() {
		return bingBid;
	}

	public void setBingBid(boolean bingBid) {
		this.bingBid = bingBid;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public int getTaskstatus() {
		return taskstatus;
	}

	public void setTaskstatus(int taskstatus) {
		this.taskstatus = taskstatus;
	}

	public String getBidTime() {
		return bidTime;
	}

	public void setBidTime(String bidTime) {
		this.bidTime = bidTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}
	
}
