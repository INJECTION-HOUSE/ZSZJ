package com.santi.core.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.dao.DemoDao;
import com.santi.core.entity.DemoEntity;
import com.santi.core.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService {
	private static final Logger logger = Logger.getLogger(DemoServiceImpl.class);
	@Autowired
    private DemoDao demoDao;
	
	@Override
	@Transactional(readOnly = false)
	public boolean createEntity(DemoEntity entity) {
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DemoEntity> list() {
		List<DemoEntity> entities = demoDao.list();
		logger.info("find demo data list : " + (entities == null ? 0 : entities.size()));
		return entities;
	}

	@Override
	public DemoEntity getEntityById(Long id) {
		DemoEntity entity = demoDao.getEntityByid(id);
		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(DemoEntity entity) {
		demoDao.delete(entity.getId());
		return true;
	}

}
