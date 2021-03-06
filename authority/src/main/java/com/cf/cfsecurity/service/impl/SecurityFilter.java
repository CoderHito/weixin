package com.cf.cfsecurity.service.impl;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.cf.cfsecurity.dao.impl.CfUserDao;
import com.cf.base.BaseSupport;
import com.cf.base.CustomUser;
import com.cf.util.security.Util;

@SuppressWarnings({ "rawtypes", "unchecked","unused" })
public class SecurityFilter extends AbstractSecurityInterceptor implements Filter {
	//与applicationContext-security.xml里的Filter的属性securityMetadataSource对应，
	//其他的两个组件，已经在AbstractSecurityInterceptor定义
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	private CfUserDao cfUserDao;

	public CfUserDao getCfUserDao() {
		return cfUserDao;
	}

	public void setCfUserDao(CfUserDao cfUserDao) {
		this.cfUserDao = cfUserDao;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}
	
	private void invoke(FilterInvocation fi) throws IOException, ServletException {
		// object为FilterInvocation对象
		//1.获取请求资源的权限
		//执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);
		//2.是否拥有权限
		//获取安全主体，可以强制转换为UserDetails的实例
		//1) UserDetails
		// Authentication authenticated = authenticateIfRequired();
		//this.accessDecisionManager.decide(authenticated, object, attributes);
		//用户拥有的权限
		//2) GrantedAuthority
		//Collection<GrantedAuthority> authenticated.getAuthorities()
		System.out.println("用户发送请求！ "+fi.getRequest().getMethod()+fi.getRequest().getRequestURI());
		HashMap vo = getUpdateVO(fi.getRequest());
		this.cfUserDao.OpAuditForUpdate(vo);
		
		InterceptorStatusToken token = null;
		
		token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<? extends Object> getSecureObjectClass() {
		//下面的MyAccessDecisionManager的supports方面必须返回true,否则会提醒类型错误
		return FilterInvocation.class;
	}
	
	private HashMap getUpdateVO(HttpServletRequest request) {
		HashMap vo = new HashMap();
		//java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMddhhmmss");
        //String s = format2.format(new Date());
		String s = Util.getCurrentDateTimeString();
        vo.put("CREATE_TIME",s);
        vo.put("IP",BaseSupport.CommonUtil.getClientIP(request));
        vo.put("REQ_URL",request.getRequestURI());
        vo.put("SESSION_ID", request.getSession().getId());
        vo.put("ID",java.util.UUID.randomUUID().toString().replaceAll("-", ""));
		try{
			//记录操作日志
			CustomUser user = (CustomUser) BaseSupport.SecurityUtil.getUserDetails();
			if(vo == null){
				vo.put("USER_ID","");
			}else{
				vo.put("USER_ID",user.getUsername());
			}
				
		}catch(Exception e){
			vo.put("USER_ID","");
			//logger.error(e.getMessage(),e);
		}
		return vo;
	}
}
