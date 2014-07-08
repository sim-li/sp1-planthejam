package de.bht.comanche.server;

import de.bht.comanche.persistence.Pool;
import de.bht.comanche.persistence.PoolImpl;

public abstract class Transaction<E> {
	
	public ResponseObject execute () {
		Pool pool = PoolImpl.getInstance();
		ResponseObject result = null;
		boolean success = false;
		try {
			result = new ResponseObject();
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

