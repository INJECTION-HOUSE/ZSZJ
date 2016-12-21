package com.santi.core.service;

import java.util.List;

import com.santi.core.entity.DemoEntity;

public interface DemoService {
	
	public boolean createEntity(DemoEntity entity);
	
	public List<DemoEntity> list();
	
	public DemoEntity getEntityById(Long id);
	
	public boolean delete(DemoEntity entity);

}
