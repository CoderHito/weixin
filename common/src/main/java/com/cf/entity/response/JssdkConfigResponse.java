package com.cf.entity.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @ClassName: JsonMessageResponse 
 * @Description: 获取微信jssdk 配置
 * @author sven 
 * @date 2016-9-27 下午1:52:56 
 *
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JssdkConfigResponse extends JsonMessageResponse {
	@JsonProperty("app_id")
	private String appId;//微信公众号id
	@JsonProperty("signature")
	private String signature;//请求jssdk签名
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
}
