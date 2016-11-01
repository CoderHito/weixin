/**
 * 
 */
package com.cf.biz.service.accounting;

import java.util.HashMap;

/**
 * 记账接口
 * @author chl_seu
 *
 */
public interface IAccountingService {
	//记账总调函数
	public boolean AccountingMain(HashMap<String,String> param);
}
