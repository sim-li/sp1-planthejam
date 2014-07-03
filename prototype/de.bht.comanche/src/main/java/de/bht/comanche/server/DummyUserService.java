package de.bht.comanche.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import de.bht.comanche.server.ServerTestUser;

@Path("/service")
public class DummyUserService { 
	//Begin/End Transaction
    @GET
    @Path("/single-user")
	@Produces(MediaType.APPLICATION_JSON)
	public ServerTestUser produceJSON() {
    	return new ServerTestUser("test@hascode.com", "Tim","Testerman", 1);
	}
    //  > Template-Method-Pattern
    //  
    //  Abstract class: Transaktionsbegleitung in execute Methode. TRY, FINALLY.
    //  Abstract class: public E execute() calls executeWithThrows, 
    // 					-> Durchreichung von Resultat: executeWithThrows -> execute
    //  Concrete class: Implements executeWithThrows
    //  
    //	new Transaction<E>(..) {
    //		
    // 		public E executeWithThrows() throws Exception {
    //			...
    //			final DmUser user = daUser.create();
    //			...
    //			return user;
    //  }.execute();
}

