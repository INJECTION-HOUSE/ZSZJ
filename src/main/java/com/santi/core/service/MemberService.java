package com.santi.core.service;

import java.util.List;

import com.santi.core.common.entity.RespJson;
import com.santi.core.datamodel.dto.MemberAuditDto;
import com.santi.core.entity.MemberAuditEntity;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.UserEntity;
import com.santi.core.param.SearchMemberCheckParam;
import com.santi.core.param.SearchMemberParam;

public interface MemberService {

	public List<MemberEntity> getMemberList(SearchMemberParam param);

	public MemberEntity getMemeberByCellPhone(String cellphone);
	
	public MemberEntity getMemeberByNickName(String nickname);
	
	public MemberEntity getMemberByOpenId(String openId);

	public RespJson createMember(UserEntity userEntity);
	
	public MemberEntity createMember(UserEntity userEntity, String address);

	public void deleteMember(MemberEntity entity);
	
	public MemberEntity getMemberById(long id);

	public void editMember(MemberEntity param);

	public void processAuditionInfo(MemberAuditDto auditInfoDto, boolean pass);

	public List<MemberAuditEntity> getMemberCheckList(SearchMemberCheckParam param);

	public List<MemberAuditDto> getMemberAuditList(SearchMemberCheckParam param);
}
