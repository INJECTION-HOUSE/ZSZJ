package com.santi.core.param;

public class SearchBaseParam {
	private Integer id;
	private int rowoffset;
	private int rows;
	private int pageIndex;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getRowoffset() {
		return rowoffset;
	}

	public void setRowoffset(int rowoffset) {
		this.rowoffset = rowoffset;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
