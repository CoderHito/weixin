/**
 * 
 */
package com.cf.util.common;

import javax.servlet.ServletContext;

/**
 * @author chl_seu
 * 缓存对象访问操作类
 */
public interface ICframeUtil {
	/**
	 * 访问系统参数值
	 * @param PARAMGROUP_ID
	 * @param PARAM_ID
	 * @return 系统参数值
	 */
	public String getSysParamsValue(String PARAMGROUP_ID,String PARAM_ID);
	
	/**
	 * 设值系统参数值
	 * @param PARAMGROUP_ID
	 * @param PARAM_ID
	 * @param PARAM_VAL
	 * @return ture设置成功，false设置失败
	 */
	public boolean setSysParamsValue(String PARAMGROUP_ID,String PARAM_ID,String PARAM_VAL);
	
	
	/**
	 * 初始化系统参数
	 * @return
	 */
	public boolean InitSysParams(ServletContext servletContext);
	
	/**
	 * 初始化数据字典
	 * @return
	 */
	public boolean InitDict(ServletContext servletContext);
	
	public String GetCurrentLongTime();
	
	public String GetCurrentDay();
	public String GetYesterDay();
	
	public String GetCurrentUserName();
	/**
	 * 获取当前用户的部门号
	 * @return
	 */
	public String GetCurrentBranchNo();
	/**
	 * 获取当前用户的可显示的按钮
	 * @return
	 */
	public String GetCurrentButtons();
	/**
	 * 判断文件是否在导入，文件是否正在生成
	 */
	public boolean ifFileOperating();
	
	/**
	 * 将String类型的 分单位金额转换为 元单位
	 * @param amount
	 * @return
	 */
	public String changeF2Y(String amount);
	
	public String changeY2F(String amount);
	
	public String plu2String(String str1, String str2);
	
	public String subtract2String(String str1, String str2);
}
