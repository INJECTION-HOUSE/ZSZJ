package com.santi.core.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.common.util.UUIDUtil;
import com.santi.core.dao.MemberDao;
import com.santi.core.dao.MessageDao;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.MessageEntity;
import com.santi.core.entity.NoticeEntity;
import com.santi.core.entity.NoticeMemberEntity;
import com.santi.core.entity.UserEntity;
import com.santi.core.global.common.ApplicationConstants;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.param.SearchMemberParam;
import com.santi.core.service.MessageService;


@Service
public class MessageServiceImpl implements MessageService {

	private static final Logger logger = Logger.getLogger(MessageServiceImpl.class);
	
	@Autowired
    private MessageDao messageDao;
	
	@Autowired
    private MemberDao memberDao;
	
	@Override
	public List<NoticeEntity> listNotices() {
		return messageDao.listNotices();
	}

	@Override
	@Transactional(readOnly = false)
	public void createNotice(NoticeEntity entity) {
		long curtime = System.currentTimeMillis();
		entity.setCreateTime(String.valueOf(curtime));
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		entity.setCreateBy(loginInfo.getLoginUser().getUsername());
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		entity.setGenerateTime(String.valueOf(curtime));
		entity.setUuid(UUIDUtil.uuid());
		messageDao.createNotice(entity);
		
		// send to each member
		entity = messageDao.getNoticeByuuid(entity.getUuid());
		SearchMemberParam  params = new SearchMemberParam();
		List<MemberEntity> members = memberDao.getMemberList(params);
		for(MemberEntity member : members) {
			NoticeMemberEntity noticeMemeber = new NoticeMemberEntity();
			noticeMemeber.setCellPhone(member.getCellphone());
			noticeMemeber.setRead(false);
			noticeMemeber.setNoticeId(entity.getId());
			noticeMemeber.setCreateBy(loginInfo.getLoginUser().getUsername());
			noticeMemeber.setUpdateBy(loginInfo.getLoginUser().getUsername());
			noticeMemeber.setCreateTime(String.valueOf(curtime));
			noticeMemeber.setUpdateTime(String.valueOf(curtime));
			messageDao.createNoticeMember(noticeMemeber);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteNotice(NoticeEntity entity) {
		messageDao.deleteNoticeMembersByNoticesId(entity.getId());
		messageDao.deleteNotice(entity.getId());
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<NoticeEntity> getUnreadNotices(String cellphone) {
		return messageDao.getUnreadNotices(cellphone);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateNotices(NoticeEntity entity) {
		long curtime = System.currentTimeMillis();
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		messageDao.updateNotices(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public NoticeEntity findNoticeById(long id) {
		return messageDao.getNoticeByid(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MessageEntity> listOutBoxMsg(String nickname) {
		return messageDao.getOutboxMessages(nickname);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MessageEntity> listInBoxMsg(String nickname) {
		return messageDao.getInboxMessages(nickname);
	}

	@Override
	@Transactional(readOnly = false)
	public void createMessage(MessageEntity entity) {
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		UserEntity receiverInfo = new UserEntity();
		receiverInfo.setUsername(entity.getReceiver());
		UserEntity existedUser = memberDao.isUserExist(receiverInfo);
		if(existedUser.getCreateBy() == null) {
			logger.info("invalid member nickname, could not send any privacy messages by : " + loginInfo.getLoginName());
			return;
		}
		
		long curtime = System.currentTimeMillis();
		entity.setCreateTime(String.valueOf(curtime));
		entity.setUpdateTime(String.valueOf(curtime));
		entity.setCreateBy(loginInfo.getLoginUser().getUsername());
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		
		// TODO:zhigang 
		entity.setSendDate(String.valueOf(curtime));
		entity.setStatus(0);
		entity.setRead(false);
		entity.setSender(loginInfo.getLoginUser().getUsername());
		// privacy message
		entity.setType(1); 
		messageDao.createMessage(entity);
		
	}
	
	@Override
	@Transactional(readOnly = false)
	public void sendSystemMessage(MessageEntity entity) {
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		UserEntity receiverInfo = new UserEntity();
		receiverInfo.setUsername(entity.getReceiver());
		UserEntity existedUser = memberDao.isUserExist(receiverInfo);
		if(existedUser.getCreateBy() == null) {
			logger.info("invalid member nickname, could not send any privacy messages by : " + loginInfo.getLoginName());
			return;
		}
		
		long curtime = System.currentTimeMillis();
		entity.setCreateTime(String.valueOf(curtime));
		entity.setUpdateTime(String.valueOf(curtime));
		entity.setCreateBy(loginInfo.getLoginUser().getUsername());
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		
		// TODO:zhigang 
		entity.setSendDate(String.valueOf(curtime));
		entity.setStatus(0);
		entity.setRead(false);
		entity.setSender(ApplicationConstants.SYSTEM_MSG_TYPE);
		// privacy message
		entity.setType(1); 
		messageDao.createMessage(entity);
		
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteMessage(MessageEntity entity) {
		logger.info("remove message by id : " + entity.getId());
		messageDao.deleteMessages(entity.getId());
	}

	@Override
	@Transactional(readOnly = false)
	public void updateReadStatus(MessageEntity entity) {
		logger.info("update read status message by id : " + entity.getId());
		entity.setRead(true);
		messageDao.updateReadStatus(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public MessageEntity getMessageById(long id) {
		logger.info("read message by id : " + id);
		return messageDao.getMessageById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public int countInBoxMsg(String nickname) {
		logger.info("get all messages inside inbox");
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		int count1 =  messageDao.getNumberOfInBoxMessage(nickname);
		int count2 = messageDao.getNumberOfNotices(loginInfo.getLoginName());
		return (count1+count2);
	}

}
