/**
 * 
 */
package com.cf.util.spring;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.cf.exception.BeanInExistenceException;

/**
 * @author chl_seu
 *
 */
public class SpringContextUtils {
	private static final Logger logger = Logger.getLogger(SpringContextUtils.class);

	private ApplicationContext applicationContext = null;
	private ServletContext servletContext = null;

	private static SpringContextUtils instance = new SpringContextUtils();
	
	private SpringContextUtils() {}

	public final static SpringContextUtils getInstance() {
		if (instance == null) {
			instance = new SpringContextUtils();
		}
		return instance;
	}

	public static Object getBean(String beanName) throws BeanInExistenceException {
		if (logger.isTraceEnabled()) {
			logger.trace(new StringBuilder().append("Try to get bean [").append(beanName).append("] from spring context.").toString());
		}
		Object beans = null;
		try {
			beans = instance.applicationContext.getBean(beanName);
		} catch (BeansException de) {
			logger.error(new StringBuilder().append("Bean [").append(beanName).append("] inexistence!").toString(), de);
			throw new BeanInExistenceException();
		}
		return beans;
	}
	public static <T extends Object> T getBean(Class<T> clazz) throws BeanInExistenceException {
		if (logger.isTraceEnabled()) {
			logger.trace(new StringBuilder().append("Try to get bean [").append(clazz.getName()).append("] from spring context.").toString());
		}
		Object beans = null;
		try {
			beans = instance.applicationContext.getBean(clazz);
		} catch (BeansException de) {
			logger.error(new StringBuilder().append("Bean [").append(clazz.getName()).append("] inexistence!").toString(), de);
			throw new BeanInExistenceException();
		}
		return (T)beans;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getBean(String beanName, Class<T> clazz) throws BeanInExistenceException, Exception {
		Object beanIns = SpringContextUtils.getBean(beanName);
		if(beanIns == null) {
			return null;
		}
		return (T)beanIns;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		getInstance().applicationContext = applicationContext;
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
