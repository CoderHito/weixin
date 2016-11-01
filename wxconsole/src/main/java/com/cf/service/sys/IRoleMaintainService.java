package com.cf.service.sys;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cf.biz.domain.Result;
import com.wxbatis.impl.data.Page;

/**
 * 接口 <code>RoleMaintainService</code> 角色维护
 * @author sven
 * @version 20150922
 */
public interface IRoleMaintainService {
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
	 * 角色菜单信息查询(新增)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> queryAddMenu(Map<String, Object> params)throws Exception;
	/**
	 * 角色菜单信息保存
	 * @param params
	 * @return
	 */
	int saveMenu(Map<String, String> params,List list)throws Exception;
	/**
	 * 新增角色信息记录
	 * @param params
	 * @return
	 */
	Result<T> saveRole(Map<String, String> params,List list)throws Exception;
	/**
	 * 删除角色信息记录
	 * @param params
	 * @return
	 */
	Result<T> delRole(Map<String, String> params)throws Exception;
	
}
