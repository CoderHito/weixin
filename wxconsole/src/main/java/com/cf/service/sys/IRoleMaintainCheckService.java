package com.cf.service.sys;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cf.biz.domain.Result;
import com.wxbatis.impl.data.Page;

/**
 * 接口 <code>RoleMaintainCheckService</code>角色信息审核
 * @author sven
 * @version 20150922
 */
public interface IRoleMaintainCheckService {
	/**
	 * 分页查询角色
	 * @param params
	 * @return
	 */
	Page<Map<String, String>> queryRole(Map<String, String> params)throws Exception;
	/**
	 * 角色菜单信息查询
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> queryMenu(Map<String, Object> params)throws Exception;
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
