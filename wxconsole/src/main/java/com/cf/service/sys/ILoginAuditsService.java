package com.cf.service.sys;

import java.util.Map;

import com.wxbatis.impl.data.Page;

/**
 * 接口 <code>LoginAuditsService</code>登录日志信息
 * @author sven
 * @version 20150922
 */
public interface ILoginAuditsService {
	/**
	 * 分页查询登录日志
	 * @param params
	 * @return
	 */
	Page<Map<String,String>> queryLogin(Map<String,String> params)throws Exception;
}
