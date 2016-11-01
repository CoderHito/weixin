package com.cf.service.sys;

import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cf.biz.domain.Result;
import com.wxbatis.impl.data.Page;

/**
 * 接口 <code>SysParamMaintainService</code> 系统参数维护
 * @author sven
 * @version 20150922
 */
public interface ISysParamMaintainService {
	/**
	 * 分页查询系统参数
	 * @return
	 */
	Page<Map<String, String>> queryParam(Map<String,String> params)throws Exception;
	/**
	 * 保存系统参数
	 * @param params
	 * @return
	 */
	Result<T> saveParam(Map<String,String> params)throws Exception;
	/**
	 *  删除系统参数
	 * @param params
	 * @return
	 */
	int delParam(Map<String,String> params)throws Exception;
}
