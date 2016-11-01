/**
 * 
 */
package com.cf.util.spring.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cf.util.spring.SpringContextUtils;

/**
 * @author chl_seu
 * @version 1.0
 * 
 * 读取Spring配置，初始化ApplicationContext.
 * 在web应用中一般用ContextLoaderListener加载webapplication,
 * 如果需要在action之外或者control类之外获取webapplication思路之一是，单独写个类放在static变量中
 */
public class SpringInitWeb extends ContextLoaderListener {

	
private final static Logger logger = Logger.getLogger(SpringInitWeb.class);
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuilder().append("---------- Start to init SpringContextLoader at ")
					.append(System.currentTimeMillis()).append(" --------------").toString());
		}
		super.contextInitialized(event);

		ServletContext servletContext = event.getServletContext();

		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		SpringContextUtils.getInstance().setApplicationContext(applicationContext);

		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuilder().append("---------- Finish to init SpringContextLoader at ")
					.append(System.currentTimeMillis()).append(" --------------").toString());
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuilder().append("---------- Start to destory SpringContextLoader at ")
					.append(System.currentTimeMillis()).append(" --------------").toString());
		}
		super.contextDestroyed(event);

		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuilder().append("---------- Finish to destory SpringContextLoader at ")
					.append(System.currentTimeMillis()).append(" --------------").toString());
		}
	}

}
