package com.cf.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.biz.domain.Result;
import com.cf.service.sys.IRollBackService;
import com.cf.service.sys.IUserMaintainCheckService;
import com.cf.service.sys.IUserMaintainService;
import com.cf.cfsecurity.domain.TbUser;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 用户信息审核
 * 
 * @author hs
 * 
 */
@Service("userMaintainCheckService")
public class UserMaintainCheckServiceImpl implements IUserMaintainCheckService {
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	private @Autowired IRollBackService rollBackService;
	private static final Logger logger = Logger.getLogger(UserMaintainCheckServiceImpl.class);

	/**
	 * 用户信息审核查询:
	 * 
	 * @return
	 */
	@Override
	public Page<Map<String, String>> queryUser(Map<String, String> params) throws Exception {
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbUserTemp.queryUserPageTemp", params);
	}
	@Override
	public List<Map<String, String>> queryUserRole(HashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.myBatisSessionTemplate.selectList("orm.sys.tbUser.queryUserRoleTemp", params);
	}
	/**
	 *  用户信息审核成功
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Result<T> succRemittance(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<>();

		int ret = -1;

		try {
			TbUser tbuser = (TbUser) this.myBatisSessionTemplate
					.selectOne("orm.sys.tbUserTemp.queryUsertempByUSER_ID", params);
			tbuser.setOP_FLAG(params.get("OP_FLAG"));
			ret = rollBackService.addUser(tbuser,params);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		if (ret > 0) {// ret大于0 操作数据库成功
			rs.setSuccess(true);
		} else {
			rs.setSuccess(false);
			rs.setRetMessage("审核失败");
		}
		return rs;
	}

	/**
	 *  用户信息审核失败
	 * 
	 * @return
	 * @throws Exception
	 */
	public Result<T> loseRemittance(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<>();
		try {
			rollBackService.delUser(params);
			rs.setSuccess(true);
			return rs;
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setRetMessage("审核失败");
			return rs;
		}

	}


}
