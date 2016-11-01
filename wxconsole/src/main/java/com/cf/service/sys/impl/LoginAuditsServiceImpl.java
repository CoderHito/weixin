package com.cf.service.sys.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.service.sys.ILoginAuditsService;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 类 <code>LoginAuditsServiceImpl</code>登录日志信息
 * @author sven
 * @version 20150922
 */
@Service("loginAuditsService")
public class LoginAuditsServiceImpl implements ILoginAuditsService {
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;

	@Override
	public Page<Map<String, String>> queryLogin(Map<String, String> params)
			throws Exception {
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbLoginAudits.queryLogin",params);
	}

}
