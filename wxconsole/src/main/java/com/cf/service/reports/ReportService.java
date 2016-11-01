package com.cf.service.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cf.biz.domain.SimpleResult;
import com.wxbatis.impl.data.Page;


public interface ReportService {
	
	//获取交易明细页面
	public Page<Map<String, String>> getTransDetail(Map<String, String> params);
	
	//获取交易明细报表
	public List<Map<String, String>> getTransDetailReport(Map<String, String> params);

	//获取订单余额汇总页面
	public Page<Map<String, String>> getOrderBalanceSummary(HashMap<String, String> params);

	//获取订单余额汇总报表
	public List<Map<String, String>> getOrderBalanceSummaryReport(HashMap<String, String> params);

	//获取交易日报表页面
	public Page<Map<String, String>> getTransDaily(HashMap<String, String> params);
	
	//确认
	public SimpleResult	sureTransDaily(Map<String, String> params);
	
	//根据订单号获取 订单里的卡信息
	public List<Map<String, String>> getCardInfoByOrderNo(HashMap<String, String> params);
	
	//获取消费流水
	public Page<Map<String, String>> getConsumeDetail(Map<String, String> params);
	//生成交易流水报表
	public List<Map<String, String>> getConsumeReport(Map<String, String> params);
	
}
