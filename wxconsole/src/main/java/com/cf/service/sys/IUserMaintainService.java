package com.cf.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cf.biz.domain.Result;
import com.cf.cfsecurity.domain.TbUser;
import com.wxbatis.impl.data.Page;

/**
 * 接口 <code>UserMaintainService</code> 操作员维护
 * @author sven
 * @version 20150922
 */
public interface IUserMaintainService {
	/**
	 * 修改密码
	 * @return
	 * @throws Exception
	 */
	Result<T> changePasswd(Map<String, String> params) throws Exception;
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
	List<Map<String,String>> queryUserQueryRole(HashMap<String, Object> params) throws Exception;
	/**
	 * 新增操作员
	 * @param params
	 * @return
	 */
	Result<T> saveUser(Map<String, String> params,List list) throws Exception;
	/**
	 * 注销操作员
	 * @param params
	 * @return
	 */
	Result<T> delUser(Map<String, String> params) throws Exception;
	/**
	 * 激活用户
	 * @param params
	 * @return
	 */
	Result<T> activeUser(Map<String, String> params) throws Exception;
	/**
	 * 重置密码
	 * @param params
	 * @return
	 */
	int resetPassword(Map<String, String> params) throws Exception;
	/**
	 * 查询操作员角色
	 * @param params
	 * @return
	 */
	List<Map<String,String>> queryUserRole(Map<String, String> params) throws Exception;
	/**
	 * 查询操作员角色(新增)
	 * @param params
	 * @return
	 */
	List<Map<String,String>> queryAddUserRole(Map<String, String> params) throws Exception;
	/**
	 * 用户角色信息保存
	 * @param params
	 * @return
	 */
	int saveUserRole(Map<String, String> params,List list) throws Exception;
	
	TbUser queryByUserId(Map<String, String> params) throws Exception;
}
