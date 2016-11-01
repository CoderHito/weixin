/**
 * 
 */
package com.cf.cfsecurity.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cf.cfsecurity.dao.model.CfRole;
import com.cf.base.AbstractBaseDao;

/**
 * @author chl_seu
 *
 */
public class CfRoleDao extends AbstractBaseDao {
	/**
	 * 根据用户查找角色
	 * @param userid
	 * @return
	 */
	public List<CfRole> findByUserId(String userid){
		Map<String, String> param = new HashMap<String, String>();
		param.put("userid", userid);
		param.put("userid", userid);
		return this.getJdbcTemplate().selectList("cf.dao.mapper.CfRoleMapper.selectRoleByUserID",param);
	}
}
