/**
 * 
 */
package com.cf.cfsecurity.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cf.util.security.DESCrypto;

/**
 * 重载SECURITY3的UsernamePasswordAuthenticationFilter的attemptAuthentication,
 * obtainUsername,obtainPassword方法(完善逻辑) 增加验证码校验模块 添加验证码属性 添加验证码功能开关属性
 * 
 * @author chl_seu
 *
 */
public class UsernamePasswordAuthenticationFilterImpl extends
		UsernamePasswordAuthenticationFilter {


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		// 只接受POST方式传递的数据
//		if (!"POST".equals(request.getMethod()))
//			throw new MethodErrorException("不支持非POST方式的请求!");


		// 获取Username和Password
		String username = obtainUsername(request);
		//String tmp = request.getParameter("password");
		String password = obtainPassword(request);
		//String password = new String(ThreeDes.decryptMode(username.getBytes(), tmp.getBytes()));
		// UsernamePasswordAuthenticationToken实现Authentication校验
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// 允许子类设置详细属性
		setDetails(request, authRequest);
		
		// Place the last username attempted into HttpSession for views  
	    HttpSession session = request.getSession(false);  
	    //如果session不为空，添加username到session中  
	    if (session != null || getAllowSessionCreation()) {  
	        request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, username);  
	    }  

		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}


	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(getUsernameParameter());
		return null == obj ? "" : obj.toString().trim();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(getPasswordParameter());
		return null == obj ? "" : new String(DESCrypto.JS3DESEncryption((
				request.getParameter(getUsernameParameter())), obj.toString().trim()));
	}

}
