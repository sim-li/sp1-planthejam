package de.bht.comanche.logic;

import de.bht.comanche.exceptions.PtjGlobalException;
import de.bht.comanche.persistence.DaPool;
import de.bht.comanche.rest.ReResponseObject;

public abstract class LgTransaction<E> {
	private final DaPool<E> pool;
	
	public LgTransaction (DaPool<E> pool) {
		this.pool = pool;
	}
	
	public ReResponseObject<E> execute () {
		pool.beginTransaction();
		boolean success = false;
		E objectFromDb = null;
		int responseCode = ReResponseObject.STATUS_OK;
		try {
			objectFromDb = executeWithThrows();
			success = true;
		} catch (PtjGlobalException ptjE) {
			responseCode = ptjE.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();		// TODO: should set responseCode to error (?)
		} finally {
			pool.endTransaction(success); 
		}
		return new ReResponseObject<E>(objectFromDb, responseCode);
	}
	
	public void forceTransactionEnd() {
		pool.endTransaction(true);
	}
	
	public void forceRestartTransaction() {
		pool.endTransaction(true);
		pool.beginTransaction();
	}
	
	public abstract E executeWithThrows() throws Exception;
}
