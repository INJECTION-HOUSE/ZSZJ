package com.santi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.dao.AdminUserDao;
import com.santi.core.entity.AdminEntity;
import com.santi.core.entity.RoleEntity;
import com.santi.core.entity.UserRoleEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.AdminUserService;

@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
    private AdminUserDao adminDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public AdminEntity getEntityByid(long id) {
		return adminDao.getEntityByid(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AdminEntity> list() {
		return adminDao.list();
	}

	@Override
	@Transactional(readOnly = false)
	public void create(AdminEntity entity) {
		RoleEntity roleEntity = adminDao.getRoleByType(entity.getRole());

		long curtime = System.currentTimeMillis();
		entity.setCreateTime(String.valueOf(curtime));
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();

		entity.setCreateBy(loginInfo.getLoginUser().getUsername());
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		adminDao.create(entity);
		entity = adminDao.getAdminInfoByCellphone(entity.getCellphone());
		
		// create user-role relationship
		UserRoleEntity urbean = new UserRoleEntity();
		urbean.setRoleid(roleEntity.getId());
		urbean.setUserid(entity.getId());
		
		// update create time and creator
		urbean.setCreateTime(String.valueOf(curtime));
		urbean.setUpdateTime(String.valueOf(curtime));

		urbean.setCreateBy(loginInfo.getLoginUser().getUsername());
		urbean.setUpdateBy(loginInfo.getLoginUser().getUsername());
		adminDao.createUserRole(urbean);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(AdminEntity entity) {
		long curtime = System.currentTimeMillis();
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		adminDao.update(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(AdminEntity entity) {
		adminDao.delete(entity.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleEntity> getRoleList() {
		return adminDao.getRoles();
	}

}
