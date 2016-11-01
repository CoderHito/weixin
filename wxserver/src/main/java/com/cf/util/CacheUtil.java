package com.cf.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CacheUtil {
	public final static String GLOBAL_ACCESS_TOKEN = "global_access_token";//微信access_token key
	public final static String GLOBAL_JSSDK_TICKET = "global_jssdk_ticket";//微信jssdk_ticket key
	public final static String GLOBAL_START_TIME = "global_start_time";//获取access_token、jssdk_ticket的时间 key
	// 根据键获取系统全局变量
	public static Object getApplicationValue(String key) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext context = webApplicationContext.getServletContext();
		return context.getAttribute(key);
	}
	// 根据键值对设置系统全局变量
	public static void setApplicationValue(String key, Object value) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext context = webApplicationContext.getServletContext();
		context.removeAttribute(key);
		context.setAttribute(key, value);
	}
}
