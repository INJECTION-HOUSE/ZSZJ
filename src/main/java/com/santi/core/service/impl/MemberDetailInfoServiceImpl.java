package com.santi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.dao.MemberDao;
import com.santi.core.dao.MemberDetailInfoDao;
import com.santi.core.datamodel.dto.PersonDetailInfoDto;
import com.santi.core.entity.MemberDetailInfoEntity;
import com.santi.core.entity.MemberEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.MemberDetailInfoService;

@Service
public class MemberDetailInfoServiceImpl implements MemberDetailInfoService {
	
	
	@Autowired
	private MemberDetailInfoDao detailDao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	@Transactional(readOnly = false)
	public void updateDetailInfo(PersonDetailInfoDto memberDetailInfo) {
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		String cellphone = loginInfo.getLoginName();
		long curtime = System.currentTimeMillis();
		MemberEntity memberEntity = memberDao.getMemberByCellPhone(cellphone);
		MemberDetailInfoEntity detailEntity = detailDao.getDetailInfoByMemberId(memberDetailInfo.getMemeberId());
		// update member
		memberEntity.setQqNumber(memberDetailInfo.getQqNumber());
		memberEntity.setGender(memberDetailInfo.getGender());
		memberDao.editMember(memberEntity);
		
		// update detail info
		detailEntity.setBirthday(memberDetailInfo.getBirthday());
		detailEntity.setContactAddress(memberDetailInfo.getWorkAddress());
		detailEntity.setCurrentCity(memberDetailInfo.getCurrentAddress());
		detailEntity.setInstroduceDesc(memberDetailInfo.getIntrodDesc());
		detailEntity.setOccupy(memberDetailInfo.getOccupy());
		detailEntity.setTelephone(memberDetailInfo.getTelephone());
		detailEntity.setSkillDesc(memberDetailInfo.getSkills());
		detailEntity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		detailEntity.setUpdateTime(String.valueOf(curtime));
		detailDao.updateEntity(detailEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public PersonDetailInfoDto getMemberDetailInfoByMemberId(long memberId) {
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		String cellphone = loginInfo.getLoginName();
		MemberEntity memberEntity = memberDao.getMemberByCellPhone(cellphone);
		MemberDetailInfoEntity detailEntity = detailDao.getDetailInfoByMemberId(memberId);
		// member info
		PersonDetailInfoDto dto = new PersonDetailInfoDto();
		dto.setMemeberId(memberEntity.getId());
		dto.setGender(memberEntity.getGender());
		dto.setQqNumber(memberEntity.getQqNumber());
		dto.setNickname(memberEntity.getNickName());
		// detail info
		dto.setBirthday(detailEntity.getBirthday());
		dto.setCurrentAddress(detailEntity.getCurrentCity());
		dto.setWorkAddress(detailEntity.getContactAddress());
		dto.setId(detailEntity.getId());
		dto.setIntrodDesc(detailEntity.getInstroduceDesc());
		dto.setSkills(detailEntity.getSkillDesc());
		dto.setOccupy(detailEntity.getOccupy());
		dto.setTelephone(detailEntity.getTelephone());
		return dto;
	}

}
