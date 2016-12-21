package com.santi.core.service;

import com.santi.core.datamodel.dto.PageListResultDto;
import com.santi.core.entity.IncomeRecordEntity;
import com.santi.core.param.SearchIncomeParam;

public interface IncomeRecordService {
	
	public IncomeRecordEntity getEntityById(long id);
	
	public void createIncomeEntity(IncomeRecordEntity entity);
	
	public void deleteEntity(long id);
	
	public void updateIncomeRecord(IncomeRecordEntity entity);

	// 全部显示，分页功能
	public PageListResultDto<IncomeRecordEntity> getIncomeRecords(SearchIncomeParam params);

}
