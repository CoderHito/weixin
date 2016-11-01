/**
 * 
 */
package com.cf.biz.service.accounting;

import java.util.Map;

import com.cf.common.result.SimpleResult;

/**
 * 消费流水报表接口
 * @author mekio
 *
 */
public interface IConsumeReportService {

	//插入消费流水
	public SimpleResult insertConsumeReport(Map<String,String> param);

}
