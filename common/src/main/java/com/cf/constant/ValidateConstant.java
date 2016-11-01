package com.cf.constant;



public class ValidateConstant {
	//检验正则
	public static final String REGEX_EMAIL = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";//邮编
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
