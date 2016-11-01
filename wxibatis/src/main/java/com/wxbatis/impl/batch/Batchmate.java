/**
 * 
 */
package com.wxbatis.impl.batch;

import com.wxbatis.itf.handler.IResultHandler;
import com.wxbatis.itf.handler.IResultListHandler;

/**
 * @author chl_seu
 *
 */
public class Batchmate {

	public static enum TYPE {
		INSERT, UPDATE, DELETE, SELECT_ONE, SELECT_LIST, SELECT_PAGE
	}
	
	private String statement;
	private TYPE optType;
	private Object parameter;
	private int pageIndex;
	private int pageSize;
	
	private IResultHandler<?> resultHandler;

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public TYPE getOptType() {
		return optType;
	}

	public void setOptType(TYPE optType) {
		this.optType = optType;
	}

	public Object getParameter() {
		return parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	/**
	 * ��OptTypeΪSELECT_ONEʱ����ȡ���õĽ������
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> IResultHandler<T> getResultHandler() {
		return ((IResultHandler<T>) resultHandler);
	}
	
	/**
	 * ��OptTypeΪSELECT_LISTʱ����ȡ���õĽ������
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> IResultListHandler<T> getResultListHandler() {
		return (IResultListHandler<T>) resultHandler;
	}
	
	/**
	 * ��OptTypeΪSELECT_PAGEʱ����ȡ���õĽ������
	 * @param <T>
	 * @return
	 */
	/*
	 * public <T> IResultPageHandler<T> getResultPageHandler() {
		return (IResultPageHandler<T>) resultHandler;
	}
	 */
	public <T> void setResultHandler(IResultHandler<T> resultHandler) {
		this.resultHandler = resultHandler;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Batchmate [optType=").append(optType).append(", pageIndex=").append(pageIndex)
				.append(", pageSize=").append(pageSize).append(", parameter=").append(parameter).append(", resultHandler=")
				.append(resultHandler).append(", statement=").append(statement).append("]").toString();
	}
}
