package com.santi.core.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.dao.IncomeRecordDao;
import com.santi.core.datamodel.dto.PageListResultDto;
import com.santi.core.entity.IncomeRecordEntity;
import com.santi.core.param.SearchIncomeParam;
import com.santi.core.service.IncomeRecordService;

@Service
public class IncomeRecordServiceImpl implements IncomeRecordService {
	private static final Logger logger = Logger.getLogger(IncomeRecordServiceImpl.class);
	@Autowired
    private IncomeRecordDao incomeRecordsDao;
	
	@Override
	@Transactional(readOnly = true)
	public IncomeRecordEntity getEntityById(long id) {
		IncomeRecordEntity entity = incomeRecordsDao.getIncomeEntityById(id);
		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public void createIncomeEntity(IncomeRecordEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEntity(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIncomeRecord(IncomeRecordEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = true)
	public PageListResultDto<IncomeRecordEntity> getIncomeRecords(SearchIncomeParam params) {
		Calendar currentDate = Calendar.getInstance();
		int currentMonth = currentDate.get(Calendar.MONTH);
		currentDate.setTimeInMillis(System.currentTimeMillis());
		currentDate.set(Calendar.MONTH, currentMonth-1);
		logger.info("pervious month : " + currentDate.getTime().toString());
		params.setPreviousMonth(String.valueOf(currentDate.getTime().getTime()));
		
		int total = incomeRecordsDao.countRecordByParams(params);
		params.setRowoffset(params.getPageIndex()*10);
		int tt = total - (params.getPageIndex()*10);
		if(tt > 10) {
			params.setRows(10);
		}
		else {
			params.setRows(tt);
		}
		
		List<IncomeRecordEntity> recordList = incomeRecordsDao.getIncomeRecordsInMonth(params);
		PageListResultDto<IncomeRecordEntity> pageResult = new PageListResultDto<IncomeRecordEntity>();
		pageResult.setModel(recordList);
		pageResult.setPageNum(params.getPageIndex()+1); // index base 0
		pageResult.setPageSize(params.getRows());
		pageResult.setTotal(total);
		return pageResult;
	}

}
