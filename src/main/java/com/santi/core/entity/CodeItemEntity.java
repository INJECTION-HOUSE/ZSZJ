package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class CodeItemEntity extends DataEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String desc;
	private int index;
	private Integer codeTypeid;
	private String typeName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Integer getCodeTypeid() {
		return codeTypeid;
	}

	public void setCodeTypeid(Integer codeTypeid) {
		this.codeTypeid = codeTypeid;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
