package com.cf.service.sys;

import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cf.biz.domain.Result;
import com.wxbatis.impl.data.Page;

/**
 * 接口 <code>SysParamMaintainCheckService </code>系统参数信息审核
 * @author sven
 * @version 20150922
 */
public interface ISysParamMaintainCheckService {
	/**
	 * 分页查询系统参数
	 * @return
	 */
	Page<Map<String, String>> queryParam(Map<String,String> params)throws Exception;
	/**
	 * 审核客户(成功):
	 * 
	 * @return
	 */
	public Result<T> succRemittance(Map<String,String> params) throws Exception;
	/**
	 * 审核客户(失败):
	 * 
	 * @return
	 */
	public Result<T> loseRemittance(Map<String,String> params) throws Exception;
}
