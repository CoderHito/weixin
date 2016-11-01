/**
 * 
 */
package com.wxbatis.impl.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

import com.wxbatis.exception.MybatisDataAccessException;
import com.wxbatis.impl.batch.Batchmate;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.datesource.DBContextHolder;
import com.wxbatis.impl.handler.ResultPageHandler;
import com.wxbatis.itf.handler.IResultHandler;
import com.wxbatis.itf.handler.IResultListHandler;
import com.wxbatis.itf.handler.IResultPageHandler;
import com.wxbatis.itf.template.IMyBatisSessionTemplate;
import com.wxbatis.utils.Constants;

/**
 * 数据库操作类，支持事务操作
 * 
 * @author chl_seu
 * 
 */
public class MyBatisSessionTemplate implements IMyBatisSessionTemplate {

	private static final Logger logger = Logger.getLogger(MyBatisSessionTemplate.class);

	private DIALECT dialect = null;
	private SqlSessionTemplate sqlSessionTemplate;
	private DataSourceTransactionManager transactionManager;
	private Exception caughtExceptoin = null;

	public Exception getCaughtExceptoin() {
		return caughtExceptoin;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final ThreadLocal<TransactionSupport> transactionHolder = new ThreadLocal();

	public static enum DIALECT {
		ORACLE("oracle"), DB2("db2"), MSSQL("mssql"), MYSQL("mysql");

		private String code;

		private DIALECT(String code) {
			this.code = code;
		}

		public String getCode() {
			return this.code;
		}

		public static DIALECT parse(String code) {
			if (code == null || code.length() <= 0) {
				return ORACLE;
			}
			for (DIALECT dialect : DIALECT.values()) {
				if (dialect.getCode().equalsIgnoreCase(code)) {
					return dialect;
				}
			}
			return ORACLE;
		}
	}

	public String getDialect() {
		return dialect.getCode();
	}

	public void setDialect(String dialect) {
		if (dialect != null) {
			this.dialect = DIALECT.parse(dialect);
		} else {
			this.dialect = DIALECT.parse("");
		}
	}

	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * 
	 */
	public MyBatisSessionTemplate() {
		super();
	}

	public int insert(String statement) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute Insert SQL [").append(statement).append("].").toString());
		// 设置使用哪个个数据源
		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		try {
			return sqlSessionTemplate.insert(statement);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			ExceptionMonitor(e);
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public int insert(String statement, Object parameter) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute Insert SQL [").append(statement).append("] with param [")
				.append(parameter == null ? null : parameter.toString()).append("].").toString());
		// 设置使用哪个个数据源
		try {
			if (((Map<String, String>) parameter).containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(((Map<String, String>) parameter).get(Constants.DB_TYPE_KEY));
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		}
		try {
			return sqlSessionTemplate.insert(statement, parameter);
		} catch (Exception e) {
			ExceptionMonitor(e);
			logger.debug(e.getMessage());
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public int delete(String statement) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute Delete SQL [").append(statement).append("].").toString());
		// 设置使用哪个个数据源
		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		try {
			return sqlSessionTemplate.delete(statement);
		} catch (Exception e) {
			ExceptionMonitor(e);
			logger.debug(e.getMessage());
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public int delete(String statement, Object parameter) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute Delete SQL [").append(statement).append("] with param [")
				.append(parameter == null ? null : parameter.toString()).append("].").toString());
		// 设置使用哪个个数据源
		try {
			if (((Map<String, String>) parameter).containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(((Map<String, String>) parameter).get(Constants.DB_TYPE_KEY));
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		}
		try {
			return sqlSessionTemplate.delete(statement, parameter);
		} catch (Exception e) {
			ExceptionMonitor(e);
			logger.debug(e.getMessage());
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public int update(String statement) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute Update SQL [").append(statement).append("].").toString());
		// 设置使用哪个个数据源
		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		try {
			return sqlSessionTemplate.update(statement);
		} catch (Exception e) {
			ExceptionMonitor(e);
			logger.debug(e.getMessage());
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public int update(String statement, Object parameter) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute Update SQL [").append(statement).append("] with param [")
				.append(parameter == null ? null : parameter.toString()).append("].").toString());
		// 设置使用哪个个数据源
		try {
			if (((Map<String, String>) parameter).containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(((Map<String, String>) parameter).get(Constants.DB_TYPE_KEY));
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		}
		try {
			return sqlSessionTemplate.update(statement, parameter);
		} catch (Exception e) {
			ExceptionMonitor(e);
			logger.debug(e.getMessage());
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public Object selectOne(String statement) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute SelectOne SQL [").append(statement).append("].").toString());
		// 设置使用哪个个数据源
		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		try {
			return sqlSessionTemplate.selectOne(statement);
		} catch (Exception e) {
			ExceptionMonitor(e);
			logger.debug(e.getMessage());
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public Object selectOne(String statement, Object parameter) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute SelectOne SQL [").append(statement).append("] with param [")
				.append(parameter == null ? null : parameter.toString()).append("].").toString());
		// 设置使用哪个个数据源
		try {
			if (((Map<String, String>) parameter).containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(((Map<String, String>) parameter).get(Constants.DB_TYPE_KEY));
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		}
		try {
			return sqlSessionTemplate.selectOne(statement, parameter);
		} catch (Exception e) {
			ExceptionMonitor(e);
			logger.debug(e.getMessage());
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return this.sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public void startTransaction(DefaultTransactionDefinition def, TransactionStatus status) {
		status = transactionManager.getTransaction(def);
	}

	public TransactionStatus startTransaction(DefaultTransactionDefinition def) {
		return transactionManager.getTransaction(def);
	}

	public void rollbackTransaction(TransactionStatus status) {
		transactionManager.rollback(status);
	}

	public void commitTransaction(TransactionStatus status) {
		transactionManager.commit(status);
	}

	/**
	 * 批量执行sql<br>
	 * 
	 */
	public int batch(final Batchmate[] batchmates) throws MybatisDataAccessException {
		Assert.notNull(batchmates, "Param [batchmates] can not be null!");
		logger.debug(new StringBuilder().append("Execute Batch SQL with ").append(batchmates.length)
				.append("batchmates.").toString());
		DefaultTransactionDefinition def = new
		DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		this.startTransaction(def, status);
		try {
			final Object[] results = new Object[batchmates.length];

			try {
				Batchmate batchmate = null;
				for (int i = 0; i < batchmates.length; i++) {
					batchmate = batchmates[i];
					logger.trace(new StringBuilder("Execute No.").append(i).append(" batchmate: ").append(batchmate)
							.toString());
					results[i] = internalProcessBatchmate(batchmate);
				}
				transactionManager.commit(status);
				return 1;
			} catch (Exception e) {
				logger.error("Error in batch commit!", e);
				logger.error(e.getMessage(), e);
				e.printStackTrace();
				try {
					transactionManager.rollback(status);
					return -1;
				 } catch (Exception ex) {
					 logger.error("Error in batch rollback!", ex);
				 }
				throw new MybatisDataAccessException(e.getMessage(), e);
			}
		} catch (Exception e) {
			ExceptionMonitor(e);
			e.printStackTrace();
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}
	
	/**
	 * 批量执行sql<br>
	 * 
	 */
	public int batch2(final Batchmate[] batchmates) throws MybatisDataAccessException {
		Assert.notNull(batchmates, "Param [batchmates] can not be null!");
		logger.debug(new StringBuilder().append("Execute Batch SQL with ").append(batchmates.length)
				.append("batchmates.").toString());
		DefaultTransactionDefinition def = new
		DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		this.startTransaction(def, status);
		try {
			final Object[] results = new Object[batchmates.length];

			try {
				Batchmate batchmate = null;
				for (int i = 0; i < batchmates.length; i++) {
					batchmate = batchmates[i];
					logger.trace(new StringBuilder("Execute No.").append(i).append(" batchmate: ").append(batchmate)
							.toString());
					results[i] = internalProcessBatchmate(batchmate);
				}
				transactionManager.commit(status);
				return 1;
			} catch (Exception e) {
				logger.error("Error in batch commit!", e);
				logger.error(e.getMessage(), e);
				e.printStackTrace();
				try {
					transactionManager.rollback(status);
					throw new MybatisDataAccessException(e.getMessage(), e);
					//return -1;
				 } catch (Exception ex) {
					 logger.error("Error in batch rollback!", ex);
					 throw new MybatisDataAccessException(ex.getMessage(), ex);
				 }
				//throw new MybatisDataAccessException(e.getMessage(), e);
			}
		} catch (Exception e) {
			ExceptionMonitor(e);
			e.printStackTrace();
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public Object batch1(final Batchmate[] batchmates) throws MybatisDataAccessException {
		Assert.notNull(batchmates, "Param [batchmates] can not be null!");
		logger.debug(new StringBuilder().append("Execute Batch SQL with ").append(batchmates.length)
				.append("batchmates.").toString());
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		this.startTransaction(def, status);
		try {
			final Object[] results = new Object[batchmates.length];

			try {
				Batchmate batchmate = null;
				for (int i = 0; i < batchmates.length; i++) {
					batchmate = batchmates[i];
					logger.trace(new StringBuilder("Execute No.").append(i).append(" batchmate: ").append(batchmate)
							.toString());
					results[i] = internalProcessBatchmate(batchmate);
				}
				transactionManager.commit(status);
				return null;
			} catch (Exception e) {
				logger.error("Error in batch commit!", e);
				try {
					transactionManager.rollback(status);
				} catch (Exception ex) {
					logger.error("Error in batch rollback!", ex);
				}
				throw new MybatisDataAccessException(e.getMessage(), e);
			}
		} catch (Exception e) {
			ExceptionMonitor(e);
			e.printStackTrace();
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	private Object internalProcessBatchmate(Batchmate batchmate) {
		// 设置使用哪个个数据源
		try {
			if (((Map<String, String>) batchmate.getParameter()).containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(((Map<String, String>) batchmate.getParameter()).get(Constants.DB_TYPE_KEY));
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_RW);
		}
		switch (batchmate.getOptType()) {
		case INSERT: {
			return this.sqlSessionTemplate.insert(batchmate.getStatement(), batchmate.getParameter());
		}
		case UPDATE: {
			return this.sqlSessionTemplate.update(batchmate.getStatement(), batchmate.getParameter());
		}
		case DELETE: {
			return this.sqlSessionTemplate.delete(batchmate.getStatement(), batchmate.getParameter());
		}
			/*
			 * case SELECT_ONE: { return
			 * this.sqlSessionTemplate.getSqlSessionTemplate
			 * ().selectOne(batchmate.getStatement(), batchmate.getParameter(),
			 * batchmate.getResultHandler()); } case SELECT_LIST: { return
			 * this.sqlSessionTemplate
			 * .getSqlSessionTemplate().selectList(batchmate.getStatement(),
			 * batchmate.getParameter(), batchmate.getResultListHandler()); }
			 */
		case SELECT_PAGE: {
			/*
			 * return this.SelectPage(sqlSession, batchmate.getStatement(),
			 * batchmate.getPageIndex(), batchmate.getPageSize(),
			 * batchmate.getParameter(), batchmate.getResultPageHandler());
			 */
		}
		default: {
			IllegalArgumentException iae = new IllegalArgumentException(new StringBuilder()
					.append("Unexpect Operate Type: [").append(batchmate.getOptType()).append("]").toString());
			logger.error(iae.getMessage(), iae);
			throw iae;
		}
		}
	}

	public <T> Page<T> selectPage(final String statement, final Object parameter) throws MybatisDataAccessException {
		int pageIndex = Integer.valueOf(((HashMap) parameter).get("page").toString());
		int pageSize = Integer.valueOf(((HashMap) parameter).get("limit").toString());
		logger.debug(new StringBuilder().append("Execute SelectPage SQL [").append(statement).append("] with param [")
				.append(parameter == null ? null : parameter.toString()).append("], pageIndex [").append(pageIndex)
				.append("], pageSize [").append(pageSize).append("].").toString());
		// 设置使用哪个个数据源
		try {
			if (((Map<String, String>) parameter).containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(((Map<String, String>) parameter).get(Constants.DB_TYPE_KEY));
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		}
		try {
			return selectPage(statement, parameter, pageIndex, pageSize, new ResultPageHandler<T>(pageIndex, pageSize));
		} catch (Exception e) {
			ExceptionMonitor(e);
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public <T> Page<T> selectPage(String statement, Object parameter, int pageIndex, int pageSize)
			throws MybatisDataAccessException {
		logger.debug((new StringBuilder()).append("Execute SelectPage SQL [").append(statement).append("] with param [")
				.append(parameter != null ? parameter.toString() : null).append("], pageIndex [").append(pageIndex)
				.append("], pageSize [").append(pageSize).append("].").toString());
		// 设置使用哪个个数据源
		try {
			if (((Map<String, String>) parameter).containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(((Map<String, String>) parameter).get(Constants.DB_TYPE_KEY));
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		}
		try {
			return selectPage(statement, parameter, pageIndex, pageSize,
					((IResultPageHandler) (new ResultPageHandler(pageIndex, pageSize))));
		} catch (Exception e) {
			ExceptionMonitor(e);
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public <T> Page<T> selectPage(final String statement, final Object parameter, final int pageIndex,
			final int pageSize, final IResultPageHandler<T> resultPageHandler) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute SelectPage SQL [").append(statement).append("] with param [")
				.append(parameter == null ? null : parameter.toString()).append("], pageIndex [").append(pageIndex)
				.append("], pageSize [").append(pageSize).append("] and resultPageHandler.").toString());
		// 设置使用哪个个数据源
		try {
			if (((Map<String, String>) parameter).containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(((Map<String, String>) parameter).get(Constants.DB_TYPE_KEY));
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		}
		try {
			if (resultPageHandler == null) {
				IllegalArgumentException iae = new IllegalArgumentException("ResultPageHandler can not be null!");
				logger.error(iae.getMessage(), iae);
				throw iae;
			}
			// 执行TotalCount获取总数据
			final String totalCountStatment = new StringBuilder().append(statement).append("_totalCount").toString();
			logger.debug(new StringBuilder().append("Prepare to fetch total count for page result. Request [")
					.append(totalCountStatment).append("] to execute selectOne.").toString());
			final Integer totalCount = (Integer) (this.selectOne(totalCountStatment, parameter));
			int executePageSize = (pageSize == 0 ? Integer.MAX_VALUE : pageSize);
			int executeStartOfPage = (pageSize == 0 ? 0 : Page.getStartOfPage(pageIndex, pageSize));
			pretreatPagingParam(parameter, executeStartOfPage, executePageSize, totalCount);
			sqlSessionTemplate.select(statement, parameter, resultPageHandler);
			Page<T> pageResult = resultPageHandler.getResult();
			pageResult.setTotalCount(totalCount.intValue());
			/**
			 * Page<T> results = sqlSessionTemplate.selectList(statement,
			 * parameter, rowBounds)
			 * 
			 * .execute(new SqlSessionCallback<Page<T>>() { public Page
			 * <T> doInSqlSession(SqlSession sqlSession) {
			 * 
			 * pretreatPagingParam(parameter, executeStartOfPage,
			 * executePageSize,totalCount);
			 * 
			 * Page<T> pageResult = resultPageHandler.getResult();
			 * pageResult.setTotalCount(totalCount.intValue()); return
			 * pageResult; } });
			 */
			return pageResult;
		} catch (Exception e) {
			ExceptionMonitor(e);
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	private void pretreatPagingParam(final Object parameter, int executeStartOfPage, int executePageSize, int total) {
		if (parameter instanceof Map) {
			String pageBegin;
			String pageEnd;
			switch (this.dialect) {
			case ORACLE: {
				pageBegin = "select * from (select rownum R, P.* from (";
				pageEnd = new StringBuffer().append(") P) where R > ").append(executeStartOfPage).append(" and R <= ")
						.append(executeStartOfPage + executePageSize).toString();
				break;
			}
			case DB2: {
				pageBegin = "select *  from (select ROW_NUMBER() over() as R, P.* from (";
				pageEnd = new StringBuffer().append(") P) where R > ").append(executeStartOfPage).append(" and R <= ")
						.append(executeStartOfPage + executePageSize).toString();
				break;
			}
			case MYSQL: {
				pageBegin = "";
				pageEnd = new StringBuffer().append(" limit ").append(executeStartOfPage).append(",")
						.append(executePageSize).toString();
				break;
			}
			case MSSQL: {
				String orderBy = (String) ((Map) parameter).get("ORDERBY");
				if (orderBy == null || "".equals(orderBy.trim())) {
					orderBy = "rand()";
				}
				pageBegin = "select *  from (select ROW_NUMBER() over(ORDER BY " + orderBy + ") as R, P.* from (";
				pageEnd = new StringBuffer().append(") P) P2 where R > ").append(executeStartOfPage)
						.append(" and R <= ").append(executeStartOfPage + executePageSize).toString();
				// int total = executeStartOfPage+ executePageSize;
				((Map) parameter).put("U", " U " + total + " ");
				break;
			}
			default: {
				pageBegin = "";
				pageEnd = "";
				break;
			}
			}
			logger.trace(new StringBuilder().append("PretreatPagingParam -> pageBegin[").append(pageBegin)
					.append("], pageEnd[").append(pageEnd).append("].").toString());
			((Map) parameter).put("pageBegin", pageBegin);
			((Map) parameter).put("pageEnd", pageEnd);
		}
	}

	public Map<String, Object> execProcedure(final String statement, final Map<String, Object> parameter)
			throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute Procedure [").append(statement).append("] with inParameter: ")
				.append(parameter).append(".").toString());
		// 设置使用哪个个数据源
		try {
			if (parameter.containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(parameter.get(Constants.DB_TYPE_KEY).toString());
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		}
		try {
			sqlSessionTemplate.selectOne(statement, parameter);
			return parameter;
		} catch (Exception e) {
			ExceptionMonitor(e);
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	/**
	 * 启动事物
	 * 
	 * @throws Exception
	 */
	public void startTransaction() throws Exception {
		try {
			TransactionSupport support = (TransactionSupport) this.transactionHolder.get();
			if (support != null) {
				throw new Exception("Last transaction is not commited! Please commit last transaction first.");
			}
			this.transactionHolder.set(new TransactionSupport(this.transactionManager));
		} catch (Exception e) {
			throw new Exception("start Transaction fial,reason:" + e.getMessage());
		}
	}

	/**
	 * 事物回滚
	 */
	public void rollbackTransaction() {
		TransactionSupport transactionSupport = (TransactionSupport) this.transactionHolder.get();
		transactionSupport.getTransactionManager().rollback(transactionSupport.getStatus());
		transactionSupport.release();
		this.transactionHolder.set(null);
	}

	/**
	 * 事物提交
	 */
	public void commitTransaction() {
		TransactionSupport transactionSupport = (TransactionSupport) this.transactionHolder.get();
		if (transactionSupport != null)
			try {
				transactionSupport.getTransactionManager().commit(transactionSupport.getStatus());
				transactionSupport.release();
				this.transactionHolder.set(null);
			} catch (Exception e) {
				logger.error("Commit Transaction failed. " + e.getMessage(), e);
				transactionSupport.getTransactionManager().rollback(transactionSupport.getStatus());
			}
		else
			logger.warn("Perperty [transactionSupport] is null, Commit failed.");
	}

	/**
	 * 
	 * @param e
	 */
	protected void ExceptionMonitor(Exception e) {
		this.caughtExceptoin = e;
		if (this.transactionHolder.get() != null)
			rollbackTransaction();
	}

	public <T> List<T> selectList(String statement) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute SelectList SQL [").append(statement).append("].").toString());
		// 设置使用哪个个数据源
		DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		try {
			return sqlSessionTemplate.selectList(statement);
		} catch (Exception e) {
			ExceptionMonitor(e);
			logger.debug(e.getMessage());
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public <T> List<T> selectList(String statement, Object parameter) throws MybatisDataAccessException {
		logger.debug(new StringBuilder().append("Execute SelectList SQL [").append(statement).append("] with param [")
				.append(parameter == null ? null : parameter.toString()).append("].").toString());
		// 设置使用哪个个数据源
		try {
			if (((Map<String, String>) parameter).containsKey(Constants.DB_TYPE_KEY)) {
				DBContextHolder.setDbType(((Map<String, String>) parameter).get(Constants.DB_TYPE_KEY));
			} else {
				DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
			}
		} catch (Exception e) {
			DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
		}
		try {
			return sqlSessionTemplate.selectList(statement, parameter);
		} catch (Exception e) {
			ExceptionMonitor(e);
			logger.debug(e.getMessage());
			throw new MybatisDataAccessException(e.getMessage(), e);
		}
	}

	public <T> T selectOne(String statement, Object parameter, IResultHandler<T> resultHandler)
			throws MybatisDataAccessException {
		return null;
	}

	public <T> List<T> selectList(String statement, Object parameter, IResultListHandler<T> resultListHandler)
			throws MybatisDataAccessException {
		return null;
	}
}
