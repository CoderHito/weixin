package com.cf.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.cf.util.CacheUtil;
import com.cf.util.WeixinUtil;

/**
 * 监听器
 * 
 * @author Mall
 * @date 2016年1月22日 下午3:34:55
 */
public class ContextLoaderListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ContextLoaderListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext context = arg0.getServletContext();
		// 初始化access_token,有效时间两小时,每两小时会自动刷新一次
		String access_token = WeixinUtil.getAccessTokenFromWxServer();
		String ticket_jsapi_str = WeixinUtil.getJsSdkTicketFromWxServer(access_token);
		logger.info(">>>系统初始化参数access_token:" + access_token);
		logger.info(">>>系统初始化参数jssdk_ticket:" + ticket_jsapi_str);
		context.setAttribute(CacheUtil.GLOBAL_ACCESS_TOKEN, access_token);
		context.setAttribute(CacheUtil.GLOBAL_JSSDK_TICKET, ticket_jsapi_str);
		context.setAttribute(CacheUtil.GLOBAL_START_TIME, System.currentTimeMillis());
		WeixinUtil.getTimer();
	}

}
