package com.cf.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cf.entity.request.JssdkConfigRequest;
import com.cf.entity.response.JssdkConfigResponse;
import com.cf.service.WxService;
import com.cf.util.HttpHelper;
@Service("wxService")
public class WxServiceImpl implements WxService {
	@Value("${wxserver.url}")
	private String wxServerUrl;
	private final static String GET_JSSDK_CONFIG_URL = "getJssdkConfigUrl";
	@Override
	public JssdkConfigResponse getJsSdkConfig(String url,long timestamp,String noncestr) {
		JssdkConfigRequest req = new JssdkConfigRequest();
		req.setTimestamp(timestamp);
		req.setNoncestr(noncestr);
		req.setUrl(url);
		JssdkConfigResponse resp = HttpHelper.httpPost(wxServerUrl+GET_JSSDK_CONFIG_URL, req, JssdkConfigResponse.class);
		return resp;
	}

}
