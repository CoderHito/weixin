package com.cf.service.sys.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.base.BaseSupport;
import com.cf.biz.domain.Result;
import com.cf.cfsecurity.domain.TbDict;
import com.cf.service.sys.IDictMaintainCheckService;
import com.cf.service.sys.IRollBackService;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 数据字典信息审核
 * 
 * @author hs
 * 
 */
@Service("dictMaintainCheckService")
public class DictMaintainCheckServiceImpl implements IDictMaintainCheckService {
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	private @Autowired IRollBackService rollBackService;
	private static final Logger logger = Logger.getLogger(DictMaintainCheckServiceImpl.class);

	@Override
	public Page<Map<String, String>> queryDict(Map<String, String> params) throws Exception {
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbDictTemp.queryDictTemp", params);
	}

	/**
	 *  数据字典信息审核成功
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Result<T> succRemittance(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<>();
		int ret = -1;
		try {
			TbDict tbDict = (TbDict) this.myBatisSessionTemplate
					.selectOne("orm.sys.tbDictTemp.queryDicttempID", params);
			tbDict.setOP_FLAG(params.get("OP_FLAG"));
			ret = rollBackService.addDict(tbDict,params);
			BaseSupport.CframeUtil.InitDict(BaseSupport.ContextUtil.contextHook());//更新数据字典缓存
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
	 *  数据字典信息审核失败
	 * 
	 * @return
	 * @throws Exception
	 */
	public Result<T> loseRemittance(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<>();
		try {
			rollBackService.delDict(params);
			BaseSupport.CframeUtil.InitDict(BaseSupport.ContextUtil.contextHook());
			rs.setSuccess(true);
			return rs;
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setRetMessage("审核失败");
			return rs;
		}

	}


}
