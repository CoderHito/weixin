package com.cf.biz.action.base;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ParentAction extends ActionSupport implements Action{
	private static final long serialVersionUID = -6959152363360756461L;
	protected final static Logger logger = Logger.getLogger(ParentAction.class);
	protected String retMessage;
	protected boolean success = false;
	protected int total;
	protected List<Map<String, String>> storeList;

	
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Map<String, String>> getStoreList() {
		return storeList;
	}
	public void setStoreList(List<Map<String, String>> storeList) {
		this.storeList = storeList;
	}
}
