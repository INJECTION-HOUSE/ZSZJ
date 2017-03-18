package com.santi.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.santi.core.datamodel.dto.MemberAuditDto;
import com.santi.core.entity.MemberAuditEntity;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.UserEntity;
import com.santi.core.param.ResetPasswordParam;
import com.santi.core.param.SearchMemberCheckParam;
import com.santi.core.param.SearchMemberParam;

public interface MemberDao {
	
	public MemberEntity findMemberByOpenId(String openId);

	public void delete(long id);
	
	public void createMemberAuditInfo(MemberAuditEntity memberCertInfo);
	
	public MemberAuditEntity getMemberAuditInfoByMemberId(long id);
	
	public void updateMemberAuditInfo(MemberAuditEntity entity);
	
	public List<MemberEntity> getMemberList(SearchMemberParam param);

	public List<MemberAuditEntity> getMemberCertificationList(SearchMemberCheckParam param) ;
	
	public void createMember(MemberEntity member);
	
	public MemberEntity getMemberByCellPhone(String cellphone);
	
	public MemberEntity findMemberById(long id);
	
	public List<MemberAuditDto> getMemberAuditors(SearchMemberCheckParam searchParams);
	
	/***
	 * 检测是否是合法用户或者重复
	 * @param user
	 * @return
	 */
	UserEntity isUserExist(UserEntity user);
	/**
	 * 验证用户登陆
	 * @param param - userAccount
	 * @return
	 */
	UserEntity findUser(Map<String, Object> param);
	
	/**
	 * 更新会员密码
	 * @param param
	 */
	void updatePassword(Map<String, Object> param);
	
	/**
	 * 更新会员信息
	 * @param param
	 */
	void editMember(MemberEntity param);
	
	/**
	 * 获取用户姓名
	 */
	public List<UserEntity> findUserName();
	
	public MemberEntity getMemberByNickName(String nickname);

	public void resetPassword(ResetPasswordParam param);

	public int getBalance(int id);

	public void addTotalCash(HashMap<String, Integer> map);
}
