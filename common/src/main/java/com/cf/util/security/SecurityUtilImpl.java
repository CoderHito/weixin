/**
 * 
 */
package com.cf.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cf.base.BaseSupport;
import com.cf.base.CustomUser;

/**
 * @author chl_seu
 *
 */
public class SecurityUtilImpl implements ISecurityUtil {

	public SecurityContext getSecurityContext() {
		return (SecurityContext) BaseSupport.ContextUtil.getSession()
				.getAttribute(SPRING_SECURITY_CONTEXT);
	}

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取SECURITY3的认证对象
	 * 
	 * @return UserDetails
	 */
	public CustomUser getUserDetails() {
		try{
			CustomUser u = (CustomUser) getAuthentication().getPrincipal();
			return u;
		} catch (Exception e){
			return null;
		}
		
	}

}
