package de.bht.comanche.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import de.bht.comanche.server.CreateUser;

@Path("/service")
public class User { 
	
    @GET
    @Path("/single-user")
	@Produces(MediaType.APPLICATION_JSON)
	public CreateUser produceJSON() {
	
	CreateUser ur = new CreateUser("test@hascode.com", "Tim","Testerman", 1);
	 
	return ur;
		 
	}
}

