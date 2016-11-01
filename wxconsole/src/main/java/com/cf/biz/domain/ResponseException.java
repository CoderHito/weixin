package com.cf.biz.domain;

public class ResponseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7801202940650443823L;

	/**
	 * @param msg
	 */
	public ResponseException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public ResponseException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}
}
