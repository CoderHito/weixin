package com.wxbatis.impl.datesource;

import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {  
	  
    /* 
     * (non-Javadoc) 
     * @see javax.sql.CommonDataSource#getParentLogger() 
     */  
    @Override  
    public Logger getParentLogger() {  
        // TODO Auto-generated method stub  
        return null;  
    }  
  
    /** 
     *  
     * override determineCurrentLookupKey 
     * <p> 
     * Title: determineCurrentLookupKey 
     * </p> 
     * <p> 
     * Description: 自动查找datasource 
     * </p> 
     *  
     * @return 
     */  
    @Override  
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getDbType();  
    }
  
} 