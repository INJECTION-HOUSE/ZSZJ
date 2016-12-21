package com.santi.core.global.common;

public interface ApplicationConstants {
	public static final String APP_BINDING_TOKEN = "zhusuhome";
	public static final String WEIXIN_WELCOME_MSG = "欢迎关注注塑之家!";
	// 应用ID
	public static final String APP_ID = "wx6962b52069f38239";
	// 应用密钥
	public static final String APP_SECRET = "8eaca07f7766d6920ed24a2fe4b9b12d";
	
	
	
	public static final String SYSTEM_MSG_TYPE = "系统通知";
	
	public static final String SUCCESS_MSG_KEY = "successInfo";
	
	// 系统中标信息
	public static final String BID_SELECTED_MSG_CONTENT = "维修 工程师  {P} 您已经中标，请联系厂家确认上门时间，了解故障细节 - 任务编号 {ORDER}";
	public static final String SYSTEM_MSG_TITLE = "系统通知-中标";
	
	// 系统完工信息
	public static final String FINISH_TASK_MSG_CONTENT = "维修 工程师 {P}已经完成维修任务 ，申请付款 。任务编号 - {ORDER}";
	public static final String FINISH_TASK_MSG_TITLE = "系统通知-申请付款";
	
	// 雇主评价
	public static final String COMMENTS_TASK_MSG_CONTENT = "雇主 :P 已经付款 ，恭喜你！ 。任务编号 - {ORDER}";
	public static final String COMMENTS_TASK_MSG_TITLE = "系统通知-雇主评价";
	
	// 返工消息
	public static final String REWORK_TASK_MSG_CONTENT = "维修 工程师 {P}您的维修工作没有完成。任务编号 - {ORDER}";
	public static final String SYSTEM_REWORK_MSG_TITLE = "系统通知-没有完成维修";

}
