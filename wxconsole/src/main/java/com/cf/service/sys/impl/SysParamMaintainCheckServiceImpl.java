package com.cf.service.sys.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.base.BaseSupport;
import com.cf.biz.domain.Result;
import com.cf.cfsecurity.domain.TbSysparam;
import com.cf.service.sys.IRollBackService;
import com.cf.service.sys.ISysParamMaintainCheckService;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 系统参数信息审核
 * 
 * @author hs
 * 
 */
@Service("sysParamMaintainCheckService")
public class SysParamMaintainCheckServiceImpl implements ISysParamMaintainCheckService {
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	private @Autowired IRollBackService rollBackService;
	private static final Logger logger = Logger.getLogger(SysParamMaintainCheckServiceImpl.class);

	@Override
	public Page<Map<String, String>> queryParam(Map<String, String> params) throws Exception {
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbSysParamtemp.queryParamTemp", params);
	}

	/**
	 *  系统参数信息审核成功
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Result<T> succRemittance(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<>();

		int ret = -1;

		try {
			TbSysparam tbSysparam = (TbSysparam) this.myBatisSessionTemplate
					.selectOne("orm.sys.tbSysParamtemp.SysParamtempByID", params);
			tbSysparam.setOP_FLAG(params.get("OP_FLAG"));
			ret = rollBackService.addSysParam(tbSysparam,params);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		if (ret > 0) {// ret大于0 操作数据库成功
			rs.setSuccess(true);
			BaseSupport.CframeUtil.InitSysParams(BaseSupport.ContextUtil.contextHook());//更新系统参数缓存
		} else {
			rs.setSuccess(false);
			rs.setRetMessage("审核失败");
		}
		return rs;
	}

	/**
	 *  系统参数信息审核失败
	 * 
	 * @return
	 * @throws Exception
	 */
	public Result<T> loseRemittance(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<>();
		try {
			rollBackService.delSysParam(params);
			rs.setSuccess(true);
			BaseSupport.CframeUtil.InitSysParams(BaseSupport.ContextUtil.contextHook());//更新系统参数缓存
			return rs;
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setRetMessage("审核失败");
			return rs;
		}

	}


}
