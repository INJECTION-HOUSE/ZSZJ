package com.santi.core.common.entity;

import java.io.Serializable;

public class RespJson implements Serializable{

	public static final Integer SUCCESS = 1;
	public static final Integer FAIL = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2958418908400447774L;

	private Integer result;
	private Integer code;
	private String msg;
	private Object data;
	
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public boolean isSuccess(){
		return result==null?false: (result.intValue() == SUCCESS);
	}
	
}
