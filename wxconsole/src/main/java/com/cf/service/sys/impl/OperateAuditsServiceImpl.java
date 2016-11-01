package com.cf.service.sys.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.service.sys.IOperateAuditsService;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 类 <code>OperateAuditsServiceImpl</code>操作日志信息
 * @author sven
 * @version 20150922
 */
@Service("operateAuditsService")
public class OperateAuditsServiceImpl implements IOperateAuditsService {
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;

	@Override
	public Page<Map<String, String>> queryOperate(Map<String, String> params)throws Exception{
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbOperateAudits.queryOperate",params);
	}
	

}
