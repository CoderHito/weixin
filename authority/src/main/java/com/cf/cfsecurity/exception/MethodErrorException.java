package com.cf.cfsecurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 请求方法错误异常
 * 
 * @author shadow
 * @email 124010356@qq.com
 * @create 2012.04.28
 */
@SuppressWarnings("serial")
public class MethodErrorException extends AuthenticationException {

	public MethodErrorException(String msg) {
		super(msg);
	}

	public MethodErrorException(String msg, Throwable t) {
		super(msg, t);
	}

}
