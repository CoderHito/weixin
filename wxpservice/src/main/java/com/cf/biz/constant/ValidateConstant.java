package com.cf.biz.constant;

import com.cf.util.constant.CommonConstant;

public class ValidateConstant {
	//检验正则
	public static final String REGEX_CHECK_STATUS = "^"+CommonConstant.CHECK_STATUS_WAIT+"$|^"
			+CommonConstant.CHECK_STATUS_PASS+"$|^"+CommonConstant.CHECK_STATUS_REFUSE+"$";//审核状态
	public static final String REGEX_ACC_STATUS = "^"+CommonConstant.ACC_STATUS_NORMAL+"$";//账户状态
	public static final String REGEX_ACC_TYPE = "^"+CommonConstant.ACC_TYPE_BUY_CARD_CASH+"$|^"
			+CommonConstant.ACC_TYPE_MANAGE+"$|^"+CommonConstant.ACC_TYPE_FOREIGN_SETTLE+"$";;//账户类型  新增、修改时类型不能为01-卡账户
	public static final String REGEX_PHONE = "^1\\d{10}$";//手机号
	public static final String REGEX_PHONE_TEL = "^1\\d{10}$|^\\d{3,4}-\\d{7,14}$";//手机号和电话
	public static final String REGEX_ID = "^\\d{15}$|^(\\d{17})(\\d|x|X)$";//省份证
	public static final String REGEX_CARD_STATUS = "^"+CommonConstant.CARD_STATUS_P+"$|^"+CommonConstant.CARD_STATUS_E+"$|^"
			+CommonConstant.CARD_STATUS_A+"$|^"+CommonConstant.CARD_STATUS_N+"$|^"+CommonConstant.CARD_STATUS_T+"$|^"+CommonConstant.CARD_STATUS_G+"$|^"
			+CommonConstant.CARD_STATUS_X+"$|^"+CommonConstant.CARD_STATUS_K+"$|^"+CommonConstant.CARD_STATUS_L+"$|^"+CommonConstant.CARD_STATUS_R+"$|^"
			+CommonConstant.CARD_STATUS_J+"$|^"+CommonConstant.CARD_STATUS_D+"$|^"+CommonConstant.CARD_STATUS_M+"$";//卡状态 
	public static final String REGEX_CARD_BIND = "^"+CommonConstant.CARD_NOT_BIND+"$|^"
			+CommonConstant.CARD_BIND+"$";//卡绑定状态
	public static final String REGEX_CARDNOS = "^(\\d{16},)*\\d{16}$";//多个卡号（逗号分割）
	public static final String REGEX_CARDNO = "^\\d{16}$";//单个卡号
	public static final String REGEX_KEY_CODE = "^([0-9a-zA-Z]+,)*[0-9a-zA-Z]+$";//datalock表keycode校验
	public static final String REGEX_MONEY = "^[1-9]\\d{0,17}$|^[1-9]\\d{0,17}\\.\\d{1,2}$|^0\\.[1-9]$|^0\\.\\d[1-9]$|^0$";//金额格式
	public static final String REGEX_ORDER_NOT_ACTIVE = "^"+CommonConstant.ORDER_NOT_ACTIVE+"$";//订单卡未激活
	public static final String REGEX_ORDER_ACTIVE_STATUS = "^"+CommonConstant.ORDER_ACTIVE+"$|^"+CommonConstant.ORDER_NOT_ACTIVE+"$|^"+CommonConstant.ORDER_FAIL_ACTIVE+"$";//订单卡激活状态
	public static final String REGEX_ID_TYPE = "^"+CommonConstant.ID_TYPE_SFZ+"$|^"+CommonConstant.ID_TYPE_XGSFZ+"$|^"+CommonConstant.ID_TYPE_HZ
			+"$|^"+CommonConstant.ID_TYPE_KHH+"$|^"+CommonConstant.ID_TYPE_ZGBH+"$|^"+CommonConstant.ID_TYPE_QT+"$";//证件类型
	public static final String REGEX_EMAIL = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";//邮编
	public static final String REGEX_ORDER_NO = "^([0-9a-zA-Z]{1,20},)*[0-9a-zA-Z]{1,20}$";//订单正则
	//字段校验失败描述
	public static final String ERROR_CHECK_SIZE_SAME_2 = "长度不正确";
	public static final String ERROR_CHECK_SIZE_SAME_1 = "为空或长度不正确";
	public static final String ERROR_CHECK_SIZE_MORE_2 = "长度过长";
	public static final String ERROR_CHECK_SIZE_MORE_1 = "为空或长度过长";
	public static final String ERROR_CHECK_DATE_VALID = "日期格式不正确";
	public static final String ERROR_CHECK_NUMBER_MORE = "数字长度或格式不正确";
	public static final String ERROR_CHECK_AMOUNT_MORE = "金额长度或格式不正确";
	public static final String ERROR_CHECK_DECIMAL_AMT = "金额格式不正确";
	public static final String ERROR_CHECK_SPECIAL_CHARACTERS = "包含非法字符";
	public static final String ERROR_CHECK_DATE_COMPARE = "输入有误，起始日期大于结束日期";
	public static final String ERROR_MATCH_SYS_DICT = "与数据字典匹配失败";
	public static final String ERROR_MATCH_SYS_PARAMS = "与系统参数表匹配失败";
	public static final String ERROR_CHECK_MONEY_FORMATE = "金额格式校验失败";
	public static final String ERROR_CHECK_MATCH_DICT = "值与字典匹配失败";
	public static final String ERROR_CHECK_WITH_REGEX_1 = "为空";
	public static final String ERROR_CHECK_WITH_REGEX_2 = "格式不正确";
}
