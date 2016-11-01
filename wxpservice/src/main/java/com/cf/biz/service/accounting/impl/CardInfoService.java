package com.cf.biz.service.accounting.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.biz.service.accounting.ICardInfoService;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

@Service("cardInfoService")
public class CardInfoService implements ICardInfoService{
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	@SuppressWarnings("unchecked")
	public Map<String,String> queryCardInfo(Map<String,String> param){
		return (Map<String,String>)myBatisSessionTemplate.selectOne("orm.account.cardInfo.queryCardInfo", param);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> queryCardOrder(Map<String,String> param){
		return (Map<String,String>)myBatisSessionTemplate.selectOne("orm.account.cardInfo.queryCardOrder", param);
	}
}
