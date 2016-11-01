package com.cf.service;

import com.cf.entity.request.JssdkConfigRequest;
import com.cf.entity.response.JssdkConfigResponse;

public interface WxService {
	JssdkConfigResponse getJssdkConfig(JssdkConfigRequest request);
}
