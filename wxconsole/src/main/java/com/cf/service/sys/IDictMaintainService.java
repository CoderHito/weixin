package com.cf.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cf.biz.domain.Result;
import com.wxbatis.impl.data.Page;
/**
 * 接口 <code>DictMaintainService</code>数据字典维护，主要包括增删改查功能
 * @author sven
 * @version 20150922
 */
public interface IDictMaintainService {
	
	/**
	 * 根据groupcode从缓存中取对应的段
	 * @param gp
	 * @return
	 */
	List<Map<String,String>> getDict(String gp);
	/**
	 * 根据多个groupid获取对应的段
	 * @param 
	 * @return
	 */
	HashMap<String,List<Map<String, String>>> getDictMap(List <String> listGp);
	/**
	 * 刷新数据字典缓存
	 * 
	 * @return
	 */
	boolean refreshDict();
	/**
	 * 查询数据字典
	 * @param params
	 * @return
	 */
	Page<Map<String,String>> queryDict(Map<String,String> params) throws Exception;
	/**
	 * 新增数据字典记录
	 * @param params
	 * @return
	 */
	Result<T> saveDict(Map<String,String> params) throws Exception;
	/**
	 * 删除数据字典记录
	 * @param params
	 * @return
	 */
	int delDict(Map<String,String> params) throws Exception;
}
