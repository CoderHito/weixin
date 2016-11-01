package com.cf.biz.domain;

import java.util.List;
import java.util.Map;

public class ExcelRequest {

	private String tableName;//工作表名称
	
	private List<RowRequest>  rlist;//用于 存放列属性的list
	
	private List<Map<String,String>> dataList;
	
	private  boolean  accountflag;//是否求和

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public List<RowRequest> getRlist() {
		return rlist;
	}

	public void setRlist(List<RowRequest> rlist) {
		this.rlist = rlist;
	}

	public List<Map<String, String>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, String>> dataList) {
		this.dataList = dataList;
	}

	public boolean isAccountflag() {
		return accountflag;
	}

	public void setAccountflag(boolean accountflag) {
		this.accountflag = accountflag;
	}
	
	
	
}
