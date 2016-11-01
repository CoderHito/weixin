package com.cf.util.constant;

/**
 * 交易代码常量
 * @author Kanzaki
 *
 */
public abstract class TransactionCodeConstants {
	//版本
	public static final String VERSION_CODE = "0000";
	//成功
	public static final String RESPONSE_SUCCESS = "000000";
	//错误机构ID
	public static final String WRONG_MERCHANTID_CODE = "0";
	//报文域名
	public static final String MSG_NAME = "msg";
	/* 内部交易代码*/
	public static final String RECONCILIATION_CODE = "0001";
	//非实名售卡交易代码
	public static final String NON_REALNAME_SELLCARD_CODE = "1501";
	//实名售卡交易代码
	public static final String REALNAME_SELLCARD_CODE = "1502";
	//销卡
	public static final String CARD_CANCELATION_CODE = "2401";
	//卡状态修改
	public static final String CARD_STA_ALT_CODE = "2402";
	//卡状态修改
	public static final String TRANS_DETL_QRY = "2002";
	//密码重置
	public static final String PASSWORD_RESET = "1401";
	
	public static final String CARDD_INFO_QRY = "2001";//卡信息查询
	public static final String CARDD_INFO_SYN = "2004";//卡信息同步
	public static final String CARDD_PSW_VRY = "1403";//卡密码验证
	
	public static final String ACC_RELATE_VRY = "1701";//关联账户验证
	public static final String ACC_RECHARGE_QRY = "2101";//卡充值
	public static final String ACC_RECHARGE_ONLINE = "2103";//联机卡充值
	//余额不足
	public static final String NOT_SUFFICIENT_FUNDS = "100005";
	//交易失败
	public static final String TRANSACTION_FAIL = "800002";
	//报文错误
	public static final String TRANSACTION_FORMAT_ERROR = "200002";
	//卡状态错误
	public static final String CARD_STATUS_ERROR = "100047";
	//撤销交易不存在
	public static final String SEQ_ERROR = "100048";
	//无效金额
	public static final String AMT_ERROR = "100001";
	

}
