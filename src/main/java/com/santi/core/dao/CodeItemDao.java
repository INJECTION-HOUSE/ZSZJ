package com.santi.core.dao;

import java.util.List;
import java.util.Map;

import com.santi.core.entity.CodeItemEntity;

public interface CodeItemDao {
	
	/***
	 * support search with multiple conditions
	 * @param param
	 * @return
	 */
	public List<CodeItemEntity> list(Map<String,Object> param);

	public void delete(long id);

	public CodeItemEntity getEntityByid(long id);

	public void updateItem(CodeItemEntity entity);

	public void create(CodeItemEntity entity);
}
