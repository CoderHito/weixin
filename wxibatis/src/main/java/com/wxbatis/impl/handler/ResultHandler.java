/**
 * 
 */
package com.wxbatis.impl.handler;

import org.apache.ibatis.session.ResultContext;

import com.wxbatis.itf.handler.IResultHandler;

/**
 * @author chl_seu
 *
 */
@SuppressWarnings("unchecked")
public class ResultHandler<T> implements IResultHandler<T> {
	private T result;

	public void handleResult(ResultContext context) {
		T resultObject = (T) context.getResultObject();
		result = resultObject;
	}

	
	public T getResult() {
		return result;
	}
}
