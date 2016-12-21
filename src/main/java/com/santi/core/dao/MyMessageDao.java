package com.santi.core.dao;

import java.util.List;

import com.santi.core.datamodel.dto.MyNoticeDto;

public interface MyMessageDao {
	
	public List<MyNoticeDto> getMyNotices(String cellphone);
	
	public void updateNoticeReadStatus(MyNoticeDto dto);
	
	public void deleteMyNotice(long memberNoticeId);
	
	public void clearInbox(String cellphone);

}
