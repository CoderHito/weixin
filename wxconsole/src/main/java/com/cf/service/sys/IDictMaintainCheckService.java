package com.cf.service.sys;

import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cf.biz.domain.Result;
import com.wxbatis.impl.data.Page;

/**
 * 接口 <code>DictMaintainCheckService</code> 数据字典审核
 * @author sven
 * @version 20150922
 */
public interface IDictMaintainCheckService {
	/**
	 * 查询数据字典
	 * @param params
	 * @return
	 */
	Page<Map<String,String>> queryDict(Map<String,String> params) throws Exception;
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
