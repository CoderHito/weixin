package com.cf.service.sys.impl;

import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.biz.domain.Result;
import com.cf.cfsecurity.domain.TbSysparam;
import com.cf.service.sys.IRollBackService;
import com.cf.service.sys.ISysParamMaintainService;
import com.cf.base.BaseSupport;
import com.cf.util.security.Util;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 类 <code>SysParamMaintainServiceImpl</code>系统参数维护
 * 
 * @author sven
 * @version 20150922
 */
@Service("sysParamMaintainService")
public class SysParamMaintainServiceImpl implements ISysParamMaintainService {
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	@Autowired
	private IRollBackService rollBackService;

	@Override
	public Page<Map<String, String>> queryParam(Map<String, String> params) throws Exception {
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbSysParam.queryParam", params);
	}

	@Override
	public Result<T> saveParam(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<T>();
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbSysParamtemp.querySysParamtempByID",
				params);
		if (count > 0) {
			rs.setSuccess(false);
			rs.setRetMessage("该记录正在进行审核");
			return rs;
		}
		String saveFlag = params.get("saveFlag");
		int ret = -1;
		if ("mod".equals(saveFlag)) {
			TbSysparam tbSysparam = (TbSysparam) this.myBatisSessionTemplate
					.selectOne("orm.sys.tbSysParam.querySysParamByID", params);
			tbSysparam.setOP_FLAG("修改");
			tbSysparam.setMODIFY_USER(BaseSupport.CframeUtil.GetCurrentUserName());
			tbSysparam.setMODIFY_TIME(Util.getCurrentDateTimeString());
			try {
				ret = rollBackService.modSysParam(tbSysparam, params);
				if (ret > 0) {
					rs.setRetMessage("请求已提交，请等待审核");
					rs.setSuccess(true);
				} else {
					rs.setRetMessage("请求提交失败");
					rs.setSuccess(false);
				}
			} catch (Exception e) {
				rs.setSuccess(false);
				rs.setRetMessage("请求提交失败，数据库操作异常");
				return rs;
			}
		}
		if ("add".equals(saveFlag)) {
			Integer paramCount = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbSysParam.queryParamCount",
					params);
			if (paramCount > 0) {
				rs.setRetMessage("该参数已存在！");
				rs.setSuccess(false);
				return rs;
			}
			params.put("IF_CANMODIFY", "1");// 可修改 备用
			params.put("OP_FLAG", "增加");
			params.put("MODIFY_USER", BaseSupport.CframeUtil.GetCurrentUserName());
			params.put("MODIFY_TIME", Util.getCurrentDateTimeString());
			ret = this.myBatisSessionTemplate.insert("orm.sys.tbSysParamtemp.addParamTemp", params);
		}
		if (ret > 0) {
			rs.setRetMessage("请求已提交，请等待审核");
			rs.setSuccess(true);
		} else {
			rs.setRetMessage("请求提交失败");
			rs.setSuccess(false);
		}
		return rs;
	}

	@Override
	public int delParam(Map<String, String> params) throws Exception {
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbSysParamtemp.querySysParamtempByID",
				params);
		if (count > 0) {
			return -1;
		}
		TbSysparam tbSysparam = (TbSysparam) this.myBatisSessionTemplate
				.selectOne("orm.sys.tbSysParam.querySysParamByID", params);
		tbSysparam.setOP_FLAG("删除");
		tbSysparam.setMODIFY_USER(BaseSupport.CframeUtil.GetCurrentUserName());
		tbSysparam.setMODIFY_TIME(Util.getCurrentDateTimeString());
		BaseSupport.myBatisSessionTemplate.insert("orm.sys.tbSysParamtemp.addParamTemp", tbSysparam);
		return 1;
	}

}
