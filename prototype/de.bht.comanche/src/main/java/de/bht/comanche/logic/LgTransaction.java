package de.bht.comanche.logic;

import javax.servlet.http.HttpServletRequest;

import de.bht.comanche.rest.ReErrorMessage;
import de.bht.comanche.rest.ReServerException;
import de.bht.comanche.rest.RestService;
/**
 * Transaction template class for exception handling. 
 * 
 * @author Simon Lischka
 *
 * @param <E> Return type
 */
public abstract class LgTransaction<E> {
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
    private final HttpServletRequest request;	

    
    /**
     * Sandwich operations
     * 
     * @param request
     */
	public LgTransaction(final HttpServletRequest request) {
		this.request = request;
	   	boolean success = false;
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
		
	/**
	 * Return result 
	 * 
	 * @return
	 */
	public E getResult() {
		return this.result;
	}
	
	public LgSession getSession() {
		return this.session;
	}
	
	public LgUser startSession() {
		return this.session.startFor(RestService.getUserName(this.request));
	}
	
	public abstract E execute() throws Exception;
}
