package de.bht.comanche.logic;

import de.bht.comanche.persistence.DaPool;
import de.bht.comanche.rest.ReErrorMessage;
import de.bht.comanche.rest.ReServerException;

public abstract class LgTransaction<E> {
	
	private final E result;
	
	public LgTransaction(LgSession session) throws Exception {
	   	boolean success = false;
		try {
			session.beginTransaction();
			result = execute();
			success = true;
		} catch (Exception ex) {
			multex.Msg.printReport(System.err, ex);
			throw new ReServerException(new ReErrorMessage(multex.Msg.getMessages(ex), multex.Msg.getStackTrace(ex)));
		} finally {
			try {
				session.endTransaction(success);
			} catch (Exception ex) {
				multex.Msg.printReport(System.err, ex);
			} 
		}
	}
		
	public E getResult() {
		return result;
	}

	public abstract E execute() throws Exception;
}
