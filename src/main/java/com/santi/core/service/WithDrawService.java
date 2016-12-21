package com.santi.core.service;

import java.util.List;

import com.santi.core.common.entity.RespJson;
import com.santi.core.entity.WithDrawEntity;
import com.santi.core.param.SearchWithDrawParam;

public interface WithDrawService {
	 
	public	List<WithDrawEntity> getWithDrawList(SearchWithDrawParam param) ;

	public void payOffLine(WithDrawEntity entity);
	
	public void checkWithDraw(WithDrawEntity entity);
	
	public WithDrawEntity findEntityById(long id);
	
	public RespJson applyWithDraw();

}
