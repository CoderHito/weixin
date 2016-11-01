package com.cf.util.constant;

public class CommonConstant {
	//提示信息定义
	public final static String QRY_FAIL = "查询操作失败-服务器异常";
	public final static String ADD_FAIL = "新增操作失败-服务器异常";
	public final static String MOD_FAIL = "修改操作失败-服务器异常";
	public final static String DEL_FAIL = "删除操作失败-服务器异常";
	public final static String CHK_FAIL = "审核操作失败-服务器异常";
	public final static String IMP_FAIL = "导入操作失败-服务器异常";
	public final static String EXP_FAIL = "导出操作失败-服务器异常";
	public final static String QRY_SUCCESS = "查询操作成功";
	public final static String ADD_SUCCESS = "新增操作成功";
	public final static String MOD_SUCCESS = "修改操作成功";
	public final static String DEL_SUCCESS = "删除操作成功";
	public final static String CHK_SUCCESS = "审核操作成功";
	public final static String IMP_SUCCESS = "导入操作成功";
	public final static String EXP_SUCCESS = "导出操作成功";
	public final static String SETL_SUCCESS = "清算操作成功";
	//补录状态
	public final static String MOD_STATUS_ADD = "01";//新增
	public final static String MOD_STATUS_MOD = "02";//修改
	public final static String MOD_STATUS_DEL = "03";//删除
	//审核状态
	public final static String CHECK_STATUS_INIT = "00";
	public final static String CHECK_STATUS_WAIT = "01";//待审核
	public final static String CHECK_STATUS_PASS = "02";//审核通过
	public final static String CHECK_STATUS_REFUSE = "03";//审核拒绝
	//账户状态
	public final static String ACC_STATUS_NORMAL = "01";//正常
	public final static String ACC_STATUS_PIN = "02";//注销
	//账户类型
	public final static String ACC_TYPE_CARD = "01";//卡账户
	public final static String ACC_TYPE_BUY_CARD_CASH = "02";//购卡现金账户
	public final static String ACC_TYPE_MANAGE = "03";//管理费账户
	public final static String ACC_TYPE_FOREIGN_SETTLE = "04";//对外结算账户
	//卡状态 
	public final static String CARD_STATUS_P = "P";//制卡中
	public final static String CARD_STATUS_E = "E";//已入库
	public final static String CARD_STATUS_A = "A";//已激活
	public final static String CARD_STATUS_N = "N";//未激活
	public final static String CARD_STATUS_T = "T";//已换卡
	public final static String CARD_STATUS_G = "G";//已作废（可回收）
	public final static String CARD_STATUS_X = "X";//已过期
	public final static String CARD_STATUS_K = "K";//口头挂失
	public final static String CARD_STATUS_L = "L";//正式挂失
	public final static String CARD_STATUS_R = "R";//已回收
	public final static String CARD_STATUS_J = "J";//已换卡登记
	public final static String CARD_STATUS_D = "D";//已作废（不可回收）
	public final static String CARD_STATUS_M = "M";//已作废（已丢失）
	public final static String CARD_STATUS_FF = "FF";//激活失败
	public final static String CARD_STATUS_DJ = "DJ";//冻结

	//卡绑定状态 
	public final static String CARD_NOT_BIND = "0";//未绑定
	public final static String CARD_BIND = "1";//已绑定
	
	//订单激活状态
	public final static String ORDER_ACTIVE = "0";//激活
	public final static String ORDER_NOT_ACTIVE = "1";//未激活
	public final static String ORDER_FAIL_ACTIVE = "2";//激活失败
	
	//订单证件类型
	public final static String ID_TYPE_SFZ = "10";//身份证
	public final static String ID_TYPE_XGSFZ = "01";//香港身份证
	public final static String ID_TYPE_HZ = "02";//护照
	public final static String ID_TYPE_KHH = "09";//客户号
	public final static String ID_TYPE_ZGBH = "11";//职工编号
	public final static String ID_TYPE_QT = "20";//其他
	
	//订单状态
	public final static String ORDER_STATUS_UNFINISHED = "01";//未完成
	public final static String ORDER_STATUS_PARTIALLY = "02";//部分完成
	public final static String ORDER_STATUS_FINISHED = "03";//已完成
	
	//锁记录操作类型
	public static final String LOCK_TYPE_MOD = "mod";
	public static final String LOCK_TYPE_DEL = "del";
	public static final String LOCK_TYPE_CHECK = "check";
	
	//最上层父科目号
	public final static String SUBJECT_ROOT_CODE = "root";
	public final static String SUBJECT_ROOT_NAME = "root";
	//日期格式
	public final static String SIMPLE_STRING_DATE_FORMAT = "yyyyMMdd";
	public final static String SIMPLE_STRING_TIME_FORMAT = "yyyyMMddhhmmss";
	
	//是否处理
	public final static String ALREADY_DEAL = "02";
	public final static String NOT_DEAL = "01";
	
	//账户配置表 交易类型
	public final static String TRANS_TYPE_RECHARGE = "0001";//0001-充值
	public final static String TRANS_TYPE_REFUND = "0002";//0002-退款
	public final static String TRANS_TYPE_POS_CONSUME = "0003";//0003-POS消费
	public final static String TRANS_TYPE_POS_CONSUME_REVOKE = "0004";//0004-POS消费撤销
	public final static String TRANS_TYPE_ONLINE_CONSUME = "0005";//0005-线上消费
	public final static String TRANS_TYPE_ONLINE_CONSUME_REVOKE = "0006";//0006-线上消费撤销
	public final static String TRANS_TYPE_MANAGEMENT_FEE = "0007";//0007-管理费
	public final static String TRANS_TYPE_MANAGEMENT_FEE_REVOKE = "0008";//0008-管理费退费
	public final static String TRANS_TYPE_COMMISSION_FEE = "0009";//0009-手续费
	public final static String TRANS_TYPE_COMMISSION_FEE_REVOKE = "0010";//0010-手续费退费
	public final static String TRANS_TYPE_CARD_CANCELLATION = "0011";//0011-卡注销
	
	//接口类型
	public final static String TRANS_TYPE_2103 = "2103";//2103充值接口
	
	//调账方式
	public final static String RECONCILIATION_ADD = "01";//新增
	public final static String RECONCILIATION_DEL = "02";//删除
	public final static String RECONCILIATION_MOD = "03";//修改
	
	//卡产品表 费用类型
	public final static String FEE_TYPE_MANAGMENT = "00";//管理费
	public final static String FEE_TYPE_COMMISSION = "01";//手续费
	//卡产品表 是否收管理费
	public final static String IF_MCOST_NO = "0";//否
	public final static String IF_MCOST_YES = "1";//是
	
	
	
	//系统参数
	public final static String SYSPARAM_GROUP_0004 = "0004";
	public final static String SYSPARAM_ID_0004_0001 = "0001";//一张身份证最大持卡数量
	public final static String SYSPARAM_ID_0004_0002 = "0002";//需实名的卡限额
	public final static String SYSPARAM_GROUP_0005 = "0005";
	public final static String SYSPARAM_ID_0005_0001 = "0001";//卡账户科目号
	public final static String SYSPARAM_GROUP_0006 = "0006";
	public final static String SYSPARAM_ID_0006_0001 = "0001";//购卡现金账户
	public final static String SYSPARAM_ID_0006_0002 = "0002";//管理费账户
	public final static String SYSPARAM_ID_0006_0003 = "0003";//卡账户科目号
	public final static String SYSPARAM_GROUP_0007 = "0007";
	public final static String SYSPARAM_ID_0007_0001 = "0001";//微信应用ID
	public final static String SYSPARAM_ID_0007_0002 = "0002";//微信应用密钥
	public final static String SYSPARAM_ID_0007_0003 = "0003";//微信token
	public final static String SYSPARAM_ID_0007_0004 = "0004";//微信encodingAESKey
	public final static String SYSPARAM_GROUP_0008 = "0008";//预充值
	public final static String SYSPARAM_ID_0008_0001 = "0001";//预充值金额
	public final static String SYSPARAM_GROUP_4001 = "0008";//交易日报表存放目录
	public final static String SYSPARAM_ID_4001_0001 = "0001";//交易日报表存放目录
	
	//中银通
	public final static String ZYT_MAKER_NAME = "ZYT";
	public final static String SYS_MAKER = "SYS";
	//中银通交易状态
	public final static String ZYT_TRANS_SUCCESS = "00";
	public final static String ZYT_TRANS_FAIL = "99";
	
	//系统号
	public final static String ZYT_SYS_NO = "1";
	public final static String OWN_SYS_NO = "0";
	
	//处理结果
	public final static String DEAL_SUCCESS = "01";
	public final static String DEAL_FAIL = "02";
	
	//清算状态
	public final static String CLEARING_NO="01";//未清算
	public final static String CLEARING_YES="02";//已清算
}
