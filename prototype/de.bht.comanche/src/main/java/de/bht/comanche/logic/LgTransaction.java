package de.bht.comanche.logic;

import javax.servlet.http.HttpServletRequest;

import de.bht.comanche.rest.ReErrorMessage;
import de.bht.comanche.rest.ReServerException;
import de.bht.comanche.rest.RestService;
/**
 * A transaction template class for exception handling. Exceptions ocurring
 * during JPA transactions are automatically handled with MulTEx.
 * 
 * @author Simon Lischka
 *
 * @param <E> Object type of element to be returned by transaction
 */
public abstract class LgTransaction<E> {
	/**
	 * Result of the JPA operation returned by operations in execute method.
	 */
	private final E result;
	
	/**
	 * The session for the transaction. A <code>JPA entity manager</code> is build and a 
	 * new <code>pool</code> instance is provided for the transaction.
	 */
	private final LgSession session;
	
	/**
	 * Servlet request, used to request user name.
	 */
    private final HttpServletRequest request;	
    
    /**
     * Executes transaction specified in <code>execute</code> method and
     * handles its exceptions.
     * 
     * @param request Servlet request containing user name for transaction
     */
	public LgTransaction(final HttpServletRequest request) {
		this.session = new LgSession();
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
	 * Returns result of JPA operation.
	 * 
	 * @return Result returned in execute method
	 */
	public E getResult() {
		return this.result;
	}
	
	/**
	 * Returns session that is initialized with this class, offers
	 * non account specific operations.
	 * 
	 * @return A initialized session instance for a specific user.
	 */
	public LgSession getSession() {
		return this.session;
	}
	
	/**
	 * Start session but returns user linked to it. The user contains
	 * all operations specific to an account.
	 * 
	 * @return Logged in user with its operations
	 */
	public LgUser startSession() {
		return this.session.startFor(RestService.getUserName(this.request));
	}
	
	/**
	 * Runs operations specified here and returns result of specified
	 * type.
	 * 
	 * @return Result of operation 
	 * @throws Exception
	 */
	public abstract E execute() throws Exception;
}
