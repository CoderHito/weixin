package com.cf.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cf.constant.ValidateConstant;
import com.cf.entity.common.SimpleResult;

public class CommonCheckUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonCheckUtil.class);
	//校验String是否为空
	public static boolean isNullOrEmpty(String tmp){
		return tmp==null||tmp.length()==0;
	}
	public static SimpleResult checkNotEmpty(String FieldName,String FieldValue){
		SimpleResult res = new SimpleResult();
		if(!isNullOrEmpty(FieldValue)){
			res.setSucceedMsg("");
		}else{
			res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_WITH_REGEX_1);
		}
		return res;
	}
	//校验长度是否符合要求 (等值)
	public static SimpleResult checkSizeSame(String FieldName,String FieldValue,int FieldSize,boolean required){
		SimpleResult res = new SimpleResult();
		boolean flag = true;
		boolean flag_tmp = true;
		if (required){
			//required为true，表示该项为必输项
			if (isNullOrEmpty(FieldValue)||FieldValue.length()!=FieldSize){
				flag = false;
			}
		}else{
			//required为false，表示该项非必输
			if (!isNullOrEmpty(FieldValue)&&FieldValue.length()!=FieldSize){
				flag = false;
				flag_tmp = false;
			}
		}
		if (!flag&&!flag_tmp){
			res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_SIZE_SAME_2);
			logger.error(res.getRetMessage());
		}else if (!flag&&flag_tmp){
			res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_SIZE_SAME_1);
			logger.error(res.getRetMessage());
		}else{
			res.setSucceedMsg("");
		}
		return res;
	}

	//校验长度是否符合要求（最大值）
	public static SimpleResult checkSizeMore(String FieldName,String FieldValue,int FieldSize,boolean required){
		SimpleResult res = new SimpleResult();
		boolean flag = true;
		boolean flag_tmp = true;
		if (required){
			//required为true，表示该项为必输项
			if (isNullOrEmpty(FieldValue)||FieldValue.length()>FieldSize){
				flag = false;
			}
		}else{
			//required为false，表示该项非必输
			if (!isNullOrEmpty(FieldValue)&&FieldValue.length()>FieldSize){
				flag = false;
				flag_tmp =false;
			}
		}
		if (!flag&&!flag_tmp){
			res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_SIZE_MORE_2);
			logger.error(res.getRetMessage());
		}else if (!flag&&flag_tmp){
			res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_SIZE_MORE_1);
			logger.error(res.getRetMessage());
		}else{
			res.setSucceedMsg("");
		}
		return res;
	}
	
	//校验长度是否符合要求（范围）
		public static SimpleResult checkSizeRange(String FieldName,String FieldValue,int FieldMinSize,int FieldMaxSize,boolean required){
			SimpleResult res = new SimpleResult();
			boolean flag = true;
			boolean flag_tmp = true;
			if (required){
				//required为true，表示该项为必输项
				if (isNullOrEmpty(FieldValue)||FieldValue.length()>FieldMaxSize||FieldValue.length()<FieldMinSize){
					flag = false;
				}
			}else{
				//required为false，表示该项非必输
				if (!isNullOrEmpty(FieldValue)&&(FieldValue.length()>FieldMaxSize||FieldValue.length()<FieldMinSize)){
					flag = false;
					flag_tmp =false;
				}
			}
			if (!flag&&!flag_tmp){
				res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_SIZE_MORE_2);
				logger.error(res.getRetMessage());
			}else if (!flag&&flag_tmp){
				res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_SIZE_MORE_1);
				logger.error(res.getRetMessage());
			}else{
				res.setSucceedMsg("");
			}
			return res;
		}
		
	
	//校验日期格式是否正确
	public static SimpleResult checkDateValid(String FieldName,String FieldValue,int FieldSize,boolean required,String DateTimeFormat){
		SimpleResult res = new SimpleResult();
		boolean flag = true;
		try{
			if (required){
				if (isNullOrEmpty(FieldValue)||FieldValue.length()!=FieldSize){
					flag = false;
				}
			}else{
				if (!isNullOrEmpty(FieldValue)&&FieldValue.length()!=FieldSize){
					flag = false;
				}
			}
			if (flag &&!isNullOrEmpty(FieldValue)){
				SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat);
				Date date = sdf.parse(FieldValue);
				if (!FieldValue.equals(sdf.format(date))){
					flag = false;
				}
			}
		}catch (Exception e){
			flag =false;
		}
		if (flag){
			res.setSucceedMsg("");
		}else{
			res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_DATE_VALID);
			logger.error(res.getRetMessage());
		}
		return res;
	}

	//纯数字验证（长度范围）
	public static SimpleResult checkNumberMore(String FieldName,String FieldValue,int FieldSize,boolean required){
		SimpleResult res = new SimpleResult();
		boolean flag = true;
		String rex = "^[0-9]+$"; //^[0-9]+$纯数字正则校验
		try{
			Pattern pattern = Pattern.compile(rex);
			if (required){
				if (isNullOrEmpty(FieldValue)||!pattern.matcher(FieldValue).matches()||FieldValue.length()>FieldSize){
					flag = false;
				}
			}else{
				if (!isNullOrEmpty(FieldValue)&&(!pattern.matcher(FieldValue).matches()||FieldValue.length()>FieldSize)){
					flag = false;
				}
			}
		} catch (Exception e){
			flag = false;
			logger.error(e.getMessage());
		}
		if (flag){
			res.setSucceedMsg("");
		}else{
			res.setFailMsg(FieldName+ValidateConstant.ERROR_CHECK_NUMBER_MORE);
			logger.error(res.getRetMessage());
		}
		return res;
	}
	
	//纯数字验证（长度等值）
	public static SimpleResult checkNumberSame(String FieldName,String FieldValue,int FieldSize,boolean required){
		SimpleResult res = new SimpleResult();
		boolean flag = true;
		String rex = "^[0-9]+$"; //^[0-9]+$纯数字正则校验
		try{
			Pattern pattern = Pattern.compile(rex);
			if (required){
				if(isNullOrEmpty(FieldValue)||!pattern.matcher(FieldValue).matches()||FieldValue.length()!=FieldSize){
					flag = false;
				}
			}else{
				if(!isNullOrEmpty(FieldValue)&&(!pattern.matcher(FieldValue).matches()||FieldValue.length()!=FieldSize)){
					flag = false;
				}
			}
		}catch (Exception e){
			flag = false;
			logger.error(e.getMessage());
		}
		if (flag){
			res.setSucceedMsg("");
		}else{
			res.setFailMsg(FieldName+ValidateConstant.ERROR_CHECK_NUMBER_MORE);
			logger.error(res.getRetMessage());
		}
		return res;
	}
	
	//校验金额格式
	public static SimpleResult checkMoneyFormate(String FieldName,String FieldValue,int IntLength,int DecimalLength,boolean required){
		SimpleResult res = new SimpleResult();
		boolean flag = true;
		try{
			if (required){
				if(isNullOrEmpty(FieldValue)){
					flag = false;
				}else{
					String[] fieldParts = FieldValue.split("\\.");
					String intPart = fieldParts[0];
					String decimalPart = fieldParts[1];
					if(intPart.length()>IntLength||decimalPart.length()!=DecimalLength){
						flag = false;
					}
				}
			}else{
				if (!isNullOrEmpty(FieldValue)){
					String[] fieldParts = FieldValue.split(".");
					String intPart = fieldParts[0];
					String decimalPart = fieldParts[1];
					if(intPart.length()>IntLength||decimalPart.length()!=DecimalLength){
						flag = false;
					}
				}
			}
		}catch(Exception e){
			flag = false;
			logger.error(e.getMessage());
		}
		if (flag){
			res.setSucceedMsg("");
		}else{
			res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_MONEY_FORMATE);
			logger.error(res.getRetMessage());
		}
		return res;
	}
	/**
	 * 金额格式校验
	 */
	public static SimpleResult checkMoneyFormateFixed(String FieldName,String FieldValue,int IntLength,int DecimalLength,boolean required){
		SimpleResult res = new SimpleResult();
		boolean flag = true;
		try{
			BigDecimal zero = new BigDecimal("0");
			String strLength=String.valueOf(IntLength-1);
			String strDecimalLength=String.valueOf(DecimalLength);
			String paterns="^(([1-9]\\d{0,"+strLength+"})|0)(\\.\\d{1,"+strDecimalLength+"})?$";
			Pattern pattern = Pattern.compile(paterns,Pattern.CASE_INSENSITIVE);
			if (required){
				//不能为空
				BigDecimal amount = new BigDecimal(FieldValue);
				if (amount.compareTo(zero) > 0) {
					Matcher matcher = pattern.matcher(FieldValue);
					if(matcher.matches()==false){
						flag=false;
					}
				}else{
					flag=false;
				}
			}else{
				//可以为空
				if (!isNullOrEmpty(FieldValue)){
					BigDecimal amount = new BigDecimal(FieldValue);
					if (amount.compareTo(zero) > 0) {
						Matcher matcher = pattern.matcher(FieldValue);
						if(matcher.matches()==false){
							flag=false;
						}
					}else{
						flag=false;
					}
				}
			}
		}catch(Exception e){
			flag = false;
			logger.error(e.getMessage());
		}
		
		if (flag){
			res.setSucceedMsg("");
		}else{
			res.setFailMsg(FieldName + ValidateConstant.ERROR_CHECK_MONEY_FORMATE);
			logger.error(res.getRetMessage());
		}
		return res;
	}

	//根据自定义的正则校验
	public static SimpleResult checkWithRegex(String FieldName,String FieldValue,String regex,boolean required){
		SimpleResult res = new SimpleResult();
		Pattern pattern = Pattern.compile(regex);
		if (required){
			if(isNullOrEmpty(FieldValue)){
				res.setFailMsg(FieldName+ValidateConstant.ERROR_CHECK_WITH_REGEX_1);
			}else if(!pattern.matcher(FieldValue).matches()){
				res.setFailMsg(FieldName+ValidateConstant.ERROR_CHECK_WITH_REGEX_2);
			}else{
				res.setSucceedMsg("");
			}
		}else{
			if(isNullOrEmpty(FieldValue)){
				res.setSucceedMsg("");
			}else if(!pattern.matcher(FieldValue).matches()){
				res.setFailMsg(FieldName+ValidateConstant.ERROR_CHECK_WITH_REGEX_2);
			}else{
				res.setSucceedMsg("");
			}
		}
		return res;
	}
}
