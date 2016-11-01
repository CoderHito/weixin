/**
 * 
 */
package com.cf.cfsecurity.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cf.cfsecurity.dao.model.CfUser;
import com.cf.base.AbstractBaseDao;
import com.wxbatis.impl.batch.Batchmate;

/**
 * @author chl_seu
 * 对用户进行操作
 */
@SuppressWarnings("rawtypes")
public class CfUserDao extends AbstractBaseDao {
	
	/**
	 * 根据用户名查询用户列表
	 * @param username
	 * @return
	 */
	public List<CfUser> findByUserName(String username){
		Map<String, String> param = new HashMap<String, String>();
		param.put("USERNAME", username);
		return this.getJdbcTemplate().selectList("cf.dao.mapper.CfUserMapper.selectUsersByName",param);
	}
	
	/**
	 * 更新user表登录信息
	 * @param username
	 * @return
	 */
	public void LoginForUpdate(HashMap vo){
		Batchmate[] batchmates = new Batchmate[2];
		batchmates[0] = new Batchmate(); 
		batchmates[0].setOptType(Batchmate.TYPE.UPDATE);
		batchmates[0].setStatement("cf.dao.mapper.CfUserMapper.LoginForUpdate");
		batchmates[0].setParameter(vo);
		batchmates[1] = new Batchmate(); 
		batchmates[1].setOptType(Batchmate.TYPE.INSERT);
		batchmates[1].setStatement("cf.dao.mapper.CfUserMapper.LoginForAudit");
		batchmates[1].setParameter(vo);
		this.getJdbcTemplate().batch(batchmates);
	}
	
	/**
	 * 更新user表退出登录信息
	 * @param username
	 * @return
	 */
	public int LogoutForUpdate(HashMap vo){
		return this.getJdbcTemplate().insert("cf.dao.mapper.CfUserMapper.LoginForAudit",vo);
	}
	
	/**
	 * 更新操作日志
	 * @param username
	 * @return
	 */
	public int OpAuditForUpdate(HashMap vo){
		return this.getJdbcTemplate().insert("cf.dao.mapper.SysOperateAuditsMapper.OpAuditForUpdate",vo);
	}

	public List<Map<String,String>> selectBtnByUser(String userId){
		return this.getJdbcTemplate().selectList("cf.dao.mapper.CfUserMapper.selectBtnByUser",userId);
	}
}
