package com.santi.core.dao;

import java.util.List;

import com.santi.core.entity.IncomeRecordEntity;
import com.santi.core.param.SearchIncomeParam;

public interface IncomeRecordDao {
	
	public IncomeRecordEntity getIncomeEntityById(long id);
	
	public void createIncomeEntity(IncomeRecordEntity entity);
	
	public void deleteEntity(long id);
	
	public void updateIncomeRecord(IncomeRecordEntity entity);
	
	public int countRecordByParams(SearchIncomeParam params);

	// 全部显示，分页功能
	public List<IncomeRecordEntity> getIncomeRecordsInMonth(SearchIncomeParam params);
	
}
