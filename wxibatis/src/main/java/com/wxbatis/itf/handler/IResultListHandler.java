/**
 * 
 */
package com.wxbatis.itf.handler;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

/**
 * @author chl_seu
 * 
 *
 */
public interface IResultListHandler<T> extends ResultHandler, IResultHandler<List<? extends T>> {
	/**
	 * 返回列表结果集
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getResult();
	
	/**
	 * 返回总记录数
	 * @return
	 */
	public int getTotalCount();
}
