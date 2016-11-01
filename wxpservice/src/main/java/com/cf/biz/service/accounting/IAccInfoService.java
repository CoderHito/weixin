/**
 * 
 */
package com.cf.biz.service.accounting;

import java.util.Map;

import com.cf.common.result.SimpleResult;

/**
 * 记账接口
 * @author chl_seu
 *
 */
public interface IAccInfoService {
	//通过卡号查询帐号信息
	public Map<String,String> queryAccByCardNo(Map<String,String> param);
	//插入交易明细
	public SimpleResult insertTransDetl(Map<String,String> param);
	//通过原平台交易流水号查询交易是否存在   消费撤销用
	public Map<String,String> queryTransSeq(Map<String,String> param);
}
