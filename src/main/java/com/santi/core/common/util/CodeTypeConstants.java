package com.santi.core.common.util;

import java.util.HashMap;
import java.util.Map;

import com.santi.core.common.entity.DataEntityConstants;

public class CodeTypeConstants {
	
	public static final String CODE_NOTICE_TYPE = "noticeType";
	public static final String CODE_WITHDRAW_TYPE = "withdrawType";
	public static final String CODE_ISSUE_TYPE = "issueType";
	public static final String CODE_ARRIVE_TIME = "arriveTime";
	
	public static Map<Integer,String> getGenderMap() {
		Map<Integer, String> genderMap = new HashMap<Integer, String>();
		genderMap.put(new Integer(0), "男");
		genderMap.put(new Integer(1), "女");
		genderMap.put(new Integer(2), "未知");
		return genderMap;
	}
	
	public static Map<Integer,String> getCertTypeMap() {
		Map<Integer, String> genderMap = new HashMap<Integer, String>();
		genderMap.put(new Integer(0), "个人会员");
		genderMap.put(new Integer(1), "企业会员");
		return genderMap;
	}
	
	public static Map<Integer,String> getPrivacyTypes() {
		Map<Integer, String> pTypeMap = new HashMap<Integer, String>();
		pTypeMap.put(new Integer(0), "会员可见");
		pTypeMap.put(new Integer(1), "本人可见");
		return pTypeMap;
	} 
	
	public static Map<Integer,String> getTaskStatus() {
		Map<Integer, String> pTypeMap = new HashMap<Integer, String>();		
		pTypeMap.put(new Integer(DataEntityConstants.TASK_STATUS_BEGIN), "刚发布");
		pTypeMap.put(new Integer(DataEntityConstants.TASK_STATUS_SELECT_BID), "投标中");
		pTypeMap.put(new Integer(DataEntityConstants.TASK_STATUS_IN_PROCESS), "上门服务中");
		pTypeMap.put(new Integer(DataEntityConstants.TASK_STATUS_DONE), "完工验收");
		pTypeMap.put(new Integer(DataEntityConstants.TASK_STATUS_CONFIRM_PAY), "付款完成");
		pTypeMap.put(new Integer(DataEntityConstants.TASK_STATUS_FINISH), "结束");
		pTypeMap.put(new Integer(DataEntityConstants.TASK_STATUS_ARBITRATE), "纠纷仲裁");
		return pTypeMap;
	}
	
	public static Map<Integer,String> getBidStatus() {
		Map<Integer, String> pTypeMap = new HashMap<Integer, String>();
		pTypeMap.put(new Integer(0), "未投标");
		pTypeMap.put(new Integer(1), "已投标");
		return pTypeMap;
	}
	
	public static Map<Integer,String> getArriveTime() {
		Map<Integer, String> pTypeMap = new HashMap<Integer, String>();
		pTypeMap.put(new Integer(1), "15分钟");
		pTypeMap.put(new Integer(2), "半小时");
		pTypeMap.put(new Integer(3), "1小时");
		pTypeMap.put(new Integer(4), "2小时");
		pTypeMap.put(new Integer(5), "4小时");
		pTypeMap.put(new Integer(6), "当天到");
		return pTypeMap;
	}

}
