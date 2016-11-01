package com.cf.cfsecurity.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.cf.cfsecurity.exception.MethodErrorException;
import com.cf.exception.ValidateCodeException;

/**
 * spring security登录失败处理
 * 
 * @author chl_seu
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		if (ex instanceof UsernameNotFoundException) {
			// 账号错误
			response.getWriter().print("{\"success\" : false, \"msg\" : \""+ex.getMessage()+"\"}");
		} else if (ex instanceof BadCredentialsException) {
			// 密码错误
			response.getWriter().print("{\"success\" : false, \"msg\" : \"用户/密码错误,请重新输入!\"}");
		} else if (ex instanceof ValidateCodeException) {
			// 验证码错误
			response.getWriter().print("{\"success\" : false, \"msg\" : \""+ex.getMessage()+"\"}");
		} else if (ex instanceof MethodErrorException) {
			// 请求方法错误
			response.getWriter().print("{\"success\" : false, \"msg\" : \""+ex.getMessage()+"\"}");
		} else if (ex instanceof SessionAuthenticationException) {
			// 登陆超时错误
			response.getWriter().print("{\"success\" : false, \"msg\" : \"重复登录或登录超时错误 !\"}");
		} else if (ex instanceof DisabledException){
			// 账户被冻结
			response.getWriter().print("{\"success\" : false, \"msg\" : \"账户被冻结 !\"}");
		}else {
			// 未知异常错误
			response.getWriter().print("{\"success\" : false, \"msg\" : \"未知异常错误,请联系相关技术人员!\"}");
		}
	}
}
