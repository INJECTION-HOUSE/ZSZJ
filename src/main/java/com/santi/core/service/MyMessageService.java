package com.santi.core.service;

import java.util.List;

import com.santi.core.datamodel.dto.MyNoticeDto;

public interface MyMessageService {
	
	public List<MyNoticeDto> listMyNotices();

	public void removeNotices(MyNoticeDto[] notices);
	
	public void clearInbox();
}
