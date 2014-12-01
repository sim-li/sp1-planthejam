package de.bht.comanche.logic;

import javax.servlet.http.HttpServletRequest;

import de.bht.comanche.rest.ReErrorMessage;
import de.bht.comanche.rest.ReServerException;
import de.bht.comanche.rest.RestService;

public abstract class LgTransaction<E> {
	
	private final E result;
	private final LgSession session = new LgSession();
    private final HttpServletRequest request;	

	public LgTransaction(final HttpServletRequest request) {
		this.request = request;
	   	boolean success = false;
		try {
			this.session.beginTransaction();
			this.result = execute();
			success = true;
		} catch (final Exception ex) {
			multex.Msg.printReport(System.err, ex);
			throw new ReServerException(new ReErrorMessage(ex, multex.Msg.getStackTrace(ex)));
		} finally {
			try {
				this.session.endTransaction(success);
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
		return this.session.startFor(RestService.getUserName(this.request));
	}
	
	public abstract E execute() throws Exception;
}
