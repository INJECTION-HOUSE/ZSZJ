package com.santi.core.dao;

import java.util.List;

import com.santi.core.entity.WithDrawEntity;
import com.santi.core.param.SearchWithDrawParam;

public interface WithDrawDao {
 
	List<WithDrawEntity> getWithDrawList(SearchWithDrawParam param) ;
	
	public void updateEntity(WithDrawEntity param) ;
	
	public WithDrawEntity findEntityById(long id);
	
	public void createEntity(WithDrawEntity entity);

}
