package com.santi.core.service;

import java.util.List;

import com.santi.core.entity.AdminEntity;
import com.santi.core.entity.RoleEntity;

public interface AdminUserService {
	
	public AdminEntity getEntityByid(long id);
	
	public List<AdminEntity> list();
	
	public void create(AdminEntity user);
	
	public void update(AdminEntity entity);
	
	public void delete(AdminEntity entity);
	
	public List<RoleEntity> getRoleList();
	
}
