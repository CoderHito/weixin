/**
 * 
 */
package com.cf.cfsecurity.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;

import com.cf.cfsecurity.dao.impl.CfResourceDao;
import com.cf.cfsecurity.dao.model.CfResource;
import com.cf.cfsecurity.service.IFilterInvocationSecurityMetadataSource;

/**
 * @author chl_seu 1 加载资源与权限的对应关系
 */
public class FilterInvocationSecurityMetadataSourceImpl implements
		IFilterInvocationSecurityMetadataSource {

	private CfResourceDao cfResourceDao;
	public CfResourceDao getCfResourceDao() {
		return cfResourceDao;
	}

	public void setCfResourceDao(CfResourceDao cfResourceDao) {
		this.cfResourceDao = cfResourceDao;
	}

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	/**
	 * 
	 */
	public FilterInvocationSecurityMetadataSourceImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.access.SecurityMetadataSource#
	 * getAllConfigAttributes()
	 */
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.SecurityMetadataSource#getAttributes
	 * (java.lang.Object)
	 * 返回所请求资源所需要的权限
	 */
	public Collection<ConfigAttribute> getAttributes(Object arg0)
			throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) arg0).getRequestUrl();
		if (resourceMap == null) {
			loadResourceDefine();
		}
		return resourceMap.get(requestUrl);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.SecurityMetadataSource#supports(java
	 * .lang.Class)
	 */
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cf.core.security.service.IFilterInvocationSecurityMetadataSource#init()
	 */
	public void init() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cf.core.security.service.IFilterInvocationSecurityMetadataSource#load()
	 */
	public void load() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cf.core.security.service.IFilterInvocationSecurityMetadataSource#expireNow
	 * ()
	 */
	public void expireNow() {
		// TODO Auto-generated method stub

	}

	// 加载所有资源与权限的关系
	@SuppressWarnings("unused")
	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<CfResource> resources = this.cfResourceDao.findAll();
			for (CfResource resource : resources) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				// 以权限名封装为Spring的security Object
				ConfigAttribute configAttribute = new SecurityConfig(
						resource.getResId());
				configAttributes.add(configAttribute);
				resourceMap.put(resource.getUrl(), configAttributes);
			}
		}

		Set<Entry<String, Collection<ConfigAttribute>>> resourceSet = resourceMap
				.entrySet();
		Iterator<Entry<String, Collection<ConfigAttribute>>> iterator = resourceSet
				.iterator();

	}

	

}
