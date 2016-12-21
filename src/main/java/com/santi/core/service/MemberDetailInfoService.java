package com.santi.core.service;

import com.santi.core.datamodel.dto.PersonDetailInfoDto;

public interface MemberDetailInfoService {
	
	public void updateDetailInfo(PersonDetailInfoDto memberDetailInfo);
	
	public PersonDetailInfoDto getMemberDetailInfoByMemberId(long memberId);

}
