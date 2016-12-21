package com.santi.core.service;

import java.util.List;

import com.santi.core.entity.MessageEntity;
import com.santi.core.entity.NoticeEntity;

public interface MessageService {
	
	public List<NoticeEntity> listNotices();
	
	public List<MessageEntity> listOutBoxMsg(String nickname);
	
	public List<MessageEntity> listInBoxMsg(String nickname);
	
	public int countInBoxMsg(String nickname);
	
	public MessageEntity getMessageById(long id);
	
	public void createMessage(MessageEntity entity);
	
	public void sendSystemMessage(MessageEntity entity);
	
	public void deleteMessage(MessageEntity entity);
	
	public void createNotice(NoticeEntity entity);
	
	public void deleteNotice(NoticeEntity entity);
	
	public void updateReadStatus(MessageEntity entity);
	
	public NoticeEntity findNoticeById(long id);
	
	public List<NoticeEntity> getUnreadNotices(String cellphone);
	
	public void updateNotices(NoticeEntity entity);

}
