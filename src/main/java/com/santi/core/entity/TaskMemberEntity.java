package com.santi.core.entity;

import com.santi.core.common.entity.BaseEntity;

public class TaskMemberEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private int bid;
	private String content;
	private String nickName;
	private boolean isVerify;
	private String bidNumber;
	private String avatar;
	private int price;
	private String time;
	private int bindBid;
	private int arriveTime;
	
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public boolean isVerify() {
		return isVerify;
	}
	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}
	public String getBidNumber() {
		return bidNumber;
	}
	public void setBidNumber(String bidNumber) {
		this.bidNumber = bidNumber;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getBindBid() {
		return bindBid;
	}
	public void setBindBid(int bindBid) {
		this.bindBid = bindBid;
	}
	public int getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}
}
