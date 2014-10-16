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
	
	public ReResponseObject<E> execute() { // throws ServerException
		pool.beginTransaction();
		boolean success = false;
		E objectFromDb = null;
		try {
			objectFromDb = executeWithThrows();
			success = true;
		} catch (multex.Exc ex) {
			ex.printStackTrace(); // TODO -> redirect System.err to log
			throw new ServerException(new ErrorMessage(ex.getMessage(), ex.getStackTrace()));
		} catch (Exception e) {
			// FIXME remove later, when all exceptions are converted to multex !!!
			System.err.println("== exception in ReResponseObject --> TODO ==");
			e.printStackTrace();
		} finally {
			pool.endTransaction(success); 
		}
		return new ReResponseObject<E>(objectFromDb);
	}
	
	public void forceTransactionEnd() {
		pool.endTransaction(true);
	}
	
	public void forceRestartTransaction() {
		pool.endTransaction(true);
		pool.beginTransaction();
	}
	
	public abstract E executeWithThrows() throws Exception; // TODO change to throws multex.Ex
}
