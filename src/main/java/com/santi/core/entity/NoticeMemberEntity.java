package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class NoticeMemberEntity extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cellPhone;
	private int noticeId;
	private boolean read;

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

}
