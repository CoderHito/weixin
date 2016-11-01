/**
 * 
 */
package com.wxbatis.itf.template;

import java.util.List;

import com.wxbatis.exception.MybatisDataAccessException;
import com.wxbatis.itf.handler.IResultHandler;
import com.wxbatis.itf.handler.IResultListHandler;

/**
 * @author chl_seu
 * 
 * 数据库操作方法接口
 * @see org.apache.ibatis.session.SqlSession方法
 * 
 * @version 1.0
 * @since 1.0
 */
public interface IMyBatisSessionTemplate {
	/**
	 * 不带参数，插入到数据库
	 * @param statement SQL ID
	 */
	public int insert(String statement) throws MybatisDataAccessException;
	
	/**
	 * 带参数，插入的数据库
	 * 
	 * @param statement SQL ID
	 * @param parameter 参数集，一般为Map类型
	 */
	public int insert(String statement, Object parameter) throws MybatisDataAccessException;
	
	/**
	 * 不带参数，从数据库删除
	 * @param statement SQL ID
	 */
	public int delete(String statement) throws MybatisDataAccessException;
	
	/**
	 * 带参数，从数据库删除
	 * @param statement SQL ID
	 * @param parameter 参数集，一般为Map类型
	 */
	public int delete(String statement, Object parameter) throws MybatisDataAccessException;
	
	/**
	 * 不带参数，更新数据库
	 * @param statement SQL ID
	 */
	public int update(String statement) throws MybatisDataAccessException;
	
	/**
	 * 带参数，更新数据库
	 * @param statement SQL ID
	 * @param parameter 参数集，一般为Map类型
	 */
	public int update(String statement, Object parameter) throws MybatisDataAccessException;
	
	/**
	 * 不带参数，查询唯一记录
	 * @param statement SQL ID
	 */
	public Object selectOne(String statement) throws MybatisDataAccessException;
	
	/**
	 * 带参数，查询唯一记录
	 * @param statement SQL ID
	 * @param parameter 参数集，一般为Map类型
	 */
	public Object selectOne(String statement, Object parameter) throws MybatisDataAccessException;
	
	/**
	 * 从数据库中查询唯一结果
	 * @param <T>
	 * @param statement SQL ID
	 * @param parameter 参数集，一般为Map类型
	 * @param resultHandler 结果处理类
	 * @return 
	 */
	public <T> T selectOne(String statement, Object parameter, IResultHandler<T> resultHandler) throws MybatisDataAccessException;
	
	/**
	 * 不带参数，查询列表数据
	 * @param <T>
	 * @param statement SQL ID
	 * @return
	 */
	public <T> List<T> selectList(String statement) throws MybatisDataAccessException;
	
	/**
	 * 带参数，查询列表数据
	 * @param <T>
	 * @param statement SQL ID
	 * @param parameter 参数集，一般为Map类型
	 * @return
	 */
	public <T> List<T> selectList(String statement, Object parameter) throws MybatisDataAccessException;
	
	/**
	 * 带参数，查询列表数据
	 * @param <T>
	 * @param statement SQL ID
	 * @param parameter 参数集，一般为Map类型
	 * @param resultListHandler 列表结果处理类
	 * @return
	 */
	public <T> List<T> selectList(String statement, Object parameter, IResultListHandler<T> resultListHandler) throws MybatisDataAccessException;
	
	
}
