package com.cf.biz.action.card;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cf.biz.action.base.ParentAction;
import com.cf.biz.service.card.ICardQueryService;
import com.cf.util.constant.CommonConstant;
import com.cf.util.http.UHttpServlet;
import com.opensymphony.xwork2.ActionContext;
import com.wxbatis.impl.data.Page;
/**
 * 卡订单查询
 * @author sven
 *
 */
@Controller("CardQueryAction")
@Scope("prototype")
@ParentPackage("json-default") 
@Namespace("/")
public class CardQueryAction extends ParentAction{
	private static final long serialVersionUID = 2108331760514665064L;
	@Autowired
	private ICardQueryService cardQueryService;
	
	
	@Action(value = "CardQueryAction!queryCardPage", results = { 
			@Result(type = "json", params = {"includeProperties", "success,total,storeList.*\\.sin,retMessage","ignoreHierarchy","false"})})
	public String queryCardPage(){
		try{
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			HashMap<String, String> params = UHttpServlet.GetRequestParameters(request);
//			params.put("MAKER", BaseSupport.CframeUtil.GetCurrentUserName());//当前操作员只能查自己录入的
			Page<Map<String, String>> pageobj = cardQueryService.queryCardPage(params);
			storeList = pageobj.getResult();
			total = pageobj.getTotalCount();
			success = true;
			if (storeList.size() > 0) {
				retMessage = "";
			} else {
				retMessage = "查询无记录";
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			retMessage = CommonConstant.QRY_FAIL;
			success = false;
		}
		return ParentAction.SUCCESS;
	}
	
}
