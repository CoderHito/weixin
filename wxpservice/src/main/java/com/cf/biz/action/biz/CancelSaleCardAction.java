package com.cf.biz.action.biz;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cf.biz.action.base.BaseAction;
import com.cf.util.constant.TransactionCodeConstants;
import com.cf.util.http.HttpUtil;
import com.wxbatis.jdbc.EncryptablePropertyPlaceholderConfigurer;
/**
 * 售卡撤销
 * @author 
 *
 */
@Controller("CancelSaleCardAction")
@Scope("prototype")
@ParentPackage("inner-default") 
@Namespace("/CancelSaleCardAction")
public class CancelSaleCardAction extends BaseAction {
	private static final long serialVersionUID = 1558639291096864959L;
	
	@Action(value = "cancelSaleCard", results = { 
			@Result(type = "json", params = {"includeProperties", "msg","ignoreHierarchy","false"})})
	public String cancelSaleCard() throws Exception{
		String url=EncryptablePropertyPlaceholderConfigurer.getContextProperty("http.address").toString()+"/TXN1503";
		msg=HttpUtil.post(url,TransactionCodeConstants.MSG_NAME+"="+msg,"utf-8");
		return BaseAction.SUCCESS;
	}
}
