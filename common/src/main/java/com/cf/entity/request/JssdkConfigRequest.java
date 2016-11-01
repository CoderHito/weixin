package com.cf.entity.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @ClassName: JssdkConfigRequest 
 * @Description: 获取微信jssdk 配置
 * @author sven 
 * @date 2016-9-27 下午1:40:12 
 *
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JssdkConfigRequest extends JsonMessageRequest {
	@JsonProperty("timestamp")
	private long timestamp;//时间戳
	@JsonProperty("url")
	private String url;//页面url
	@JsonProperty("noncestr")
	private String noncestr;//随机字符窜
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
}
