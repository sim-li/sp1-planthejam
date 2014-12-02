package de.bht.comanche.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ReServerException extends WebApplicationException {
	
	private static final long serialVersionUID = 1L;

	public ReServerException(final ReErrorMessage message) {
		super(Response.serverError().entity(message).type(MediaType.APPLICATION_JSON).build());
	}

	// TODO not used -> remove
//	public ReServerException(final int status, final ReErrorMessage message) {
//		super(Response.status(status).entity(message).type(MediaType.APPLICATION_JSON).build());
//	}
}
