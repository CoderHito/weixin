package com.cf.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.biz.domain.Result;
import com.cf.cfsecurity.domain.TbRole;
import com.cf.service.sys.IRoleMaintainCheckService;
import com.cf.service.sys.IRollBackService;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 角色信息审核
 * 
 * @author hs
 * 
 */
@Service("roleMaintainCheckService")
public class RoleMaintainCheckServiceImpl implements IRoleMaintainCheckService {
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	private @Autowired IRollBackService rollBackService;
	private static final Logger logger = Logger.getLogger(RoleMaintainCheckServiceImpl.class);

	/**
	 * 角色信息审核查询:
	 * 
	 * @return
	 */
	@Override
	public Page<Map<String, String>> queryRole(Map<String, String> params) throws Exception {
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbRoleTemp.queryRoleTemp", params);
	}
	@Override
	public List<Map<String, String>> queryMenu(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.myBatisSessionTemplate.selectList("orm.sys.tbRole.queryMenuTemp", params);
	}
	/**
	 *  角色信息审核成功
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Result<T> succRemittance(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<>();

		int ret = -1;

		try {
			TbRole tbrole = (TbRole) this.myBatisSessionTemplate
					.selectOne("orm.sys.tbRoleTemp.queryRoletempByROLE_ID", params);
			tbrole.setOP_FLAG(params.get("OP_FLAG"));
			ret = rollBackService.addRole(tbrole,params);
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
	 *  角色信息审核失败
	 * 
	 * @return
	 * @throws Exception
	 */
	public Result<T> loseRemittance(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<>();
		try {
			rollBackService.delRole(params);
			rs.setSuccess(true);
			return rs;
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setRetMessage("审核失败");
			return rs;
		}

	}


}
