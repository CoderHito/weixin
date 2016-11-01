/**
 * 
 */
package com.cf.util.constant;


/**
 * @author chl_seu
 * 定义系统参数表段号及键值 
 */
public class SysParamsDefine {
	//批量标志位
	public static String GROUP_BATCH_FLAG = "0001";
	//导入路径段 
	public static String GROUP_RESOURCE_PATH = "0002";
	
	public static String GROUP_MENU_CHANGE = "9001"; 
	
	public static String BATCH_FLAG_EUDA_IMPORT = "0001";
	public static String BATCH_FLAG_EUDA_FILE_GENERATE = "0002";
	
	public static String RESOURCE_PATH_EUDA_IMPORTFILE_BASE = "0001";
	public static String RESOURCE_PATH_EUDA_IMPORTFILE_SUCC = "0002";
	public static String RESOURCE_PATH_EUDA_IMPORTFILE_ERR = "0003";
	
	public static String MENU_CHANGE = "0001";
	
	public static String GROUP_CHECKER_CHANGE="0003"; //自审与非自审的切换
	public static String CHECKER_CHANGE="0001";
	
	public static String GROUP_LEAD="0001"; 
	public static String GROUP_LEAD_CHANGE="0001";//导入与非导入文件
	public static String GROUP_CREATE_FILE="0002";//文件生成与非生成中
	public static String GROUP_CREATE="0002"; 
	public static String GROUP_CREATE_CHANGE="0001";

	public static String GROUP_PWD_CHANGE = "9002"; //是否启用第一次登录修改密码
	public static String CODE_PWD_CHANGE = "0001";
	
	public static String GROUP_VERSION_CHANNEL = "9003"; //版本号
	public static String VERSION_CHANNEL = "0001";
	
	public static String JG_PARAM_GROUP_ID = "8080" ; 
	public static String JG_PARAM_ID = "8080" ; 
	
	public static String FTP_INFORMATION_GROUP = "7001";	//FTP信息
	public static String FTP_HOST = "0001";
	public static String FTP_PORT = "0002";
	public static String FTP_USERNAME = "0003";
	public static String FTP_PASSWORD = "0004";
	public static String FTP_DOWNLOAD_PATH = "0005";
	
	public static String SSL_GROUP = "6001";
	public static String SSL_PASSWORD = "0001";
	public static String SSL_STORE_PATH = "0002";
	public static String SSL_TRUST_PATH = "0003";
	
	public static String ACCTIME_GROUP = "5001";
	public static String ACCTIME_TIME = "0001";
}
