/**
 * 
 */
package com.wxbatis.impl.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ResultContext;

import com.wxbatis.impl.data.Page;
import com.wxbatis.itf.handler.IResultPageHandler;

/**
 * @author chl_seu
 *
 */
@SuppressWarnings("unchecked")
public class ResultPageHandler<T> implements IResultPageHandler<T> {
	private int pageIndex;
	private int pageSize;
	private int totalCount;
	private final List<T> list = new ArrayList<T>();
	
	public ResultPageHandler(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public void handleResult(ResultContext context) {
		T resultObject = (T) context.getResultObject();
		list.add(resultObject);
	}

	public Page<T> getResult() {
		Page<T> page = new Page<T>(pageIndex, pageSize, list);
		page.setTotalCount(this.getTotalCount());
		return page;
	}
	
	public int getPageSize() {
		return this.pageSize;
	}
	
	public int getPageIndex() {
		return this.pageIndex;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
