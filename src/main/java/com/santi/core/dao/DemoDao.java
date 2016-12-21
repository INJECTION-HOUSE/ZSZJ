package com.santi.core.dao;

import java.util.List;

import com.santi.core.entity.DemoEntity;
public interface DemoDao {
	
		List<DemoEntity> list();
		
		public void delete(long id);
		
		public DemoEntity getEntityByid(long id);
}
