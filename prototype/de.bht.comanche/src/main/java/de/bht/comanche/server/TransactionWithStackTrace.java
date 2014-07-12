package de.bht.comanche.server;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.persistence.Pool;

public abstract class TransactionWithStackTrace<E> {
	private final Pool<E> pool;
	
	public TransactionWithStackTrace (Pool<E> pool) {
		this.pool = pool;
	}
	
	public DbObject execute () {
		pool.beginTransaction();
		DbObject result = null;
		boolean success = false;
		try {
			result = executeWithThrows();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.endTransaction(success); 
		}
		return result;
	}
	
	public abstract DbObject executeWithThrows() throws Exception;
}

