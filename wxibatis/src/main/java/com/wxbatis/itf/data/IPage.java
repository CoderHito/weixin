package com.wxbatis.itf.data;

import java.io.Serializable;
import java.util.List;

public interface IPage<T> extends Serializable {
	/**
	 * 取总记录数.
	 */
	public int getTotalCount();
	
	/**
	 * 取总记录数.
	 */
	public void setTotalCount(int totalCount);

	/**
	 * 取总页数.
	 */
	public int getPageCount();

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize();
	
	/**
	 * 设置每页数据容量.
	 */
	public void setPageSize(int pageSize);

	/**
	 * 取当前页中的记录数据.
	 */
	public List<T> getResult();
	
	/**
	 * 设置当前页的记录数据.
	 */
	public void setResult(List<T> result);
	
	/**
	 * 取该页当前页码,页码从1开始.
	 */  
	public int getPageIndex();

	/**
	 * 设置该页当前页码,页码从1开始.
	 */
	public void setPageIndex(int pageIndex);
	
	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage();

	/**
	 * 该页是否有上一页.  
	 */
	public boolean hasPreviousPage();

	/**
	 * 返回当前页的数据条数
	 * @return
	 */
	public int getCurrentCount();
	
	/**
	 * 返回余下的记录数
	 * @return
	 */
	public int getRemainingCount();
}
