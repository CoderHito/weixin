/**
 * 
 */
package com.wxbatis.itf.handler;

import org.apache.ibatis.session.ResultHandler;

/**
 * @author chl_seu
 * @param <T>
 */
public interface IResultHandler<T> extends ResultHandler {
	/**
	 * 返回唯一结果
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T getResult();
}
