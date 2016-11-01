package com.wxbatis.impl.datesource;

import java.util.Random;

public class DBContextHolder {  
	  
    /** 
     * 线程threadlocal 
     */  
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
  
    public static String DB_TYPE_RW = "dataSourceKeyRW";  
    public static String DB_TYPE_R = "dataSourceKeyR";
    public static int RANDOM_NUM = 4;//读数据库的个数
  
    public static String getDbType() {
        String db = contextHolder.get();  
        if (db == null) {  
            db = DB_TYPE_RW;// 默认是读写库  
        }
        //System.out.println(db);
        return db;  
    }  
  
    /** 
     *  
     * 设置本线程的dbtype 
     *  
     * @param str 
     * @see [相关类/方法](可选) 
     * @since [产品/模块版本](可选) 
     */  
    public static void setDbType(String str) {
    	if(str.equals("dataSourceKeyRW")){
    		contextHolder.set(str);
    	}else{
    		Random r = new Random();
    		int remainder=r.nextInt(100)%RANDOM_NUM;
    		str+=remainder;
    		contextHolder.set(str);
    	}
    }  
  
    /** 
     * clearDBType 
     *  
     * @Title: clearDBType 
     * @Description: 清理连接类型 
     */  
    public static void clearDBType() {  
        contextHolder.remove();  
    }  
}