package com.santi.core.service;

import com.santi.core.entity.MemberAuditEntity;
import com.santi.core.entity.MemberEntity;

public interface ApplyMemberService {
		
	public void applyCertification(MemberEntity entity);
	
	public MemberAuditEntity queryCertification(long id);
}
