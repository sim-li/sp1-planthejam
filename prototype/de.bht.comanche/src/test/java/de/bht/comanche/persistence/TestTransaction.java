package de.bht.comanche.persistence;


import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.rest.ReErrorMessage;
import de.bht.comanche.rest.ReServerException;
/**
 * Transaction template class for exception handling.
 * ( For testing, works without HttpServletRequests )
 * 
 * @author Simon Lischka
 *
 * @param <E> Return type
 */
public abstract class TestTransaction<E> {
	/**
	 * Result of operation
	 */
	private final E result;
	
	/**
	 * Session
	 */
	private final LgSession session = new LgSession();
	
	/**
	 * Servelet Request
	 */
    private final String username;

    
    /**
     * Sandwich operations
     * 
     * @param request
     */
	public TestTransaction(final String username) {
	   	boolean success = false;
	   	this.username = username;
		try {
			this.session.getApplication().beginTransaction();
			this.result = execute();
			success = true;
		} catch (final Exception ex) {
			multex.Msg.printReport(System.err, ex);
			throw new ReServerException(new ReErrorMessage(ex, multex.Msg.getStackTrace(ex)));
		} finally {
			try {
				this.session.getApplication().endTransaction(success);
			} catch (final Exception ex) {
				multex.Msg.printReport(System.err, ex);
			} 
		}
	}
		
	public E getResult() {
		return this.result;
	}
	
	public LgSession getSession() {
		return this.session;
	}
	
	public LgUser startSession() {
		return this.session.startFor(this.username);
	}
	
	public abstract E execute();
}