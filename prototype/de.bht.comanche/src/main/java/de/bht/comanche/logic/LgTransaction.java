package de.bht.comanche.logic;

import de.bht.comanche.persistence.DaPool;
import de.bht.comanche.rest.ReErrorMessage;
import de.bht.comanche.rest.ReServerException;

public abstract class LgTransaction<E> {
	
	private final E result;
	
	public E execute() { // throws ServerException
		boolean success = false;
		
		try {
			result = executeWithThrows();
			success = true;
		} catch (Exception ex) {
			multex.Msg.printReport(System.err, ex);
			throw new ReServerException(new ReErrorMessage(multex.Msg.getMessages(ex), multex.Msg.getStackTrace(ex)));
		} finally {
			try {
				pool.endTransaction(success);
			} catch (Exception ex) {
				multex.Msg.printReport(System.err, ex);
			} 
		}
		return result;
	}
	
	public abstract E executeWithThrows() throws Exception;
}
