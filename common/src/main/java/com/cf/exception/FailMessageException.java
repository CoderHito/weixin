package com.cf.exception;


/**
 * 失败消息通知
 * @author sven
 *
 */
public class FailMessageException extends RuntimeException {

	private static final long serialVersionUID = 6910995077888853926L;
	
	public FailMessageException(String msg) {
		super(msg);
	}

	public FailMessageException(String msg, Throwable t) {
		super(msg, t);
	}

}
