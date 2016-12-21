package com.santi.core.service;

import java.util.List;
import java.util.Map;

import com.santi.core.entity.CodeItemEntity;
import com.santi.core.entity.CodeTypeEntity;

public interface CodeService {
	
	public List<CodeTypeEntity> listTypes();
	
	public void deleteType(CodeTypeEntity entity);
	
	public void createType(CodeTypeEntity entity);
	
	public CodeTypeEntity findCodeTypeById(Integer id);
	
	public void updateType(CodeTypeEntity entity);
	
	// code item entity service
	public List<CodeItemEntity> listCodeItems(Map<String, Object> paramMap);
	
	public void deleteCodeItem(CodeItemEntity entity);
	
	public void createCodeItem(CodeItemEntity entity);
	
	public CodeItemEntity findCodeItemByid(Integer id);
	
	public void updateCodeItem(CodeItemEntity entity);

}
