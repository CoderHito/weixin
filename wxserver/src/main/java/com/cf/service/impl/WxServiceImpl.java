package com.cf.service.impl;

import org.springframework.stereotype.Service;

import com.cf.entity.request.JssdkConfigRequest;
import com.cf.entity.response.JssdkConfigResponse;
import com.cf.service.WxService;
import com.cf.util.CacheUtil;
import com.cf.util.WeixinUtil;

@Service("wxService")
public class WxServiceImpl implements WxService {

	@Override
	public JssdkConfigResponse getJssdkConfig(JssdkConfigRequest request) {
		String jsapi_ticket = CacheUtil.getApplicationValue(CacheUtil.GLOBAL_JSSDK_TICKET).toString();
		String noncestr = request.getNoncestr();
		String url = request.getUrl();
		long timestamp = request.getTimestamp();
		String signature  = WeixinUtil.createJsSdkSignature(jsapi_ticket,timestamp,noncestr,url);
		String appId = WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_APPID);
		JssdkConfigResponse response = new JssdkConfigResponse();
		response.setAppId(appId);
		response.setSignature(signature);
		return response;
	}

}
