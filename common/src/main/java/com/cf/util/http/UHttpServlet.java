/**
 * 
 */
package com.cf.util.http;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
 * @author chl_seu
 * 操作HttpServlet的request，response的函数
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public class UHttpServlet {
	private static final Logger logger = Logger.getLogger(UHttpServlet.class);
	private UHttpServlet() {}
	
	/**
	 * 将HttpServletRequest的前台传入参数转化为map对象
	 * @throws UnsupportedEncodingException 
	 */
	public static HashMap<String,String> GetRequestParameters(HttpServletRequest request) throws UnsupportedEncodingException {
		HashMap<String,String> params = new HashMap(); 
		Enumeration<String> en = request.getParameterNames();
		while(en.hasMoreElements()){
			 String key = en.nextElement();
			 logger.info("请求参数："+key+"--"+request.getParameter(key));
			 params.put(key, request.getParameter(key));
		}
		return params;
	}

	/**
	 * 将HttpServletRequest的前台传入参数转化为map对象
	 */
	public static HashMap<String,Object> GetRequestParameters1(HttpServletRequest request) {
		HashMap<String,Object> params = new HashMap(); 
		Enumeration<String> en = request.getParameterNames();
		while(en.hasMoreElements()){
			 String key = en.nextElement();
			 System.out.println(key);
			 System.out.println(request.getParameter(key));
			 params.put(key, request.getParameter(key));
		}
		return params;
	}
}
