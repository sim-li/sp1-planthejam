package de.bht.comanche.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ReServerException extends WebApplicationException {
	
	private static final long serialVersionUID = 1L;


	public ReServerException(ReErrorMessage message) {
		super(Response.serverError().entity(message).type(MediaType.APPLICATION_JSON).build());
	}

	public ReServerException(int status, ReErrorMessage message) {
		super(Response.status(status).entity(message).type(MediaType.APPLICATION_JSON).build());
	}
	
}