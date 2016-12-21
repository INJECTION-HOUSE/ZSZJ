package com.santi.core.service;

import com.santi.core.datamodel.dto.WeiXinUserInfoBean;
import com.santi.core.entity.WeiXinUserEntity;

public interface WeiXinUserInfoService {
	
	public void bindMemberInfo(String openId, String cellphone, String password);
	
	public void createWXUserInfo(WeiXinUserInfoBean dto);
	
	public WeiXinUserEntity findUserByOpenid(String openid);
	
	public boolean isExistUser(String openid);
	
	public void deleteWXUser(String openid);

}
