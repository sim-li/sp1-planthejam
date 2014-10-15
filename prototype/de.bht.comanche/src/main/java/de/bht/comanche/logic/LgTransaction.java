package de.bht.comanche.logic;

import de.bht.comanche.exceptions.ErrorMessage;
import de.bht.comanche.exceptions.ServerException;
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
		} catch (multex.Exc ex) {
			throw new ServerException(new ErrorMessage("message", "stack trace"));
//			throw new WebApplicationException(ex.getMessage()); // TODO use "TestException" instead !!!
		} catch (Exception e) {
			
			// FIXME remove later, when all exceptions are converted to multex !!!
			
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
