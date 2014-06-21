package de.bht.comanche.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import de.bht.comanche.server.ServerTestUser;

@Path("/service")
public class DummyUserService { 
	
    @GET
    @Path("/single-user")
	@Produces(MediaType.APPLICATION_JSON)
	public ServerTestUser produceJSON() {
    	return new ServerTestUser("test@hascode.com", "Tim","Testerman", 1);
	}
}

