package com.santi.core.dao;

import com.santi.core.entity.WeiXinUserEntity;
import com.santi.core.entity.WeiXinUserInfoEntity;

public interface WeiXinUserInfoDao {
	
	public void create(WeiXinUserEntity entity);
	
	public WeiXinUserEntity findByOpenId(String openid);
	
	public void delete(String openid);
	
	public int isExist(String openid);
	
	public void update(WeiXinUserEntity entity);

	public WeiXinUserInfoEntity getUserInfo(String openid);
}
