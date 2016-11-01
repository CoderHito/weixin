package com.cf.cfsecurity.constant;

public class DictConstant {
	//是否可用 enable 0001  zll
	public static String DISABLED = "0";//否
	public static String ENABLED = "1";//是
	
	//用户表状态 status 0002  zll
	public static String USER_STATUS_NORMAL = "0";//正常
	public static String USER_STATUS_LOCKED = "1";//锁定
	
	//登录 日志类型  0003  zll
	public static String AUDIT_TYPE_LOGIN = "1";//登录
	public static String AUDIT_TYPE_LOGOUT = "2";//退出
	//登录 登录来源 0007  zll
	public static String SOURCE_TYPE_USER = "1";//用户登录
	public static String SOURCE_TYPE_OPR = "2";//操作员登录
	
	//交易状态 0302
	public static String TRADE_STATUS_UNCHECK = "00";//待审核
	public static String TRADE_STATUS_REFUSE = "01";//审核拒绝
	public static String TRADE_STATUS_WAIT_SEND = "02";//待发送
	public static String TRADE_STATUS_SENT = "03";//已发送
	public static String TRADE_STATUS_RESPONE = "04";//已应答
	public static String TRADE_STATUS_FAILURE = "06";//交易失败
	public static String TRADE_STATUS_SUCCESS = "07";//交易成功
	public static String TRADE_STATUS_PROCESSING = "10";//交易处理中
	
	//操作员确认结果
	public static String OPR_RESULT_FIALURE = "0";//失败
	public static String OPR_RESULT_SUCCESS = "1";//成功
	
	//审核状态 zll
	public static String CHECK_STATUS_UN = "0";//待审核
	public static String CHECK_STATUS_OK = "1";//审核通过
	public static String CHECK_STATUS_REFUSE = "2";//审核拒绝
	
	//账户状态  zll
	public static String ACC_STATUS_NORMAL = "00";//正常
	public static String ACC_STATUS_FREEZE = "01";//冻结
	public static String ACC_STATUS_CANCEL = "02";//注销
	
	//账户状态xuym
	public static String CUST_STATUS_NORMAL = "00";//正常
	public static String CUST_STATUS_LOCK = "01";//锁定
	public static String CUST_STATUS_CANCEL  = "02";//注销
	
	//账户审核状态xuym
	public static String CUST_CHECKSTATUS_NO = "0";//未审核
	public static String CUST_CHECKSTATUS_PRE= "1";//待审核
	public static String CUST_CHECKSTATUS_SUCESS = "2";//审核通过
	public static String CUST_CHECKSTATUS_CANCEL  = "3";//审核不通过
	
}
