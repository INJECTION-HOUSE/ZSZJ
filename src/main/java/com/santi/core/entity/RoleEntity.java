package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class RoleEntity extends DataEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;
	private String type;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
