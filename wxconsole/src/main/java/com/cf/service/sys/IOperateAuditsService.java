package com.cf.service.sys;

import java.util.Map;

import com.wxbatis.impl.data.Page;

/**
 * 接口 <code>LoginAuditsService</code>操作日志信息
 * @author sven
 * @version 20150922
 */
public interface IOperateAuditsService {
	/**
	 * 分页查询操作日志
	 * @param params
	 * @return
	 */
	Page<Map<String,String>> queryOperate(Map<String,String> params)throws Exception;
}
