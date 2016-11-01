package com.cf.util;

import org.apache.http.protocol.HTTP;

public class HttpHelper {
	/**
	 * @Title: httpPost 
	 * @Description: post的参数可以是集合、entity,默认utf-8解析返回的流
	 * @return T    返回类型 
	 * @throws
	 */
	public static <T> T httpPost(String url, Object o, Class<T> cls){
		return httpPost(url, o, HTTP.UTF_8, cls);
	}
	/**
	 * @Title: httpPost 
	 * @Description: post的参数可以是集合、entity
	 * @return T    返回类型 
	 * @throws
	 */
	public static <T> T httpPost(String url, Object o,String charSet,Class<T> cls){
		return CommonUtil.jsonToBean(httpPost(url,o,charSet), cls);
	}
	/**
	 * @Title: httpPost 
	 * @Description: post的参数可以是集合、entity,默认utf-8解析返回的流
	 * @return String    返回类型 
	 * @throws
	 */
	public static String httpPost(String url, Object o){
		return httpPost(url,o,HTTP.UTF_8);
	}
	/**
	 * @Title: httpPost 
	 * @Description: post的参数可以是集合、entity
	 * @return String    返回类型 
	 * @throws
	 */
	public static String httpPost(String url, Object o,String charSet){
		String json = CommonUtil.beanToJson(o);
		return HttpUtil.httpPost(url,json,charSet,"application/json");
	}
}
