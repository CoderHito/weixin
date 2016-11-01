package com.cf.biz.action.accounting;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cf.biz.action.base.BaseAction;
import com.cf.biz.service.accounting.IAccountingService;
import com.cf.biz.service.accounting.impl.AccountingService;
import com.cf.util.constant.TransactionCodeConstants;
import com.cf.util.string.FastJsonUtil;
/**
 * 帐务处理
 * @author chl_seu
 *
 */
@Controller("accountingAction")
@Scope("prototype")
@ParentPackage("json-default") 
@Namespace("/AccountingAction")
public class AccountingAction extends BaseAction {
	private static final long serialVersionUID = 1558639291096864959L;
	protected final static Logger logger = Logger.getLogger(AccountingService.class);
	@Autowired
	private IAccountingService accountingService;
	@SuppressWarnings({ "unchecked" })
	@Action(value = "accounting", results = { 
			@Result(type = "json", params = {"includeProperties", "msg","ignoreHierarchy","false"})})
	public String accounting() throws UnsupportedEncodingException{
		HashMap<String,String> params =new HashMap<>();
		HashMap<String,Object> response =new HashMap<>();
		HashMap<String,String> head =new HashMap<>();
		JSONObject msgBody = JSONObject.fromObject(msg);
		JSONObject body = msgBody.getJSONObject("body");
		head =  (HashMap<String,String>)JSONObject.toBean(msgBody.getJSONObject("head"), Map.class);
		params=(HashMap<String,String>)JSONObject.toBean(body, Map.class);
		try{
			boolean success=accountingService.AccountingMain(params);
			if(success){
				head.put("ERRCODE",TransactionCodeConstants.RESPONSE_SUCCESS);
				head.put("ERRMSG","记账成功");
			}else{
				head.put("ERRCODE","01");
				head.put("ERRMSG","记账失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			head.put("ERRCODE","01");
			head.put("ERRMSG",e.getMessage());
		}
		response.put("head", head);
		msg=FastJsonUtil.toJSONString(response);
		return BaseAction.SUCCESS;
	}
}
