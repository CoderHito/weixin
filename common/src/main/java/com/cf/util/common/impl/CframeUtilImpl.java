package com.cf.util.common.impl;
/**
 * 
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cf.base.AbstractBaseDao;
import com.cf.base.BaseSupport;
import com.cf.base.CustomUser;
import com.cf.util.common.ICframeUtil;
import com.cf.util.constant.Constants;
import com.cf.util.constant.SysParamsDefine;

/**
 * @author chl_seu
 *
 */
//@SuppressWarnings("unchecked")
//@Repository("CframeUtil")
public class CframeUtilImpl extends AbstractBaseDao implements ICframeUtil{
	private final static Logger logger = Logger.getLogger(CframeUtilImpl.class);
	/* (non-Javadoc)
	 * @see cf.util.common.CframeUtil#getSysParamsValue(java.lang.String, java.lang.String)
	 */
	public String getSysParamsValue(String PARAMGROUP_ID, String PARAM_ID) {
		HashMap<String,String> map =new HashMap<String,String>();
		map.put("PARAMGROUP_ID", PARAMGROUP_ID);
		map.put("PARAM_ID", PARAM_ID);
		try{
			String sl = (String)this.getJdbcTemplate().selectOne("cf.dao.mapper.SysParamsMapper.getSysParamsValue",map);
			return sl;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
/*		try{
			//@SuppressWarnings("unused")
			//Object o= BaseSupport.ContextUtil.contextHook();
			@SuppressWarnings("static-access")
			List<Map<String,String>> groupList = BaseSupport.SysParamsMap.sysParam.get(PARAMGROUP_ID);
			for(Map<String,String> map : groupList){
				if(map.get("PARAM_ID").equals(PARAM_ID)){
					return map.get("PARAM_VAL");
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}*/
		return null;
	}

	/* (non-Javadoc)
	 * @see cf.util.common.CframeUtil#setSysParamsValue(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean setSysParamsValue(String PARAMGROUP_ID, String PARAM_ID,
			String PARAM_VAL) {
		// TODO Auto-generated method stub
		HashMap<String,String> map =new HashMap<String,String>();
		map.put("PARAMGROUP_ID", PARAMGROUP_ID);
		map.put("PARAM_ID", PARAM_ID);
		map.put("PARAM_VAL", PARAM_VAL);
		this.getJdbcTemplate().update("cf.dao.mapper.SysParamsMapper.setSysParamsValue",map);
		return true;
	}

	public boolean InitSysParams(ServletContext servletContext){
		logger.info("系统开始启动系统参数装载程序...");
		logger.info("开始加载系统参数...");
		HashMap<String,List<Map<String,String>>> sysParam = new HashMap<String,List<Map<String,String>>>();
		try {
			List<Map<String,String>> resultList= this.getJdbcTemplate().selectList("cf.dao.mapper.SysParamsMapper.selectSysParams");
			String groupcode = "";
			List<Map<String,String>> lcTempList = new ArrayList<Map<String,String>>();
			for (Map<String,String> idmp : resultList) {
				if (!idmp.get("PARAMGROUP_ID").equals(groupcode)) {
					groupcode =  (String) idmp.get("PARAMGROUP_ID");
					lcTempList = new ArrayList<Map<String,String>>();
					sysParam.put(groupcode, (ArrayList<Map<String,String>>) lcTempList);
				}
				lcTempList.add(idmp);
			}
			logger.info("系统参数成功!");
		} catch (Exception e) {
			logger.error("系统参数失败!");
			e.printStackTrace();
			return false;
		}
		servletContext.setAttribute("sysParam", sysParam);
		BaseSupport.SysParamsMap.sysParam=sysParam;
		return true;
	}
	/* (non-Javadoc)
	 * @see cf.util.common.CframeUtil#InitDict()
	 */
	@SuppressWarnings("rawtypes")
	public boolean InitDict(ServletContext servletContext) {
		logger.info("系统开始启动字典装载程序...");
		logger.info("开始加载字典...");
		HashMap dict = new HashMap();
		try {
			List<Map<String,String>>  resultList= this.getJdbcTemplate().selectList("cf.dao.mapper.SysDictMapper.selectDictList");
			String groupcode = "";
			List lcTempList = new ArrayList();
			for (Map idmp : resultList) {
				if (!idmp.get("GROUP_ID").equals(groupcode)) {
					groupcode =  (String) idmp.get("GROUP_ID");
					lcTempList = new ArrayList();
					dict.put(groupcode, (ArrayList) lcTempList);
				}
				lcTempList.add(idmp);
			}
			logger.info("字典加载成功!");
		} catch (Exception e) {
			logger.error("字典加载失败!");
			e.printStackTrace();
			return false;
		}
		servletContext.setAttribute("sysDict", dict);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cf.util.common.ICframeUtil#GetCurrentLongTime()
	 */
	
	public String GetCurrentLongTime(){
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String s = format2.format(new Date());
        return s;
	}
	public String GetCurrentDay(){
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMdd");
        String s = format2.format(new Date());
        return s;
	}
	
	@Override
	public String GetYesterDay() {
		// TODO Auto-generated method stub
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMdd");
        String s = format2.format(new Date(new Date().getTime()-24*60*60*1000));
		return s;
	}
	/**
	 * 取当前用户名
	 */
	public String GetCurrentUserName(){
		return ((CustomUser)BaseSupport.SecurityUtil.getAuthentication().getPrincipal()).getUsername();
	}

	public String GetCurrentBranchNo() {
		return ((CustomUser)BaseSupport.SecurityUtil.getAuthentication().getPrincipal()).getBranchNo();
	}
	@Override
	public String GetCurrentButtons() {
		return ((CustomUser)BaseSupport.SecurityUtil.getAuthentication().getPrincipal()).getButtons();
	}
	@Override
	public boolean ifFileOperating() {
		String leadValue=getSysParamsValue(SysParamsDefine.GROUP_LEAD,SysParamsDefine.GROUP_LEAD_CHANGE);
		String createValue=getSysParamsValue(SysParamsDefine.GROUP_LEAD,SysParamsDefine.GROUP_CREATE_FILE);
		if(!Constants.FILE_NOT_IMPORT.equals(leadValue)||!Constants.FILE_NOT_CREATE.equals(createValue)){
			return false;
		}
		return true;
	}
	
	
	public String changeF2Y(String amount){   
		if(amount.equals("")){
			return "";
		}else{
			return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString(); 
		}
		
	}
	
	public String changeY2F(String amount){
		if(amount.equals("")){
			return "";
		}else{
			return BigDecimal.valueOf(Long.valueOf(amount)).multiply(new BigDecimal(100)).toString();  
		}
	}
	
	//string加法
	public String plu2String(String str1, String str2){
		if(str1.equals("")){
			return str2;
		}else if(str2.equals("")){
			return str1;
		}else if(str1.equals("") && str2.equals("")){
			return "";
		}else{
			   BigDecimal b1 = new BigDecimal(str1);  
			   BigDecimal b2 = new BigDecimal(str2); 
			   return new Double(b1.add(b2).doubleValue()).toString(); 
		}
	}
	
	//String减法
	public String subtract2String(String str1, String str2){
		if(str1.equals("")){
			return str2;
		}else if(str2.equals("")){
			return str1;
		}else if(str1.equals("") && str2.equals("")){
			return "";
		}else{
			   BigDecimal b1 = new BigDecimal(str1);  
			   BigDecimal b2 = new BigDecimal(str2); 
			   return new Double(b1.subtract(b2).doubleValue()).toString(); 
		}
	}
}
