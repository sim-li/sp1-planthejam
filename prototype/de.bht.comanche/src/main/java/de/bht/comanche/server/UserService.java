package de.bht.comanche.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaFactory;
import de.bht.comanche.persistence.DaUser;
import de.bht.comanche.persistence.JpaDaFactory;

@Path("/user/")
@Produces({"text/xml", "application/json"})
@Consumes({"text/xml", "application/json"})
public class UserService {
	
     
//     @Path("/login")
//     @GET
//     @Consumes(MediaType.APPLICATION_JSON) ????
//     public User login(@QueryParam("name") final String name,
//    		 		   @QueryParam("password") final String password) {
//
//    	 new Transaction<LgUser>() {
//    		 public LgUser executeWithThrows() throws Exception {
//    			 LgUser lgUser1 = new LgUser();
//    			 Validation.validateName(name);
//    			 Validation.validatePassword(password);
//    			 DaFactory jpaDaFactory = new JpaDaFactory();
//    			 DaUser daUser = jpaDaFactory.getDaUser();
//    			 daUser.save(lgUser1);
//    			 
//    			 lgUser1 = daUser.findByName(name);
//    			 lgUser1.validatePassword(password);
//    			 
//    			 return lgUser1;
//    		 }
//    	 }.execute();
//    	 
//
//    	 if (lgUser1.passwordCorrect) {
//    		 // Build Sucess JSON
//    		 
//    	 } else {
//    		 // Build Error JSON
//    	 }
    	 
    	 
//     }
	
	
	@Path("/login")
    @POST
    @Consumes("application/json")
    @Produces({"application/json"})
    public ResponseObject login(final LgUser userFromClient) {
		
		return new Transaction<LgUser>() {
			public LgUser executeWithThrows() throws Exception {
				DaFactory jpaDaFactory = new JpaDaFactory();
				DaUser daUser = jpaDaFactory.getDaUser();
				LgUser userFromDb = daUser.findByName(userFromClient.getName()).iterator().next();
				if (!userFromDb.validatePassword(userFromClient.getPassword())) {
					throw new WrongPasswordExc();
				}
				LgUser userWithId = new LgUser();
				userWithId.setIdFrom(userWithId);
				return userWithId;
			}
   	 }.execute();
   	 
    }
	
     @Path("/create")
     @POST
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject create(LgUser obj){
    	 System.out.println(obj.toString());
    	DemoFactory dm = new DemoFactory();
 		return dm.getTransactionObject();
     }
  
}
