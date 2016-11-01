/**
 * 
 */
package com.wxbatis.exception;

import org.springframework.dao.DataAccessException;

/**
 * @author chl_seu
 *
 */
public class MybatisDataAccessException extends DataAccessException {

	/**
	 * @param msg
	 */
	public MybatisDataAccessException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public MybatisDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

}
