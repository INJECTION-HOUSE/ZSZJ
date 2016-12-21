package com.santi.core.common.entity;

import com.santi.core.common.messages.Global;

/**
 * RespJson工厂类
 * @author hl
 *
 */
public class RespJsonFactory {

	public static final Integer SUCCESS = 1;
	
	public static final Integer FAIL = 0;
	
	public static RespJson buildSuccess(){
		return buildSuccess(null);
	}
	
	public static RespJson buildSuccess(Object data){
		RespJson respJson = new RespJson();
		respJson.setResult(SUCCESS);
		respJson.setMsg(Global.getMessage("success"));
		respJson.setData(data);
		return respJson;
	}
	
	public static RespJson buildFailure(){
		return buildFailure("fail");
	}
	
	public static RespJson buildFailure(String msg){
		return buildFailure(null, msg);
	}

	public static RespJson buildFailure(Object data,String msg){
		RespJson respJson = new RespJson();
		respJson.setResult(FAIL);
		respJson.setMsg(Global.getMessage(msg));
		respJson.setData(data);
		return respJson;
	}
	
	
}
