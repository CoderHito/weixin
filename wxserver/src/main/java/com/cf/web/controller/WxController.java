package com.cf.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cf.entity.request.JssdkConfigRequest;
import com.cf.entity.response.JssdkConfigResponse;
import com.cf.service.WxService;
/**
 * @ClassName: WxController 
 * @Description: 提供微信一些接口
 * @author sven 
 * @date 2016-9-27 下午2:23:07 
 *
 */
@Controller
public class WxController {
	@Autowired
	private WxService wxService;
	/**
	 * @Title: getJssdkConfig 
	 * @Description: 查询jssdk 配置参数信息
	 * @return JssdkConfigResponse    返回类型 
	 * @throws
	 */
	@RequestMapping(value="/getJssdkConfigUrl",method=RequestMethod.POST)
	@ResponseBody
	public JssdkConfigResponse getJssdkConfig(@RequestBody JssdkConfigRequest req){
		return wxService.getJssdkConfig(req);
	}
}
