package com.santi.core.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.common.entity.DataEntityConstants;
import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.common.util.MD5Util;
import com.santi.core.dao.AdminUserDao;
import com.santi.core.dao.MemberDao;
import com.santi.core.dao.MemberDetailInfoDao;
import com.santi.core.datamodel.dto.MemberAuditDto;
import com.santi.core.entity.MemberAuditEntity;
import com.santi.core.entity.MemberDetailInfoEntity;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.RoleEntity;
import com.santi.core.entity.UserEntity;
import com.santi.core.entity.UserRoleEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.param.SearchMemberCheckParam;
import com.santi.core.param.SearchMemberParam;
import com.santi.core.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	private static final Logger logger = Logger.getLogger(MemberServiceImpl.class);
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private MemberDetailInfoDao memberDetailDao;

	@Autowired
	private AdminUserDao adminDao;

	@Override
	@Transactional(readOnly = true)
	public List<MemberEntity> getMemberList(SearchMemberParam param) {

		return memberDao.getMemberList(param);
	}

	@Override
	@Transactional(readOnly = false)
	public List<MemberAuditEntity> getMemberCheckList(SearchMemberCheckParam param) {
		List<MemberAuditEntity> list = memberDao.getMemberCertificationList(param);
		return list;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MemberAuditDto> getMemberAuditList(SearchMemberCheckParam param) {
		logger.info("list all member audit records...");
		List<MemberAuditDto> list = memberDao.getMemberAuditors(param);
		return list;
	}

	@Override
	@Transactional(readOnly = false)
	public void editMember(MemberEntity member) {
		long curtime = System.currentTimeMillis();
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		member.setUpdateTime(String.valueOf(curtime));
		member.setUpdateBy(loginInfo.getLoginUser().getUsername());
		memberDao.editMember(member);
	}

	@Override
	public void deleteMember(MemberEntity entity) {
		memberDao.delete(entity.getId());
	}

	@Override
	@Transactional(readOnly = false)
	public RespJson createMember(UserEntity userEntity) {
		// check duplicated nickname of member
		UserEntity duplicatedUser = memberDao.isUserExist(userEntity);
		if(duplicatedUser != null && duplicatedUser.getUsername().equals(userEntity.getUsername())) {
			return RespJsonFactory.buildFailure("register.error");
		}
		// try to create member
		RoleEntity roleEntity = adminDao.getRoleByType(DataEntityConstants.ROLE_UNKNOWN_TYPE);
		MemberEntity entity = new MemberEntity();
		long curtime = System.currentTimeMillis();
		entity.setCreateTime(String.valueOf(curtime));
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		entity.setCreateBy(loginInfo.getLoginName());
		entity.setUpdateBy(loginInfo.getLoginName());

		// attributes of member
		entity.setCellphone(userEntity.getUserAccount());
		entity.setNickName(userEntity.getUsername());
		entity.setPassword(MD5Util.getMD5String(userEntity.getPassword()));
		entity.setRole(DataEntityConstants.ROLE_UNKNOWN_TYPE);
		entity.setTotalCash(0);
		entity.setAvaCash(0);
		entity.setLevel(100);
		entity.setVerified(false);
		entity.setAvatarName("picture/profile.jpg");
		entity.setLock(false);
		
		memberDao.createMember(entity);
		entity = memberDao.getMemberByCellPhone(entity.getCellphone());
		
		// create user-role relationship
		UserRoleEntity urbean = new UserRoleEntity();
		urbean.setRoleid(roleEntity.getId());
		urbean.setUserid(entity.getId());
		
		// update create time and creator
		urbean.setCreateTime(String.valueOf(curtime));
		urbean.setUpdateTime(String.valueOf(curtime));
		urbean.setCreateBy(loginInfo.getLoginName());
		urbean.setUpdateBy(loginInfo.getLoginName());
		adminDao.createUserRole(urbean);
		
		// create member detail information
		MemberDetailInfoEntity detailInfoEntity = new MemberDetailInfoEntity();
		detailInfoEntity.setMemberId(entity.getId());
		detailInfoEntity.setCreateBy(loginInfo.getLoginName());
		detailInfoEntity.setUpdateBy(loginInfo.getLoginName());
		detailInfoEntity.setCreateTime(String.valueOf(curtime));
		detailInfoEntity.setUpdateTime(String.valueOf(curtime));
		memberDetailDao.createEntity(detailInfoEntity);
		
		return RespJsonFactory.buildSuccess();
	}

	@Override
	public MemberEntity getMemeberByCellPhone(String cellphone) {
		return memberDao.getMemberByCellPhone(cellphone);
	}

	@Override
	@Transactional(readOnly = false)
	public void processAuditionInfo(MemberAuditDto auditInfoDto, boolean pass) {
		// update audit
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		MemberAuditEntity auditInfo = memberDao.getMemberAuditInfoByMemberId(auditInfoDto.getMemberId());
		auditInfo.setStatus(pass?DataEntityConstants.APPLY_STATUS_PASS : DataEntityConstants.APPLY_STATUS_REJECT);
		auditInfo.setAuditor(loginInfo.getLoginUser().getId());
		auditInfo.setAuditDate(String.valueOf(System.currentTimeMillis()));
		auditInfo.setAuditReason(auditInfoDto.getReasonText());
		memberDao.updateMemberAuditInfo(auditInfo);
		
		// update member if pass the audit
		MemberEntity memberInfo = memberDao.getMemberByCellPhone(auditInfoDto.getMemberCellPhone());
		if(pass) {
			if(auditInfo.getCertType() == DataEntityConstants.MEMBER_TYPE_PERSONAL) {
				memberInfo.setRole(DataEntityConstants.ROLE_PERSON_TYPE);
			}
			if(auditInfo.getCertType() == DataEntityConstants.MEMBER_TYPE_ENTERPRISE) {
				memberInfo.setRole(DataEntityConstants.ROLE_ENTERPRISE_TYPE);
			}
			memberInfo.setUpdateBy(loginInfo.getLoginUser().getUsername());
			memberInfo.setUpdateTime(String.valueOf(System.currentTimeMillis()));
			memberInfo.setVerified(true);
			memberDao.editMember(memberInfo);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public MemberEntity getMemberById(long id) {
		return memberDao.findMemberById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public MemberEntity createMember(UserEntity userEntity, String address) {
		logger.info("wether create member from weixin client user info or not...");
		MemberEntity entity = memberDao.findMemberByOpenId(userEntity.getOpenId());
		if(entity == null || entity.getNickName() == null) {
			// try to create member
			RoleEntity roleEntity = adminDao.getRoleByType(DataEntityConstants.ROLE_UNKNOWN_TYPE);
			entity = new MemberEntity();
			long curtime = System.currentTimeMillis();
			entity.setCreateTime(String.valueOf(curtime));
			entity.setUpdateTime(String.valueOf(curtime));
			LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
			entity.setCreateBy(loginInfo.getLoginName());
			entity.setUpdateBy(loginInfo.getLoginName());

			// attributes of member
			entity.setCellphone(userEntity.getUserAccount());
			entity.setNickName(userEntity.getUsername());
			entity.setPassword(MD5Util.getMD5String(userEntity.getPassword()));
			entity.setRole(DataEntityConstants.ROLE_UNKNOWN_TYPE);
			entity.setTotalCash(0);
			entity.setAvaCash(0);
			entity.setLevel(100);
			entity.setWeixinId(userEntity.getOpenId());
			entity.setVerified(false);
			entity.setAvatarName("picture/profile.jpg");
			entity.setLock(false);
			
			memberDao.createMember(entity);
			entity = memberDao.getMemberByCellPhone(entity.getCellphone());
			
			// create user-role relationship
			UserRoleEntity urbean = new UserRoleEntity();
			urbean.setRoleid(roleEntity.getId());
			urbean.setUserid(entity.getId());
			
			// update create time and creator
			urbean.setCreateTime(String.valueOf(curtime));
			urbean.setUpdateTime(String.valueOf(curtime));
			urbean.setCreateBy(loginInfo.getLoginName());
			urbean.setUpdateBy(loginInfo.getLoginName());
			adminDao.createUserRole(urbean);
			
			// create member detail information
			MemberDetailInfoEntity detailInfoEntity = new MemberDetailInfoEntity();
			detailInfoEntity.setMemberId(entity.getId());
			detailInfoEntity.setContactAddress(address);
			detailInfoEntity.setCreateBy(loginInfo.getLoginName());
			detailInfoEntity.setUpdateBy(loginInfo.getLoginName());
			detailInfoEntity.setCreateTime(String.valueOf(curtime));
			detailInfoEntity.setUpdateTime(String.valueOf(curtime));
			memberDetailDao.createEntity(detailInfoEntity);
		}
		return entity; 
	}

	@Override
	@Transactional(readOnly = true)
	public MemberEntity getMemberByOpenId(String openId) {
		return memberDao.findMemberByOpenId(openId);
	}

	@Override
	@Transactional(readOnly = true)
	public MemberEntity getMemeberByNickName(String nickname) {
		return memberDao.getMemberByNickName(nickname);
	}

	@Override
	public int getBalance(int id) {
		return memberDao.getBalance(id);
	}

	@Override
	public void addTotalCash(HashMap<String, Integer> map) {
		memberDao.addTotalCash(map);
	}

}
