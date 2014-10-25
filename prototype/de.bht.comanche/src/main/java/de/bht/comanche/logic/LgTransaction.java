package de.bht.comanche.logic;

import de.bht.comanche.exceptions.ErrorMessage;
import de.bht.comanche.exceptions.ServerException;
import de.bht.comanche.persistence.DaPool;

public abstract class LgTransaction<E> {
	private final DaPool<E> pool;
	
	public LgTransaction (DaPool<E> pool) {
		this.pool = pool;
	}
	
	public E execute() { // throws ServerException
		pool.beginTransaction();
		boolean success = false;
		E objectFromDb = null;
		try {
			objectFromDb = executeWithThrows();
			success = true;
		} catch (Exception ex) {
			multex.Msg.printReport(System.err, ex);
			throw new ServerException(new ErrorMessage(multex.Msg.getMessages(ex), multex.Msg.getStackTrace(ex)));
		} finally {
			try {
				pool.endTransaction(success);
			} catch (Exception ex) {
				multex.Msg.printReport(System.err, ex);
			} 
		}
		return objectFromDb;
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
