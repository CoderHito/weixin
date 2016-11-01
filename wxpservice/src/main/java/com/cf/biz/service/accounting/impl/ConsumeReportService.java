package com.cf.biz.service.accounting.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cf.biz.service.accounting.IAccountingService;
import com.cf.biz.service.accounting.IConsumeReportService;
import com.cf.common.result.SimpleResult;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

@Service("consumeReportService")
public class ConsumeReportService implements IConsumeReportService{
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	@Autowired
	private IAccountingService accountingService;
	@Override
	public SimpleResult insertConsumeReport(Map<String, String> param) {
		
		SimpleResult sr = new SimpleResult();
		try {
			this.myBatisSessionTemplate.insert("orm.account.consumeReport.insertConsumeReport", param);
			sr.setSuccess(true);
			sr.setRetMessage("插入交易流水成功");
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
		return sr;
	}
}
