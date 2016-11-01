package com.cf.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cf.biz.domain.Result;
import com.wxbatis.impl.data.Page;

/**
 * 接口 <code>UserMaintainCheckService</code>用户信息审核
 * @author sven
 * @version 20150922
 */
public interface IUserMaintainCheckService {
	/**
	 * 分页查询操作员信息
	 * @param params
	 * @return
	 */
	Page<Map<String,String>> queryUser(Map<String, String> params) throws Exception;
	/**
	 * 查询操作员角色(临时表)
	 * @param params
	 * @return
	 */
	List<Map<String,String>> queryUserRole(HashMap<String, Object> params) throws Exception;
	
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
