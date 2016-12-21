package com.santi.core.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.santi.core.common.entity.DataEntity;

public class MessageEntity extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	
	@NotNull(message="{module.privacy.message.common.notnull}")
	@NotBlank(message="{module.privacy.message.common.notnull}")
	private String title;
	
	@NotNull(message="{module.privacy.message.common.notnull}")
	@NotBlank(message="{module.privacy.message.common.notnull}")
	private String content;
	
	@NotNull(message="{module.privacy.message.common.notnull}")
	@NotBlank(message="{module.privacy.message.common.notnull}")
	private String receiver;
	
	private String sender;
	private boolean read;
	private int status;
	private String sendDate;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

}
