package com.cf.biz.service.card.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cf.biz.service.card.ICardQueryService;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;


@Service("cardQueryService")
@Transactional(rollbackFor = Exception.class)
public class CardQueryServiceImpl implements ICardQueryService {
	protected final static Logger logger = Logger.getLogger(CardQueryServiceImpl.class);
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	@Override
	public Page<Map<String, String>> queryCardPage(Map<String, String> params) {
		Page<Map<String, String>> pageobj = myBatisSessionTemplate.selectPage("orm.card.cardQueryMapper123.queryCardPage123", params);
		List<Map<String,String>> list = pageobj.getResult();
		for(Map<String,String> m:list){
			m.put("AMOUNT",new BigDecimal(m.get("AMOUNT")).divide(new BigDecimal("100")).toPlainString());
		}
		return pageobj;
//		return this.myBatisSessionTemplate.selectPage("orm.card.cardQueryMapper123.queryCardPage123", params);
	}

	
}

//Mapped Statements collection already contains value for
