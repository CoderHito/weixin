package com.wxbatis.impl.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wxbatis.itf.data.IPage;

public class Page<T> implements IPage<T> {
private static final long serialVersionUID = -1681785874646754028L;
	
	private static final Logger logger = Logger.getLogger(Page.class);

	private static int DEFAULT_PAGE_SIZE = 20;
	
	private int pageIndex = 1;					// 页号, 从1开始计数
	private int pageSize = DEFAULT_PAGE_SIZE; 	// 每页的记录数
	private int totalCount; // 总记录数
	private List<T> data; // 当前页中存放的记录,类型一般为List
	
	/**
	 * 构造方法，只构造空页.
	 */
	public Page() {
		this(0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	/**
	 * 默认构造方法.
	 *
	 * @param pageIndex	   页号
	 * @param totalSize  数据库中总记录条数
	 * @param pageSize   本页容量
	 * @param data	             本页包含的数据
	 */
	public Page(int pageIndex, int pageSize, List<T> data) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.data = data;
	}

	/**
	 * 取总记录数.
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 取总页数.
	 */
	public int getTotalPageCount() {
		if (totalCount % pageSize == 0) {
			return totalCount / pageSize;
		} else {
			return totalCount / pageSize + 1;
		}
	}

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 * 取当前页中的记录.
	 */
	public List<T> getResult() {
		return (data == null)?new ArrayList<T>():data;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	public int getPageIndex() {
		return this.pageIndex;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getPageIndex() < this.getTotalPageCount() - 1;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return this.getPageIndex() > 1;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 *
	 * @see #getStartOfPage(int,int)
	 */
	public int getStartOfPage(int pageIndex) {
		return getStartOfPage(pageIndex, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageIndex   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageIndex, int pageSize) {
		return (pageIndex - 1) * pageSize;
	}
	
	public int getCurrentCount() {
		return (data == null)? 0 : ((List<T>)data).size();
	}

	public int getPageCount() {
		if(this.data == null || this.data.size() == 0) {
			return 0;
		} else if(this.getPageSize() > 0) {
			return (int) Math.floor(this.getTotalCount() / this.getPageSize());			
		} else {
			return 1;
		}
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setResult(List<T> result) {
		this.data = result;
	}

	public int getRemainingCount() {
		int remain = this.getTotalCount() - (this.getPageIndex() - 1) * this.getPageSize() - this.getCurrentCount();
		logger.trace(new StringBuilder().append("TotalCount[").append(this.getTotalCount())
				.append("], PageIndex[").append(this.getPageIndex()).append("], PageSize[")
				.append(this.getPageSize()).append("], Current Count[").append(this.getCurrentCount())
				.append("], Remain[").append(remain).append("].").toString());
		return remain;
	}
}
