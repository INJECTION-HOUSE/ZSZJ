package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class CodeTypeEntity extends DataEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeName;
	private String typeDesc;
	private String seriesNumber;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getSeriesNumber() {
		return seriesNumber;
	}

	public void setSeriesNumber(String seriesNumber) {
		this.seriesNumber = seriesNumber;
	}

}
