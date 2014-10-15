package de.bht.comanche.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ExceptionMapperManager implements ExceptionMapper<AdapterException> {
	
	@Override
	public Response toResponse(AdapterException exception) {
		return Response.status(exception.ptj.getResponseCode())
			.entity(new ErrorMessage(exception))	
			.type(MediaType.APPLICATION_JSON).
			build();
	}
}
