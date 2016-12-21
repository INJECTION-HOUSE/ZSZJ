package com.santi.core.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.dao.CodeItemDao;
import com.santi.core.dao.CodeTypeDao;
import com.santi.core.entity.CodeItemEntity;
import com.santi.core.entity.CodeTypeEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.CodeService;

@Service
public class CodeServiceImpl implements CodeService {
	private static final Logger logger = Logger.getLogger(CodeServiceImpl.class);
	
	
	@Autowired
    private CodeTypeDao codeTypeDao;
	
	@Autowired
    private CodeItemDao codeItemDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<CodeTypeEntity> listTypes() {
		logger.info("List all code type from service layer");
		return codeTypeDao.list();
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteType(CodeTypeEntity entity) {
		if(entity == null) 
			return;
		codeTypeDao.delete(entity.getId());
	}

	@Override
	@Transactional(readOnly = false)
	public void createType(CodeTypeEntity entity) {
		long curtime = System.currentTimeMillis();
		entity.setCreateTime(String.valueOf(curtime));
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		entity.setCreateBy(loginInfo.getLoginUser().getUsername());
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		codeTypeDao.create(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateType(CodeTypeEntity entity) {
		long curtime = System.currentTimeMillis();
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		codeTypeDao.updateType(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public CodeTypeEntity findCodeTypeById(Integer id) {
		logger.info("try to find code type record by id : " + id);
		return codeTypeDao.getEntityByid(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CodeItemEntity> listCodeItems(Map<String, Object> paramMap) {
		logger.info("List all code items from service layer");
		return codeItemDao.list(paramMap);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteCodeItem(CodeItemEntity entity) {
		logger.info("delete selected code item by id :" + entity.getId());
		codeItemDao.delete(entity.getId());	
	}

	@Override
	@Transactional(readOnly = false)
	public void createCodeItem(CodeItemEntity entity) {
		long curtime = System.currentTimeMillis();
		entity.setCreateTime(String.valueOf(curtime));
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		entity.setCreateBy(loginInfo.getLoginUser().getUsername());
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		codeItemDao.create(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public CodeItemEntity findCodeItemByid(Integer id) {
		logger.info("try to find code item record by id : " + id);
		return codeItemDao.getEntityByid(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateCodeItem(CodeItemEntity entity) {
		long curtime = System.currentTimeMillis();
		entity.setUpdateTime(String.valueOf(curtime));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		codeItemDao.updateItem(entity);
	}

}
