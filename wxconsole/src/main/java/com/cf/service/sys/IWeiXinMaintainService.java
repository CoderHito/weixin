package com.cf.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cf.common.SimpleResult;
import com.wxbatis.impl.data.Page;


public interface IWeiXinMaintainService {

	//查询微信菜单
	Page<Map<String,String>> queryWeiXinMenu(Map<String, String> params) throws Exception;
	//查询微信菜单
	List<Map<String,String>> queWeiXinMenu(); 
	//增加微信菜单
	public SimpleResult addWeinXinMenu(Map<String, String> params);
	//删除微信菜单
	public SimpleResult delWeinXinMenu(Map<String, String> params);
	//修改微信菜单
	public SimpleResult upWeinXinMenu(Map<String, String> params);
	//查询一级菜单
	public SimpleResult queryOneMenu();
	//查询二级菜单
	public SimpleResult queryTwoMenu(Map<String, String> params);
	SimpleResult updOneMenu(HashMap<String, String> params);
	//查询一级菜单下的子菜单
	List<Map<String, String>> querySubMenu(Map<String, String> map);
	int selectWeixinMenu1();
	int selectWeixinMenu2();
	List<Map<String, String>> queryOneMenu1();
	int queryParentMenu(Map<String, String> params);
	
}
