package de.bht.comanche.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServerException extends WebApplicationException {
	
	private static final long serialVersionUID = 1L;


	public ServerException(ErrorMessage message) {
		super(Response.serverError().entity(message).type(MediaType.APPLICATION_JSON).build());
	}

	public ServerException(int status, ErrorMessage message) {
		super(Response.status(status).entity(message).type(MediaType.APPLICATION_JSON).build());
	}
	
}