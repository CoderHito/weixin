/**
 * 
 */
package com.cf.cfsecurity.listener;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cf.base.BaseSupport;
import com.cf.util.spring.SpringContextUtils;
import com.cf.util.spring.web.SpringInitWeb;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 系统启动监听器
 * @author chl_seu
 *
 */
@SuppressWarnings("unused")
public class SystemInitListener implements ServletContextListener {

	private final static Logger logger = Logger.getLogger(SpringInitWeb.class);
	private MyBatisSessionTemplate jdbcTemplate; 
	private boolean success = true;
	private String retMessage;
	private ApplicationContext applicationContext = null;
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuilder().append("---------- Start to Destroy ServletContextListener  at ")
					.append(System.currentTimeMillis()).append(" --------------").toString());
		}
			

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuilder().append("---------- Start to init ServletContextListener  at ")
					.append(System.currentTimeMillis()).append(" --------------").toString());
		}
		systemStartup(arg0.getServletContext());

	}
	
	/**
	 * 初始化全局变量
	 */
	private void systemStartup(ServletContext servletContext) {
		if (logger.isDebugEnabled()) {
			logger.debug(new StringBuilder().append("---------- Start to 初始化全局变量  at ")
					.append(System.currentTimeMillis()).append(" --------------").toString());
		}
		
		try {
			applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			this.jdbcTemplate = (MyBatisSessionTemplate)SpringContextUtils.getBean("myBatisSessionTemplate");
		} catch (Exception e) {
			success = false;
			logger.error(e.getMessage(),e);
		}
		
		if (success) {
			BaseSupport.CframeUtil.InitDict(servletContext);
			BaseSupport.CframeUtil.InitSysParams(servletContext);
			BaseSupport.DataLock.unLockData(new HashMap<String,String>());
		}
	}


}
