package com.santi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.dao.MyMessageDao;
import com.santi.core.datamodel.dto.MyNoticeDto;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.MyMessageService;

@Service
public class MyMessageServiceImpl implements MyMessageService {

	@Autowired
    private MyMessageDao mymessageDao;
	
	@Override
	@Transactional(readOnly = false)
	public List<MyNoticeDto> listMyNotices() {
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		List<MyNoticeDto> mynotices = mymessageDao.getMyNotices(loginInfo.getLoginName());
		return mynotices;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void removeNotices(MyNoticeDto[] notices) {
		for(MyNoticeDto notice : notices) {
			mymessageDao.deleteMyNotice(notice.getId());
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void clearInbox() {
		String cellphone = LoginInfoUtil.getLoginInfo().getLoginUser().getUserAccount();
		mymessageDao.clearInbox(cellphone);
	}

}
