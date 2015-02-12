package de.bht.comanche.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * This class provide exception that may be thrown by a resource method if a specific HTTP error occur and 
 * response should be produced.
 * @author Maxim Novichkov
 *
 */
public class ReServerException extends WebApplicationException {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Create a new server exception with an server error message.
	 * @param message The message, which has to be reported.
	 */
	public ReServerException(final ReErrorMessage message) {
		super(Response.serverError().entity(message).type(MediaType.APPLICATION_JSON).build());
	}
}
