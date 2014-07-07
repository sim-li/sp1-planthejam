package de.bht.comanche.server;

import de.bht.comanche.persistence.Pool;
import de.bht.comanche.persistence.PoolImpl;

public abstract class Transaction <E> {
	public E execute () {
		Pool pool = PoolImpl.getInstance(); // = JpaDaFactory.getPool()? DaUser.getPool()?
		E result = null;
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
	
	public abstract E executeWithThrows() throws Exception;
}

