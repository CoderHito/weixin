package com.cf.biz.action.base;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements Action{
	private static final long serialVersionUID = -82943470536738808L;
	protected String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	protected Object responseObj;

	protected String responStr;

	public Object getResponseObj() {
		return responseObj;
	}

	public void setResponseObj(Object responseObj) {
		this.responseObj = responseObj;
	}

	public String getResponStr() {
		return responStr;
	}

	public void setResponStr(String responStr) {
		this.responStr = responStr;
	}
	
}