/**
 * 
 */
package com.wxbatis.itf.handler;

import org.apache.ibatis.session.ResultHandler;

import com.wxbatis.impl.data.Page;

/**
 * @author chl_seu
 *
 */
public interface IResultPageHandler<T> extends ResultHandler, IResultHandler<T> {
	/**
	 * 返回分页结果集
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<T> getResult();
	
	/**
	 * 为获取数据总条数预留接口
	 * @return
	 * @deprecated
	 */
	public int getTotalCount();
	
	/**
	 * 为获取数据总条数预留接口,支持从外部设置
	 * @deprecated
	 */
	public void setTotalCount(int totalCount);
}
