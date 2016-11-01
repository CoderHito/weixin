package com.cf.service.sys;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cf.biz.domain.Result;

public interface IOnlineUserManageService {
	/**
	 * 查询在线的用户
	 * @param param
	 * @return
	 */
	List<Map<String,String>> queryOnlineUser(Map<String,String> params) throws Exception;
	/**
	 * 注销在线的用户
	 * @return
	 */
	Result<T> destoryOnlineUser(Map<String,String> params) throws Exception;
}
