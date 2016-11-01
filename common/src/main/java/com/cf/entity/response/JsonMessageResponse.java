package com.cf.entity.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JsonMessageResponse {
	public final static String SUCCESS_CODE = "00";
	public final static String FAILURE_CODE = "01";
	@JsonProperty("rtn_code")
	private String resultCode = SUCCESS_CODE;//返回码
	@JsonProperty("rtn_msg")
	private String resultMessage;//返回信息
	
	public boolean isSuccess(){
		if(SUCCESS_CODE.equals(resultCode)){
			return true;
		}
		return false;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
