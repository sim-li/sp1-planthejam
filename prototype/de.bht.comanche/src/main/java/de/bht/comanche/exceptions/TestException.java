package de.bht.comanche.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TestException extends WebApplicationException {
	
	private static final long serialVersionUID = 1L;


	public TestException(ErrorMessage message) {
		super(Response.serverError().entity(message).type(MediaType.APPLICATION_JSON).build());
	}

	public TestException(int status, ErrorMessage message) {
		super(Response.status(status).entity(message).type(MediaType.APPLICATION_JSON).build());
	}
	
}