package com.santi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.common.entity.DataEntityConstants;
import com.santi.core.common.util.MD5Util;
import com.santi.core.dao.AdminUserDao;
import com.santi.core.dao.MemberDao;
import com.santi.core.dao.MemberDetailInfoDao;
import com.santi.core.dao.WeiXinUserInfoDao;
import com.santi.core.datamodel.dto.WeiXinUserInfoBean;
import com.santi.core.entity.MemberDetailInfoEntity;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.RoleEntity;
import com.santi.core.entity.UserRoleEntity;
import com.santi.core.entity.WeiXinUserEntity;
import com.santi.core.entity.WeiXinUserInfoEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.WeiXinUserInfoService;

@Service
public class WeiXinUserInfoServiceImpl implements WeiXinUserInfoService {

	
	@Autowired
    private WeiXinUserInfoDao userInfoDao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private MemberDetailInfoDao memberDetailDao;

	@Autowired
	private AdminUserDao adminDao;
	
	@Override
	@Transactional(readOnly = false)
	public void bindMemberInfo(String openId, String cellphone, String password) {
		MemberEntity entity = memberDao.getMemberByCellPhone(cellphone);
		WeiXinUserEntity wxuser = userInfoDao.findByOpenId(openId);
		if(entity != null) {
			entity.setWeixinId(openId);
			memberDao.editMember(entity);
		} else {
			RoleEntity roleEntity = adminDao.getRoleByType(DataEntityConstants.ROLE_UNKNOWN_TYPE);
			entity = new MemberEntity();
			long curtime = System.currentTimeMillis();
			entity.setCreateTime(String.valueOf(curtime));
			entity.setUpdateTime(String.valueOf(curtime));
			LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
			entity.setCreateBy(loginInfo.getLoginName());
			entity.setUpdateBy(loginInfo.getLoginName());

			// attributes of member
			entity.setCellphone(cellphone);
			entity.setNickName(wxuser.getNickname());
			entity.setPassword(MD5Util.getMD5String(password));
			entity.setRole(DataEntityConstants.ROLE_UNKNOWN_TYPE);
			entity.setTotalCash(0);
			entity.setAvaCash(0);
			entity.setLevel(100);
			entity.setWeixinId(wxuser.getOpenid());
			entity.setVerified(false);
			entity.setAvatarName(wxuser.getHeadimgurl());
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
			detailInfoEntity.setContactAddress(wxuser.getAddress());
			detailInfoEntity.setCreateBy(loginInfo.getLoginName());
			detailInfoEntity.setUpdateBy(loginInfo.getLoginName());
			detailInfoEntity.setCreateTime(String.valueOf(curtime));
			detailInfoEntity.setUpdateTime(String.valueOf(curtime));
			memberDetailDao.createEntity(detailInfoEntity);
		}
		
		// post update weixin user information
		wxuser.setValid(true);
		userInfoDao.update(wxuser);
		
	}

	@Override
	@Transactional(readOnly = false)
	public void createWXUserInfo(WeiXinUserInfoBean dto) {
		WeiXinUserEntity entity = new WeiXinUserEntity();
		entity.setAddress(dto.getCountry() + " " + dto.getProvince() + " " + dto.getCity());
		entity.setOpenid(dto.getOpenid());
		entity.setNickname(dto.getNickname());
		entity.setValid(false);
		entity.setHeadimgurl(dto.getHeadimgurl());
		entity.setGender(Integer.parseInt(dto.getSex()));

		long curtime = System.currentTimeMillis();
		entity.setCreateTime(String.valueOf(curtime));
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		entity.setCreateBy(loginInfo.getLoginUser().getUsername());
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		userInfoDao.create(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public WeiXinUserEntity findUserByOpenid(String openid) {
		WeiXinUserEntity entity = userInfoDao.findByOpenId(openid);
		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isExistUser(String openid) {
		int count = userInfoDao.isExist(openid);
		return (count == 1);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteWXUser(String openid) {
		userInfoDao.delete(openid);
	}

	@Override
	@Transactional(readOnly = true)
	public WeiXinUserInfoEntity getUserInfo(String openid) {
		return userInfoDao.getUserInfo(openid);
	}

}
