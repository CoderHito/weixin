/**
 * 
 */
package com.cf.biz.service.accounting;

import java.util.Map;

/**
 * 记账接口
 * @author chl_seu
 *
 */
public interface ICardInfoService {
	//通过卡号查询帐号信息
	public Map<String,String> queryCardInfo(Map<String,String> param);
	//通过卡号查询订单号
	public Map<String,String> queryCardOrder(Map<String,String> param);
}
