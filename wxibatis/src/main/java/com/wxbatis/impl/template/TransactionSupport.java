package com.wxbatis.impl.template;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionSupport {
	 private DefaultTransactionDefinition def = null;
	  private TransactionStatus status = null;
	  private DataSourceTransactionManager transactionManager = null;

	  public TransactionSupport(DataSourceTransactionManager transactionManager) throws Exception {
	    if (transactionManager == null) {
	      throw new Exception("Param [transactionManager] can not be null!");
	    }
	    this.transactionManager = transactionManager;
	    this.def = new DefaultTransactionDefinition();
	    this.def.setPropagationBehavior(0);
	    this.status = this.transactionManager.getTransaction(this.def);
	  }

	  public TransactionStatus getStatus() {
	    return this.status;
	  }

	  public DataSourceTransactionManager getTransactionManager() {
	    return this.transactionManager;
	  }

	  public void release() {
	    this.def = null;
	    this.status = null;
	    this.transactionManager = null;
	  }
}
