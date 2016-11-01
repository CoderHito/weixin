package com.cf.biz.action.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.cf.base.BaseSupport;
import com.cf.biz.constant.ValidateConstant;
import com.cf.biz.validate.CommonCheck;
import com.cf.common.SimpleResult;
import com.cf.datalock.DataLock;
import com.cf.util.http.UHttpServlet;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@ParentPackage("json-default") 
@Namespace("/")
@Controller
public class CommonAction extends ParentAction{
	
	private static final Logger logger = Logger.getLogger(CommonAction.class);
	private String retMessage;
	private boolean success;
	
	//取消锁
	@Action(value = "commonAction!unLockData", results = { @Result(type = "json", params = {
			"includeProperties", "success,retMessage" }) })
	public String cancel()  {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HashMap<String, String> params;
		try {
			params = UHttpServlet.GetRequestParameters(request);
			SimpleResult sr = CommonCheck.checkWithRegex("关键编号", params.get("KEY_CODE"), ValidateConstant.REGEX_KEY_CODE, true);
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			BaseSupport.DataLock.unLockData(params);
			success = true;
		} catch (Exception e) {
			retMessage = "服务器异常";
			success = false;
			logger.error(e.getMessage(),e);
			logger.error(retMessage);
		} 	
		return ParentAction.SUCCESS;
	}
	//修改校验是否被锁
	@Action(value = "commonAction!modDataLocked", results = { @Result(type = "json", params = {
			"includeProperties", "success,retMessage" }) })
	public String modDataLocked()  {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HashMap<String, String> params;
		try {
			params = UHttpServlet.GetRequestParameters(request);
			SimpleResult sr = CommonCheck.checkWithRegex("关键编号", params.get("KEY_CODE"), ValidateConstant.REGEX_KEY_CODE, true);
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			sr = CommonCheck.checkSizeRange("关键编号描述", params.get("KEY_DESC"), 0, 50, true);
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			sr = CommonCheck.checkNotEmpty("业务sql", params.get("SQL_MAP"));
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			sr = CommonCheck.checkNotEmpty("业务sql关键字段", params.get("SQL_KEY"));
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			params.put("LOCK_TYPE", DataLock.LOCK_TYPE_MOD);
			Map<String,Object> retMap = BaseSupport.DataLock.checkDataLocked(params);
			if((boolean)retMap.get("success")==true){
				success = true;
			}else{
				success = false;
				retMessage = retMap.get("retMessage").toString();
			}
			
		} catch (Exception e) {
			retMessage = "服务器异常";
			success = false;
			logger.error(e.getMessage(),e);
			logger.error(retMessage);
		} 	
		return ParentAction.SUCCESS;
	}
	//修改校验是否被锁
	@Action(value = "commonAction!checkDataLocked", results = { @Result(type = "json", params = {
			"includeProperties", "success,retMessage" }) })
	public String checkDataLocked()  {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HashMap<String, String> params;
		try {
			params = UHttpServlet.GetRequestParameters(request);
			SimpleResult sr = CommonCheck.checkWithRegex("关键编号", params.get("KEY_CODE"), ValidateConstant.REGEX_KEY_CODE, true);
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			sr = CommonCheck.checkSizeRange("关键编号描述", params.get("KEY_DESC"), 0, 50, true);
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			sr = CommonCheck.checkNotEmpty("业务判断sql", params.get("SQL_MAP"));
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			sr = CommonCheck.checkNotEmpty("业务sql关键字段", params.get("SQL_KEY"));
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			params.put("LOCK_TYPE", DataLock.LOCK_TYPE_CHECK);
			Map<String,Object> retMap = BaseSupport.DataLock.checkDataLocked(params);
			if((boolean)retMap.get("success")==true){
				success = true;
			}else{
				success = false;
				retMessage = retMap.get("retMessage").toString();
			}
		} catch (Exception e) {
			retMessage = "服务器异常";
			success = false;
			logger.error(e.getMessage(),e);
			logger.error(retMessage);
		} 	
		return ParentAction.SUCCESS;
	}
	
	//删除校验是否被锁
		@Action(value = "commonAction!delDataLocked", results = { @Result(type = "json", params = {
				"includeProperties", "success,retMessage" }) })
		public String delDataLocked()  {
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			HashMap<String, String> params;
			try {
				params = UHttpServlet.GetRequestParameters(request);
				SimpleResult sr = CommonCheck.checkWithRegex("关键编号", params.get("KEY_CODE"), ValidateConstant.REGEX_KEY_CODE, true);
				if(!sr.isSuccess()){
					success = false;
					retMessage = sr.getRetMessage();
					return ParentAction.SUCCESS;
				}
				sr = CommonCheck.checkSizeRange("关键编号描述", params.get("KEY_DESC"), 0, 50, true);
				if(!sr.isSuccess()){
					success = false;
					retMessage = sr.getRetMessage();
					return ParentAction.SUCCESS;
				}
				sr = CommonCheck.checkNotEmpty("业务判断sql", params.get("SQL_MAP"));
				if(!sr.isSuccess()){
					success = false;
					retMessage = sr.getRetMessage();
					return ParentAction.SUCCESS;
				}
				sr = CommonCheck.checkNotEmpty("业务sql关键字段", params.get("SQL_KEY"));
				if(!sr.isSuccess()){
					success = false;
					retMessage = sr.getRetMessage();
					return ParentAction.SUCCESS;
				}
				params.put("LOCK_TYPE", DataLock.LOCK_TYPE_DEL);
				Map<String,Object> retMap = BaseSupport.DataLock.checkDataLocked(params);
				if((boolean)retMap.get("success")==true){
					success = true;
				}else{
					success = false;
					retMessage = retMap.get("retMessage").toString();
				}
			} catch (Exception e) {
				retMessage = "服务器异常";
				success = false;
				logger.error(e.getMessage(),e);
				logger.error(retMessage);
			} 	
			return ParentAction.SUCCESS;
		}

	public String getRetMessage() {
		return retMessage;
	}

	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
