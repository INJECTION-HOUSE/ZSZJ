package com.santi.core.dao;

import com.santi.core.entity.MemberDetailInfoEntity;

public interface MemberDetailInfoDao {
	
	public void createEntity(MemberDetailInfoEntity entity);
	
	public void updateEntity(MemberDetailInfoEntity entity);
	
	public MemberDetailInfoEntity getDetailInfoByMemberId(long memberId);
	
	public void deleteEntity(long memberId);

}
