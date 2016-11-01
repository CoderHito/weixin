package com.cf.biz.service.accounting.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cf.biz.service.accounting.IAccInfoService;
import com.cf.biz.service.accounting.IAccountingService;
import com.cf.common.result.SimpleResult;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

@Service("accInfoService")
public class AccInfoService implements IAccInfoService{
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	@Autowired
	private IAccountingService accountingService;
	@SuppressWarnings("unchecked")
	public Map<String,String> queryAccByCardNo(Map<String,String> param){
		return (Map<String,String>)myBatisSessionTemplate.selectOne("orm.account.accInfo.queryAccByCardNo", param);
	}
	@SuppressWarnings("unchecked")
	public Map<String,String> queryTransSeq(Map<String,String> param){
		return (Map<String,String>)myBatisSessionTemplate.selectOne("orm.account.accInfo.queryTransSeq", param);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public SimpleResult insertTransDetl(Map<String, String> param) {
		// TODO Auto-generated method stub
		SimpleResult sr = new SimpleResult();
		try{
			
			//根据卡号=账号 查询 卡在系统中的余额    将其维护到该条交易的 消费后余额中
			Map<String,String> accInfo = (Map<String, String>) myBatisSessionTemplate.selectOne("orm.account.accInfo.queryAccByNo",param.get("ACCNO"));
			if(!(null==accInfo)){
				param.put("CONSUME_AMT", accInfo.get("AMT"));
			}
			
			boolean isSuccess=accountingService.AccountingMain((HashMap<String, String>)param);
			this.myBatisSessionTemplate.insert("orm.account.transDetl.insertTransDetl", param);
			if(isSuccess){
				sr.setSuccess(true);
				sr.setRetMessage("交易成功");
			}else{
				sr.setSuccess(false);
				sr.setRetMessage("记账错误");
			}
		}catch(Exception e){
			throw e;
		}
		return sr;
	} 
}
