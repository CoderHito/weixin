package com.cf.service;

import com.cf.entity.response.JssdkConfigResponse;

public interface WxService {
	/**
	 * @Title: getJsSdkConfig 
	 * @Description: 获取jsSdk配置参数
	 * @returnJssdkConfigResponse    返回类型 
	 * @throws
	 */
	JssdkConfigResponse getJsSdkConfig(String url,long timestamp,String noncestr);
}
