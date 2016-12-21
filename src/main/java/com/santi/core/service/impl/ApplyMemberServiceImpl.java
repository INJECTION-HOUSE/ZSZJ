package com.santi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.dao.MemberDao;
import com.santi.core.entity.MemberAuditEntity;
import com.santi.core.entity.MemberEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.ApplyMemberService;
@Service
public class ApplyMemberServiceImpl implements ApplyMemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Override
	@Transactional(readOnly = false)
	public void applyCertification(MemberEntity member) {
		long curtime = System.currentTimeMillis();
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		member.setUpdateTime(String.valueOf(curtime));
		member.setUpdateBy(loginInfo.getLoginName());
		memberDao.editMember(member);
		
		MemberAuditEntity auditInfo = memberDao.getMemberAuditInfoByMemberId(member.getId());
		if(auditInfo == null || auditInfo.getId() == null) {
			auditInfo = new MemberAuditEntity();
			auditInfo.setCreateBy(loginInfo.getLoginUser().getUsername());
			auditInfo.setCreateTime(String.valueOf(curtime));
			auditInfo.setMemberId(member.getId());
		}
		auditInfo.setUpdateBy(loginInfo.getLoginUser().getUsername());
		auditInfo.setUpdateTime(String.valueOf(curtime));
		auditInfo.setCertType(member.getCertificationType());
		auditInfo.setStatus(0);
		auditInfo.setEnterpriseImage(member.getEnterpriseImg());
		auditInfo.setIdcardforntImage(member.getIdencardfgImg());
		auditInfo.setIdcardbackImage(member.getIdencardbgImg());
		memberDao.createMemberAuditInfo(auditInfo);
	}

	@Override
	public MemberAuditEntity queryCertification(long id) {
		MemberAuditEntity auditInfo = memberDao.getMemberAuditInfoByMemberId(id);
		return auditInfo;
	}

}
