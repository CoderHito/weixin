package com.cf.biz.domain;

public class RowRequest {
      private String titleName;//列的标题名
      
      private String fieldName;//列的字段名
      
      private int columnView;//列的宽度
      
      private boolean doubleflag;
      
      private boolean dateflag;

	  private boolean addflag;
     
	  
	  
	  
	  
	public RowRequest() {
		super();
	}

	public RowRequest(String titleName, String fieldName, int columnView,
			boolean doubleflag, boolean dateflag, boolean addflag) {
		super();
		this.titleName = titleName;
		this.fieldName = fieldName;
		this.columnView = columnView;
		this.doubleflag = doubleflag;
		this.dateflag = dateflag;
		this.addflag = addflag;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getColumnView() {
		return columnView;
	}

	public void setColumnView(int columnView) {
		this.columnView = columnView;
	}


	public boolean isDateflag() {
		return dateflag;
	}

	public void setDateflag(boolean dateflag) {
		this.dateflag = dateflag;
	}

	public boolean isDoubleflag() {
		return doubleflag;
	}

	public void setDoubleflag(boolean doubleflag) {
		this.doubleflag = doubleflag;
	}

	public boolean isAddflag() {
		return addflag;
	}

	public void setAddflag(boolean addflag) {
		this.addflag = addflag;
	}

	

	
      
      
      
}
