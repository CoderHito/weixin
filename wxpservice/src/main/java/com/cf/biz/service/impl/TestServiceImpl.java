package com.cf.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cf.biz.service.ITestService;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

@Service("testService")
public class TestServiceImpl implements ITestService{
	private final static Logger logger = Logger.getLogger(TestServiceImpl.class);
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void test() throws Exception{
		try{
			myBatisSessionTemplate.delete("orm.common.dataLock.unLockData");
			int i=1/0;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw e;
		}
	}
}
