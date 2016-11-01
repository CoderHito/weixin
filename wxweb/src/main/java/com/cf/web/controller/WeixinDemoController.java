package com.cf.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.entity.response.JssdkConfigResponse;
import com.cf.service.WxService;
import com.cf.util.CommonUtil;
import com.cf.web.interceptors.LoggerInterceptor;

@Controller
public class WeixinDemoController {
	@Autowired
	private WxService wxService;
	Logger log = Logger.getLogger(WeixinDemoController.class);
	@RequestMapping("/demo/index")
	public String test(HttpServletRequest request){
		String url = request.getRequestURL()+(request.getQueryString()==null?"":"?"+request.getQueryString()); 
		if(url.indexOf("#")>-1){//当前网页的URL，不包含#及其后面部分
			url = url.substring(0,url.indexOf("#"));
		}
		log.info("request==>" +  request);
		long timestamp = System.currentTimeMillis();
		String noncestr = CommonUtil.getUUID();
		JssdkConfigResponse res = wxService.getJsSdkConfig(url, timestamp, noncestr);
		log.info("res ==>" + res);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("appId", res.getAppId());
		request.setAttribute("signature", res.getSignature());
		return "/demo/index";
	}
	
	
	
	
	@RequestMapping("/demo/test")
	public String demoHome(HttpServletRequest request) {
		String url = request.getRequestURL()+(request.getQueryString()==null?"":"?"+request.getQueryString()); 
		if(url.indexOf("#")>-1){//当前网页的URL，不包含#及其后面部分
			url = url.substring(0,url.indexOf("#"));
		}
		long timestamp = System.currentTimeMillis();
		String noncestr = CommonUtil.getUUID();
		JssdkConfigResponse res = wxService.getJsSdkConfig(url, timestamp, noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("appId", res.getAppId());
		request.setAttribute("signature", res.getSignature());
		return "/demo/test";
	}

	@RequestMapping("/demo/ActionSheet")
	public String demoActionSheet(HttpServletRequest request) {
		return "/wxui/ActionSheet";
	}

	@RequestMapping("/demo/chooseImage")
	public String chooseImage(HttpServletRequest request) {
		return "/wxui/chooseImage";
	}
	@RequestMapping("/demo/Article")
	public String demoArticle(HttpServletRequest request) {
		// return "/demo/home";
		// return "/demo/templates/home/home";
		return "/wxui/Article";
	}

	@RequestMapping("/demo/Toast")
	public String demo(HttpServletRequest request) {
		return "/wxui/Toast";
	}
	@RequestMapping("/demo/hito")
	public String demoHito(HttpServletRequest request) {
		return "/demo/hito";
	}
	
	
	@RequestMapping("/demo/home")
	public String HomePage(HttpServletRequest request) {
		String url = request.getRequestURL()+(request.getQueryString()==null?"":"?"+request.getQueryString()); 
		if(url.indexOf("#")>-1){//当前网页的URL，不包含#及其后面部分
			url = url.substring(0,url.indexOf("#"));
		}
		long timestamp = System.currentTimeMillis();
		String noncestr = CommonUtil.getUUID();
		JssdkConfigResponse res = wxService.getJsSdkConfig(url, timestamp, noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("appId", res.getAppId());
		request.setAttribute("signature", res.getSignature());
		return "/demo/homepage";
	}
	
	
	@RequestMapping("/demo/home1")
	public String demoHit(HttpServletRequest request) {
		return "/demo/home";
	}
	
	@RequestMapping("/demo/t")
	public String t(HttpServletRequest request) {
		return "/demo/t";
	}
	
	@RequestMapping("/demo/find")
	public String FindPage(HttpServletRequest request) {
		String url = request.getRequestURL()+(request.getQueryString()==null?"":"?"+request.getQueryString()); 
		if(url.indexOf("#")>-1){//当前网页的URL，不包含#及其后面部分
			url = url.substring(0,url.indexOf("#"));
		}
		long timestamp = System.currentTimeMillis();
		String noncestr = CommonUtil.getUUID();
		JssdkConfigResponse res = wxService.getJsSdkConfig(url, timestamp, noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("appId", res.getAppId());
		request.setAttribute("signature", res.getSignature());
		return "/demo/findpage";
	}
	
}
