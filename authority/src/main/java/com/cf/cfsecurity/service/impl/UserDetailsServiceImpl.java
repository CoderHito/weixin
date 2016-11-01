/**
 * 
 */
package com.cf.cfsecurity.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cf.cfsecurity.constant.DictConstant;
import com.cf.cfsecurity.dao.impl.CfResourceDao;
import com.cf.cfsecurity.dao.impl.CfRoleDao;
import com.cf.cfsecurity.dao.impl.CfUserDao;
import com.cf.cfsecurity.dao.model.CfResource;
import com.cf.cfsecurity.dao.model.CfRole;
import com.cf.cfsecurity.dao.model.CfUser;
import com.cf.base.CustomUser;

import net.sf.json.JSONObject;

/**
 * @author chl_seu
 * 
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
	
	private CfUserDao cfUserDao;	
	
	private CfRoleDao cfRoleDao;
	
	private CfResourceDao cfResourceDao;

	public CfUserDao getCfUserDao() {
		return cfUserDao;
	}

	public void setCfUserDao(CfUserDao cfUserDao) {
		this.cfUserDao = cfUserDao;
	}

	public CfRoleDao getCfRoleDao() {
		return cfRoleDao;
	}

	public void setCfRoleDao(CfRoleDao cfRoleDao) {
		this.cfRoleDao = cfRoleDao;
	}

	public CfResourceDao getCfResourceDao() {
		return cfResourceDao;
	}

	public void setCfResourceDao(CfResourceDao cfResourceDao) {
		this.cfResourceDao = cfResourceDao;
	}

	// 登录验证
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		System.out.println("username is " + username);
		// 这里应该可以不用再查了
		try {
			List<CfUser> users = this.cfUserDao.findByUserName(username);
			CfUser cfUser = users.get(0);
			Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(cfUser);

			boolean enables = false;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			if(DictConstant.USER_STATUS_NORMAL.equals(cfUser.getStatus())){
				enables = true;
			}
			//查出该用户所有的按钮权限
			List<Map<String,String>> buttonList = this.cfUserDao.selectBtnByUser(cfUser.getUserId());
			Map<String,List<String>> map = new HashMap<String, List<String>>();
			for(Map<String,String> m:buttonList){
				String parentId = m.get("PARENT_ID");
				String buttonId = m.get("BUTTON_ID");
				if(map.containsKey(parentId)){
					map.get(parentId).add(buttonId);
				}else{
					List<String> l = new ArrayList<String>();
					l.add(buttonId);
					map.put(parentId, l);
				}
			}
			JSONObject jsonObject = JSONObject.fromObject(map);			
			
			// 封装成spring security的user
			CustomUser userdetail = new CustomUser(jsonObject.toString(),cfUser.getBranchNo(), cfUser.getUserId(),
					cfUser.getPasswd(), enables, accountNonExpired,
					credentialsNonExpired, accountNonLocked, grantedAuths);
			return userdetail;
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			throw new UsernameNotFoundException("用户/密码错误,请重新输入!");
		}

	}

	// 取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(CfUser user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		List<CfResource> resources = new ArrayList<CfResource>();
		List<CfRole> roles = cfRoleDao.findByUserId(user.getUserId());

		for (CfRole role : roles) {
			List<CfResource> tempRes = cfResourceDao.getResourcesByRoleid(role
					.getRoleId());
			for (CfResource res : tempRes) {
				resources.add(res);
			}
		}
		for (CfResource res : resources) {
			authSet.add(new GrantedAuthorityImpl(res.getResId()));
		}
		return authSet;
	}

}
