package com.cf.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 日志拦截器
 * 
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {
	Logger logger = Logger.getLogger(LoggerInterceptor.class);
	final static String SPLIT_LINE = "==========================";

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		logger.info(SPLIT_LINE);
		logger.info(">>>>REQUEST URL:" + request.getRequestURI());
		logger.info(SPLIT_LINE);
	}
}
