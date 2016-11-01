package com.cf.web.controller;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
/**
 * 基础控制类
 *
 */
public class BaseController {
	/**
	 * 日志记录器
	 */
	protected static final Logger logger = Logger.getLogger(BaseController.class);
	
	/**
	 * 页面重定向
	 * @param redirectUrl 页面路径
	 * @return
	 */
	public String redirect(String redirectUrl){
		logger.info("The page redirect to :"+ redirectUrl);
		return "redirect:"+ redirectUrl;
	}
	
	/**
	 * 数据绑定
	 * @param map 数据模型
	 * @param key 键
	 * @param value 值
	 */
	public void put(ModelMap map, String key, Object value){
		map.put(key, value);
	}
	
}
