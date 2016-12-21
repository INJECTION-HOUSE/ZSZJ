package com.santi.core.common.entity;

public interface DataEntityConstants {
	// back-end administrator console role
	public final static String ROLE_ADMIN_TYPE = "admin";
	public final static String ROLE_OPERATOR_TYPE = "operator";
	// back-end member role
	public final static String ROLE_PERSON_TYPE = "person";
	public final static String ROLE_ENTERPRISE_TYPE = "enterprise";
	public final static String ROLE_UNKNOWN_TYPE = "unknown";
	
	// certification apply status
	public final static int APPLY_STATUS_PENDING = 0; // 申请处理中
	public final static int APPLY_STATUS_PASS = 1; // 认证申请通过
	public final static int APPLY_STATUS_REJECT = 2; // 申请失败
	
	// certification type
	public final static int MEMBER_TYPE_PERSONAL = 0;
	public final static int MEMBER_TYPE_ENTERPRISE = 1;
	
	// 可提现金额
	public final static int MIN_WITHDRAW_CASH = 400;
	public final static int WITHDRAW_STATUS_PENDING = 0;
	public final static int WITHDRAW_STATUS_AUDITED = 1;
	public final static int WITHDRAW_STATUS_PAIED = 2;
	public final static int WITHDRAW_STATUS_REJECT = 3;
	
	// Task Status
	public final static int TASK_STATUS_BEGIN = 0; // default
	public final static int TASK_STATUS_SELECT_BID = 1; // 
	public final static int TASK_STATUS_IN_PROCESS = 2; // 工作中
	public final static int TASK_STATUS_DONE = 3; // 完工
	public final static int TASK_STATUS_CONFIRM_PAY = 4; // 付款
	public final static int TASK_STATUS_FINISH = 5; // 结束
	public final static int TASK_STATUS_ARBITRATE = 6; // 仲裁
	
	// comments satisfaction
	public final static int BID_COMMENTS_SATISFCATION_VERY_GOOD = 3;
	public final static int BID_COMMENTS_SATISFCATION_GOOD = 2;
	public final static int BID_COMMENTS_SATISFCATION_BAD = 1;
}
