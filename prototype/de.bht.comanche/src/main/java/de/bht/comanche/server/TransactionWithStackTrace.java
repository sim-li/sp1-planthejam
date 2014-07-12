package de.bht.comanche.server;

import de.bht.comanche.persistence.Pool;

public abstract class TransactionWithStackTrace<E> {
	private final Pool<E> pool;
	private final boolean throwStackTrace;
	
	public TransactionWithStackTrace (Pool<E> pool, boolean throwStackTrace) {
		this.pool = pool;
		this.throwStackTrace = throwStackTrace;
	}
	
	public TransactionWithStackTrace (Pool<E> pool) {
		this.pool = pool;
		this.throwStackTrace = true;
	}
	
	public void execute () {
		pool.beginTransaction();
		boolean success = false;
		try {
			executeWithThrows();
			success = true;
		} catch (Exception e) {
			if (throwStackTrace) {
				e.printStackTrace();
			}
		} finally {
			pool.endTransaction(success); 
		}
	}
	
	public void forceNewTransaction() {
		pool.endTransaction(true);
		pool.beginTransaction();
	}
	
	public abstract void executeWithThrows() throws Exception;
}

