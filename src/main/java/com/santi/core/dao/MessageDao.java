package com.santi.core.dao;

import java.util.List;

import com.santi.core.entity.MessageEntity;
import com.santi.core.entity.NoticeEntity;
import com.santi.core.entity.NoticeMemberEntity;

public interface MessageDao {

	public List<NoticeEntity> listNotices();

	public void createNotice(NoticeEntity entity);
	
	public int getNumberOfInBoxMessage(String nickname);

	public void deleteNotice(long id);
	
	public NoticeEntity getNoticeByid(long id);
	
	public NoticeEntity getNoticeByuuid(String uuid);
	
	public void deleteNoticeMembersByNoticesId(long id);

	public List<NoticeEntity> getUnreadNotices(String cellphone);

	public void updateNotices(NoticeEntity entity);
	
	public void updateNoticeMember(NoticeMemberEntity noticeMemeberEntity);
	
	public void createNoticeMember(NoticeMemberEntity entity);
	
	public int getNumberOfNotices(String cellphone);
	
	public List<NoticeMemberEntity> getMemberNotices(String cellphone);
	
	// privacy messages
	public void createMessage(MessageEntity entity);
	
	public List<MessageEntity> getInboxMessages(String nickname);
	
	public List<MessageEntity> getOutboxMessages(String nickname);
	
	public void deleteMessages(long id);
	
	public void updateReadStatus(MessageEntity entity);
	
	public MessageEntity getMessageById(long id);
	
}
