package com.cf.biz.domain;

public class SimpleResult {
	private boolean success = true;
	private String retMessage;
	
	public void setSucceedMsg(String msg){
		this.retMessage = msg;
		this.success = true;
	}
	
	public void setFailMsg(String msg){
		this.retMessage = msg;
		this.success = false;
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
