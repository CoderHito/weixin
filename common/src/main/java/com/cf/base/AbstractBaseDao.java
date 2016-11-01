/**
 * 
 */
package com.cf.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * @author chl_seu
 *
 * dao实现的基类 
 */
@Repository
public abstract class AbstractBaseDao {

	// SPRING JDBC模板接口
		@Autowired
		private MyBatisSessionTemplate jdbcTemplate;

		public MyBatisSessionTemplate getJdbcTemplate() {
			return jdbcTemplate;
		}

		public void setJdbcTemplate(MyBatisSessionTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}
		
		

}
