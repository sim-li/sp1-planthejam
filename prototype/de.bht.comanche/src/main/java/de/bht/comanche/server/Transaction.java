package de.bht.comanche.server;

import de.bht.comanche.persistence.Pool;
import de.bht.comanche.persistence.PoolImpl;

public abstract class Transaction<E> {
	
	public TransactionObject execute () {
		Pool pool = PoolImpl.getInstance();
		TransactionObject result = null;
		boolean success = false;
		try {
			result = new TransactionObject();
			executeWithThrows();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.endTransaction(success);
		}
		return result;
	}
	
	public abstract E executeWithThrows() throws Exception;
}

