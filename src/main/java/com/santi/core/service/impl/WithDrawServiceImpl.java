package com.santi.core.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.common.entity.DataEntityConstants;
import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.dao.MemberDao;
import com.santi.core.dao.WithDrawDao;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.WithDrawEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.param.SearchWithDrawParam;
import com.santi.core.service.WithDrawService;

@Service
public class WithDrawServiceImpl implements WithDrawService {
	
	
	private static final Logger logger = Logger.getLogger(WithDrawServiceImpl.class);
	
	@Autowired
    private WithDrawDao withDrawDao;
	
	@Autowired
	private MemberDao memberDao;

	@Override
	@Transactional(readOnly = true)
	public	List<WithDrawEntity> getWithDrawList(SearchWithDrawParam param)  {
		logger.info("get withdraw applicant list by status...");
		return withDrawDao.getWithDrawList(param);
	}

	
	@Override
	@Transactional(readOnly = false)
	public	void payOffLine(WithDrawEntity entity)  {
		logger.info("payOffLine by id : " + entity.getId());
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		long curtime = System.currentTimeMillis();
		entity.setPayTime(String.valueOf(curtime));
		entity.setPayor(loginInfo.getLoginUser().getUsername());
		entity.setUpdateTime(String.valueOf(curtime));
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		withDrawDao.updateEntity(entity);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void checkWithDraw(WithDrawEntity entity)  {
		logger.info("checkWithDraw entity by id : " + entity.getId());
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		long curtime = System.currentTimeMillis();
		entity.setCheckDate(String.valueOf(curtime));
		entity.setAuditor(loginInfo.getLoginUser().getUsername());
		entity.setUpdateTime(String.valueOf(curtime));
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		withDrawDao.updateEntity(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public WithDrawEntity findEntityById(long id) {
		logger.info("find withdraw entity by id : " + id);
		return withDrawDao.findEntityById(id);
	}

	@Override
	public RespJson applyWithDraw() {
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		logger.info("apply income withdraw : " + loginInfo.getLoginUser().getUsername());
		MemberEntity memberEntity = memberDao.getMemberByCellPhone(loginInfo.getLoginName());
		Integer totalcash = memberEntity.getTotalCash();
		if(totalcash != null && totalcash.intValue() > DataEntityConstants.MIN_WITHDRAW_CASH) {
			// create withdraw record
			Integer avacash = memberEntity.getAvaCash();
			if(avacash == null || avacash.intValue() <= DataEntityConstants.MIN_WITHDRAW_CASH) {
				return RespJsonFactory.buildFailure("module.withdraw.apply.error");
			}
			long curtime = System.currentTimeMillis();
			WithDrawEntity entity = new WithDrawEntity();
			entity.setMemberId(loginInfo.getLoginUser().getId());
			entity.setWithdrawMoney(avacash);
			entity.setStatus(DataEntityConstants.WITHDRAW_STATUS_PENDING);
			entity.setDesc("提现申请单");
			entity.setWithdrawTime(String.valueOf(curtime));
			entity.setCreateTime(String.valueOf(curtime));
			entity.setUpdateTime(String.valueOf(curtime));
			entity.setCreateBy(loginInfo.getLoginUser().getUsername());
			entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
			withDrawDao.createEntity(entity);
			
			// update member avail cash to zero
			memberEntity.setAvaCash(0);
			memberEntity.setUpdateBy(loginInfo.getLoginUser().getUsername());
			memberEntity.setUpdateTime(String.valueOf(curtime));
			memberDao.editMember(memberEntity);
			// end by zhigang
			return RespJsonFactory.buildSuccess();
		} else {
			return RespJsonFactory.buildFailure("module.withdraw.apply.error");
		}
		
	}
}
