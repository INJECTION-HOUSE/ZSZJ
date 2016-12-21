package com.santi.core.dao;

import java.util.List;

import com.santi.core.entity.CodeTypeEntity;

public interface CodeTypeDao {
	
	public List<CodeTypeEntity> list();
	
	public void delete(long id);
	
	public CodeTypeEntity getEntityByid(long id);
	
	public void updateType(CodeTypeEntity entity);
	
	public void create(CodeTypeEntity entity);
}
