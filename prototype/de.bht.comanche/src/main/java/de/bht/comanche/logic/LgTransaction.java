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
			multex.Msg.printStackTrace(ex); // TODO -> redirect System.err to log
			
//			multex.Msg.printMessages(io_destination, i_throwable);
//			multex.Msg.printStackTrace(io_destination, i_throwable);
//			multex.Msg.printReport(io_destination, i_throwable);
			
//			multex.Msg.getMessages(ex);
//			multex.Msg.getStackTrace(ex);
//			multex.Msg.getReport(ex);
			
			throw new ServerException(new ErrorMessage(multex.Msg.getMessages(ex), multex.Msg.getStackTrace(ex)));
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
