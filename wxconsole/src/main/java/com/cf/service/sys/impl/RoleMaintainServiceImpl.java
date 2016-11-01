package com.cf.service.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.base.BaseSupport;
import com.cf.biz.domain.Result;
import com.cf.cfsecurity.domain.TbRole;
import com.cf.service.sys.IRoleMaintainService;
import com.cf.service.sys.IRollBackService;
import com.cf.util.security.Util;
import com.wxbatis.impl.batch.Batchmate;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;
/**
 * 类 <code>RoleMaintainServiceImpl</code>角色维护
 * @author sven
 * @version 20150922
 */
@Service("roleMaintainService")
public class RoleMaintainServiceImpl implements IRoleMaintainService {
	private static final Logger logger = Logger.getLogger(RoleMaintainServiceImpl.class);
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	@Autowired
	private IRollBackService iRollBackService;
	@Override
	public Page<Map<String, String>> queryRole(Map<String, String> params)
			throws Exception {
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbRole.queryRole", params);
	}

	@Override
	public List<Map<String, String>> queryMenu(Map<String, Object> params)
			throws Exception {
		return this.myBatisSessionTemplate.selectList("orm.sys.tbRole.queryMenu", params);
	}
	@Override
	public List<Map<String, String>> queryAddMenu(Map<String, Object> params)
			throws Exception {
		return this.myBatisSessionTemplate.selectList("orm.sys.tbRole.queryAddMenu", params);
	}

	@Override
	public int saveMenu(Map<String, String> params,List list) throws Exception {
		try {
			/*JSONArray jsonR = JSONArray.fromObject("["+params.get("params")+"]");
			logger.info("角色的菜单："+jsonR);*/
			ArrayList<Batchmate> batchmates=new ArrayList<Batchmate>();
			//List test=(List) JSONArray.toCollection(jsonR);
			for (int k=0;k<list.size();k++){
				HashMap<String,String> tmpmap = (HashMap<String, String>) list.get(k);;
				tmpmap.put("CREATOR", BaseSupport.CframeUtil.GetCurrentUserName());
				tmpmap.put("CREATE_TIME", Util.getCurrentDateTimeString());
				tmpmap.put("RES_ID", tmpmap.get("id"));
				Batchmate bt = new Batchmate();
				bt.setOptType(Batchmate.TYPE.INSERT);
				bt.setStatement("orm.sys.tbRole.insertRoleTempMenu");
				bt.setParameter(tmpmap);
			batchmates.add(bt);
			}
			Batchmate bt2 = new Batchmate();
			bt2.setOptType(Batchmate.TYPE.INSERT);
			bt2.setStatement("orm.sys.tbRoleTemp.addRole");
			bt2.setParameter(params);
		    batchmates.add(bt2);
			Batchmate[] batchmates1 = new Batchmate[batchmates.size()];
			for (int i =0;i<batchmates.size();i++ ){
				batchmates1[i]=batchmates.get(i);
			}
			return this.myBatisSessionTemplate.batch(batchmates1);
		} catch (Exception e) {
			return 0;
		}	  
	}

	@Override
	public Result<T> saveRole(Map<String, String> params,List list) throws Exception {
		Result<T> rs = new Result<T>();
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbRoleTemp.RoletempByROLE_ID", params);	
		if(count>0)
		{
			rs.setSuccess(false);
			rs.setRetMessage("该记录正在进行审核");
			return rs;
		}
		//转换布尔型
		String isENABLED = params.get("ENABLED");
		if ("true".equalsIgnoreCase(isENABLED)) {
			params.put("ENABLED","1");
		} else {
			params.put("ENABLED","0");
		}
		String saveFlag = params.get("saveFlag");
		int ret = -1;
		int save= -1;
		if ("mod".equals(saveFlag)) {
			TbRole tbRole=(TbRole)this.myBatisSessionTemplate.selectOne("orm.sys.tbRole.queryRoleByROLE_ID",params);	
			tbRole.setOP_FLAG("修改");
			try {
				ret=iRollBackService.modRole(tbRole,params,list);
				save=1;
			} catch (Exception e) {
				rs.setSuccess(false);
				rs.setRetMessage("审核失败，数据库操作异常");
				return rs;
			}
		}
		if ("add".equals(saveFlag)) {
			Integer roleCount = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbRole.selectRoleCount", params);
			if (roleCount == 0) {
				params.put("OP_FLAG","增加");
				save=saveMenu(params,list);
				ret=1;
			} else {
				rs.setRetMessage("角色代码已存在，不能新增！");
				rs.setSuccess(false);
				return rs;
			}
		}
		if(ret>0&&save>0){//库操作成功
			rs.setSuccess(true);
		}else{
			rs.setRetMessage("保存失败！");
			rs.setSuccess(false);
		}
		return rs;
	}

	@Override
	public Result<T> delRole(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<T>();
		Integer roleUsedCount = (Integer) myBatisSessionTemplate.selectOne("orm.sys.tbRole.queryUsedRole", params);
		if (roleUsedCount > 0) {
			rs.setRetMessage("角色还在使用中，不能删除！");
			rs.setSuccess(false);
			return rs;
		}
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbRoleTemp.RoletempByROLE_ID", params);	
		if(count>0)
		{
			rs.setSuccess(false);
			rs.setRetMessage("该记录正在进行审核");
			return rs;
		}
		TbRole tbRole=(TbRole)this.myBatisSessionTemplate.selectOne("orm.sys.tbRole.queryRoleByROLE_ID",params);	
		try {
			tbRole.setOP_FLAG("删除");
			BaseSupport.myBatisSessionTemplate.insert("orm.sys.tbRoleTemp.CopyRole",tbRole);
			rs.setSuccess(true);
			rs.setRetMessage("请求已提交，请等待审核");
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setRetMessage("删除角色失败！");
		}	  
		return rs;
	}

}
