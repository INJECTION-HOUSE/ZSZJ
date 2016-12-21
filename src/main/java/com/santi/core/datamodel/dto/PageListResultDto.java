package com.santi.core.datamodel.dto;

import java.util.List;
/**
 * pagination result data for each Page View
 * 
 * @author gloomy-fish
 *
 * @param <T>
 */
public class PageListResultDto<T> {

	private List<T> model; // data set
	private int pageNum; // page index
	private int total; // total records
	private int pageSize = 10; // records in one page by default

	public List<T> getModel() {
		return model;
	}

	public void setModel(List<T> model) {
		this.model = model;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
