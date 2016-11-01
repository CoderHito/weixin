/**
 * 
 */
package com.wxbatis.impl.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ResultContext;

import com.wxbatis.itf.handler.IResultListHandler;

/**
 * @author chl_seu
 *
 */
@SuppressWarnings("unchecked")
public class ResultListHandler<T> implements IResultListHandler<T> {
	private final List<T> list = new ArrayList<T>();

	public void handleResult(ResultContext context) {
		list.add((T) context.getResultObject());
	}

	public List<T> getResult() {
		return list;
	}

	public int getTotalCount() {
		return list.size();
	}
}
