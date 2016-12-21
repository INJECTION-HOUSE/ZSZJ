package com.santi.core.dao;

import java.util.List;

import com.santi.core.entity.AdminEntity;
import com.santi.core.entity.RoleEntity;
import com.santi.core.entity.UserRoleEntity;

public interface AdminUserDao {

	public AdminEntity getEntityByid(long id);
	
	public AdminEntity getAdminInfoByCellphone(String cellphone);
	
	public List<AdminEntity> list();
	
	public void create(AdminEntity entity);
	
	public void update(AdminEntity entity);
	
	public void delete(long id);
	
	// user - role map relationship
	public List<RoleEntity> getRoles();
	
	public RoleEntity getRoleByType(String type);
	
	public void createUserRole(UserRoleEntity entity);
	
	public UserRoleEntity getUserRole(long userId);
	
	public RoleEntity getRole(long roleId);
	
	public void updateUserRole(UserRoleEntity entity);
}
