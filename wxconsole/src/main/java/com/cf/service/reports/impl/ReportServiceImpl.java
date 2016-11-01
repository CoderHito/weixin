package com.cf.service.reports.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cf.biz.domain.SimpleResult;
import com.cf.service.reports.ReportService;
import com.cf.util.constant.CommonConstant;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

@Service("reportService")
public class ReportServiceImpl implements ReportService {

	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	
	@Override
	public Page<Map<String, String>> getTransDetail(Map<String, String> params) {
		return myBatisSessionTemplate.selectPage("orm.report.queryTransDetailPage",params);
	}
	@Override
	public List<Map<String, String>> getTransDetailReport(Map<String, String> params) {
		return myBatisSessionTemplate.selectList("orm.report.queryTransDetail",params);	
	}
	
	@Override
	public Page<Map<String, String>> getOrderBalanceSummary(HashMap<String, String> params) {
		return myBatisSessionTemplate.selectPage("orm.report.queryOrderBalanceSummaryPage",params);
	}
	@Override
	public List<Map<String, String>> getOrderBalanceSummaryReport(HashMap<String, String> params) {
		return myBatisSessionTemplate.selectList("orm.report.queryOrderBalanceSummary",params);	
	}
	@Override
	public Page<Map<String, String>> getTransDaily(HashMap<String, String> params) {
		return myBatisSessionTemplate.selectPage("orm.report.queryTransDailyPage", params);
	}
	
	@Override
	public Page<Map<String, String>> getConsumeDetail(Map<String, String> params){
		return myBatisSessionTemplate.selectPage("orm.report.queryConsumeDetailPage",params);
	}
	@Override
	public List<Map<String, String>> getConsumeReport(Map<String, String> params){
		return myBatisSessionTemplate.selectList("orm.report.queryConsumeDetail",params);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public SimpleResult sureTransDaily(Map<String, String> params) {
		SimpleResult sr=new SimpleResult();
		params.put("CLEARING_STATUS", CommonConstant.CLEARING_NO);
		Object consumeAmt=myBatisSessionTemplate.selectOne("orm.report.selectConsumeAmount",params);
		if(null==consumeAmt){
			sr.setFailMsg("当天已清算");
			return sr;
		}
		params.put("CONSUME_AMOUNT", consumeAmt.toString());
		params.put("ACCTYPE", CommonConstant.ACC_TYPE_FOREIGN_SETTLE);
		myBatisSessionTemplate.update("orm.report.updateSideAccInfo",params);
		params.put("CLEARING_STATUS", CommonConstant.CLEARING_YES);
		sr.setSuccess(true);
		myBatisSessionTemplate.update("orm.report.updateClearingStatus",params);
		sr.setRetMessage(CommonConstant.SETL_SUCCESS);
		return sr;
	}
	
	@Override
	public List<Map<String, String>> getCardInfoByOrderNo(HashMap<String, String> params){
		List<Map<String,String>> cardlList = myBatisSessionTemplate.selectList("orm.card.bizOrderDtl.queryOrderDtlByOrderNo",params);
		return cardlList;
	}

}