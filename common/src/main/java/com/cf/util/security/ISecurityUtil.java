package com.cf.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

public interface ISecurityUtil {
	public final static String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

	/**
	 * 获取SECURITY3的CONTEXT
	 * 
	 * @return SecurityContext
	 */
	public SecurityContext getSecurityContext();

	/**
	 * 获取SECURITY3的认证
	 * 
	 * @return Authentication
	 */
	public Authentication getAuthentication();

	/**
	 * 获取SECURITY3的认证对象
	 * 
	 * @return UserDetails
	 */
	public UserDetails getUserDetails();
}
