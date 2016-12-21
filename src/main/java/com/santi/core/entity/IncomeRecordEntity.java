package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class IncomeRecordEntity extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer memberId;
	private String orderNumber;
	private String orderTitle;
	private Integer sourceFrom;
	private String incomeTime;
	private Integer income;
	private Integer outTax;
	private Integer tradeAmount;
	private String vendor;
	private boolean withDraw;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public Integer getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(Integer sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getIncomeTime() {
		return incomeTime;
	}

	public void setIncomeTime(String incomeTime) {
		this.incomeTime = incomeTime;
	}

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Integer getOutTax() {
		return outTax;
	}

	public void setOutTax(Integer outTax) {
		this.outTax = outTax;
	}

	public Integer getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Integer tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public boolean isWithDraw() {
		return withDraw;
	}

	public void setWithDraw(boolean withDraw) {
		this.withDraw = withDraw;
	}

}
