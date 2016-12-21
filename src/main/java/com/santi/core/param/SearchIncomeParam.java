package com.santi.core.param;

public class SearchIncomeParam extends SearchBaseParam {
	private String orderNumber;
	private Integer memberId;
	private Integer sourceFrom;
	private String previousMonth;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(Integer sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getPreviousMonth() {
		return previousMonth;
	}

	public void setPreviousMonth(String previousMonth) {
		this.previousMonth = previousMonth;
	}


}
