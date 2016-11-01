package com.cf.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.base.BaseSupport;
import com.cf.biz.domain.Result;
import com.cf.cfsecurity.domain.TbDict;
import com.cf.service.sys.IDictMaintainService;
import com.cf.service.sys.IRollBackService;
import com.cf.util.security.Util;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 类 <code>DictMaintainServiceImpl</code>数据字典维护，主要包括增删改查功能
 * @author sven
 * @version 20150922
 */
@Service("dictMaintainService")
public class DictMaintainServiceImpl implements IDictMaintainService {
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	@Autowired
	private IRollBackService rollBackService;
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,String>> getDict(String gp) {
	    HashMap<String,List<Map<String, String>>> dictMap = (HashMap<String,List<Map<String, String>>>) BaseSupport.ContextUtil.contextHook().getAttribute("sysDict");
		return (List<Map<String, String>>)dictMap.get(gp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, List<Map<String, String>>> getDictMap(List <String> listGp){
	    HashMap<String,List<Map<String, String>>> dictMap = (HashMap<String,List<Map<String, String>>>) BaseSupport.ContextUtil.contextHook().getAttribute("sysDict");	
	    HashMap<String,List<Map<String, String>>> returndictMap = new HashMap<String, List<Map<String,String>>>();    
	    for(int i=0; i<listGp.size(); i++){
	    	returndictMap.put(listGp.get(i), (List<Map<String, String>>)dictMap.get(listGp.get(i)));
	    }    
	    
	    return returndictMap;
	}
	
	@Override
	public boolean refreshDict() {
		return BaseSupport.CframeUtil.InitDict(BaseSupport.ContextUtil.contextHook());
	}

	@Override
	public Page<Map<String, String>> queryDict(Map<String, String> params) throws Exception {
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbDict.queryDict",params);
	}

	@Override
	public Result<T> saveDict(Map<String, String> params) throws Exception{
		Result<T> rs = new Result<T>();
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbDictTemp.queryDicttempByID",
				params);
		if (count > 0) {
			rs.setSuccess(false);
			rs.setRetMessage("该记录正在进行审核");
			return rs;
		}
		String saveFlag = params.get("saveFlag");
		int ret =-1;
		if("mod".equals(saveFlag)){
			TbDict tbDict = (TbDict) this.myBatisSessionTemplate
					.selectOne("orm.sys.tbDict.queryDictByID", params);
			tbDict.setOP_FLAG("修改");
			tbDict.setMODIFY_USER(BaseSupport.CframeUtil.GetCurrentUserName());
			tbDict.setMODIFY_TIME(Util.getCurrentDateTimeString());
			try {
				ret = rollBackService.modDict(tbDict, params);
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
		if("add".equals(saveFlag)){
			Integer paramCount = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbDict.queryDictCount", params);
			if(paramCount>0){
				rs.setRetMessage("该参数已存在！");
				rs.setSuccess(false);
				return rs;
			}
			params.put("OP_FLAG", "增加");
			params.put("MODIFY_USER", BaseSupport.CframeUtil.GetCurrentUserName());
			params.put("MODIFY_TIME", Util.getCurrentDateTimeString());
			ret = this.myBatisSessionTemplate.insert("orm.sys.tbDictTemp.addDictTemp", params);
		}
		if(ret > 0){
			rs.setSuccess(true);
			rs.setRetMessage("请求已提交，请等待审核");
		}else{
			rs.setSuccess(false);
			rs.setRetMessage("请求提交失败");
		}
		return rs;
	}

	@Override
	public int delDict(Map<String, String> params) throws Exception {
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbDictTemp.queryDicttempByID",
				params);
		if (count > 0) {
			return -1;
		}
		TbDict tbDict = (TbDict) this.myBatisSessionTemplate
				.selectOne("orm.sys.tbDict.queryDictByID",params);
		if(tbDict==null)
		{
			return -2;
		}
		tbDict.setOP_FLAG("删除");
		tbDict.setMODIFY_USER(BaseSupport.CframeUtil.GetCurrentUserName());
		tbDict.setMODIFY_TIME(Util.getCurrentDateTimeString());
		BaseSupport.myBatisSessionTemplate.insert("orm.sys.tbDictTemp.addDictTemp", tbDict);
		return 1;
	}

}
