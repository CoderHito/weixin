package com.cf.service.sys.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cf.base.BaseSupport;
import com.cf.service.sys.IUniquenessService;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

@Service("uniquenessService")
public class UniquenessServiceImpl implements IUniquenessService{
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplat;
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getUniqueness(int num){
		String CURRENT_DAY =BaseSupport.CframeUtil.GetCurrentDay();
		Map<String,String> map = (Map<String,String>)myBatisSessionTemplat.selectOne("orm.sys.bizTransId.selectSeries");
		int ss=1;
		Map<String,String> params= new HashMap<>();
		if(CURRENT_DAY.equals(map.get("CURRENT_DAY"))){
			String series= map.get("SERIES");
			ss=Integer.parseInt(series);
			ss++;
		}
		params.put("SERIES", ss+"");
		params.put("CURRENT_DAY", CURRENT_DAY);
		myBatisSessionTemplat.update("orm.sys.bizTransId.updateSeries",params);
		String str = String.format("%0"+num+"d", ss);
		return str;
	}
}
