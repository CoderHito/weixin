/**
 * 
 */
package com.cf.cfsecurity.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cf.cfsecurity.dao.model.CfResource;
import com.cf.base.AbstractBaseDao;

/**
 * @author chl_seu
 *
 */
public class CfResourceDao extends AbstractBaseDao {
	/**
	 * 查询所有的资源信息
	 * @return
	 */
	public List<CfResource> findAll(){
		return this.getJdbcTemplate().selectList("cf.dao.mapper.CfResourceMapper.findAll");
	}
	
	/**
	 * 根据角色ID查询所包含的资源
	 * @param roleid
	 * @return
	 */
	public List<CfResource> getResourcesByRoleid(String roleid){
		Map<String, String> param = new HashMap<String, String>();
		param.put("roleid", roleid);
		return this.getJdbcTemplate().selectList("cf.dao.mapper.CfResourceMapper.selectResourcesByRoleid",param);
	}
}
