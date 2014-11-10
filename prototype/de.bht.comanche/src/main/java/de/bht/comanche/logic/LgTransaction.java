package de.bht.comanche.logic;

import javax.servlet.http.HttpServletRequest;

import de.bht.comanche.rest.ReErrorMessage;
import de.bht.comanche.rest.ReServerException;
import de.bht.comanche.rest.RestService;

public abstract class LgTransaction<E> {
	
	private final E result;
	private final LgSession session = new LgSession();
    private HttpServletRequest request;	

	public LgTransaction(final HttpServletRequest request) {
		this.request = request;
	   	boolean success = false;
		try {
			session.beginTransaction();
			result = execute();
			success = true;
		} catch (Exception ex) {
			multex.Msg.printReport(System.err, ex);
			throw new ReServerException(new ReErrorMessage(ex, multex.Msg.getStackTrace(ex)));
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
	
	public LgSession getSession() {
		return session;
	}
	
	public LgUser startSession() {
		return session.startFor(RestService.getUserName(request));
	}
	
	//for tests only, delete later
	public LgUser startDummySession() {
		return session.startFor(RestService.getDummyUserName(request));
	}

	public abstract E execute() throws Exception;
}
