package de.bht.comanche.server;

import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import de.bht.comanche.logic.DbObject;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaFactory;
import de.bht.comanche.persistence.DaUser;
import de.bht.comanche.persistence.JpaDaFactory;

@Path("/user/")
//@Produces({"text/xml", "application/json"})
//@Consumes({"text/xml", "application/json"})
public class UserService {
	
	@Path("login")
    @POST
    @Consumes("application/json")
    @Produces({"application/json"})
    public ResponseObject loginUser(final LgUser userFromClient) {
		 ResponseObject response = new Transaction<LgUser>() {
			 public LgUser executeWithThrows() throws Exception {
				 DaFactory jpaDaFactory = new JpaDaFactory();
				 DaUser daUser = jpaDaFactory.getDaUser();
				 
				 //  <--  the real thing --> 
//				 Iterator<LgUser> it = daUser.findByName(userFromClient.getName()).iterator();
//				 if (!it.hasNext()) {
//					 throw new NoUserWithThisNameExc();
//				 }
//				 
//				 LgUser userFromDb = it.next(); // <--
				 LgUser userFromDb = daUser.getDummy(); //  <-- test
				 System.out.println("from client: " + userFromClient);
				 System.out.println("from server: " + userFromDb);
				 if (!userFromDb.passwordMatchWith(userFromClient.getPassword())) {
				 
//				 if (!userFromDb.validatePassword(userFromClient.getPassword())) { //  <-- the real thing

					 System.out.println("client-pw: '" + userFromClient.getPassword() + "'" + 
							 			"server-pw: '" + userFromDb.getPassword() + "'");

					 throw new WrongPasswordExc();
				 }
				 LgUser userWithId = new LgUser();
				 userWithId.setIdFrom(userFromDb);
				 return userWithId;
			 }
		 }.execute();
		
		if (!response.isSuccess()) { // user with specified name not found or wrong password
			printDebug(response);
			throw new WebApplicationException("Wrong name or password", 500);
		}
		return response;
		
//---- TODO for the future: try to send Exceptions via REST to client like this:  
//		throw new WebApplicationException("Wrong password", 500) {
//		};
   	 
    }
	
	//return complete User by Id
	@POST
	@Path("get")
	@Consumes("application/json")
	@Produces({"application/json"})
	public ResponseObject getUser(final LgUser userFromClient){
		ResponseObject response = new Transaction<LgUser>() {
			public LgUser executeWithThrows() throws Exception {
				DaFactory jpaDaFactory = new JpaDaFactory();
				DaUser daUser = jpaDaFactory.getDaUser();
				
//				LgUser userFromDb = daUser.find(userFromClient.getOid()); //  <--  the real thing
				LgUser userFromDb = daUser.getDummy(); //  <-- test
				System.out.println("from client: " + userFromClient);
				System.out.println("from server: " + userFromDb);
				return userFromDb;
			}
		}.execute();

		if (!response.isSuccess()) {
			printDebug(response);
			throw new WebApplicationException("[get] Something went wrong", 500); // TODO message
		}
		return response;
	}
   	 
     @Path("/register")
     @POST
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject registerUser(final LgUser newUserFromClient){
    	 ResponseObject response = new Transaction<LgUser>() {
    		 public LgUser executeWithThrows() throws Exception {
    			 DaFactory jpaDaFactory = new JpaDaFactory();
    			 DaUser daUser = jpaDaFactory.getDaUser();
    			 //throws Exc if name not exist - need boolin

    			 if (daUser.findByName(newUserFromClient.getName()).iterator().hasNext()) {
    				 throw new UserWithThisNameExistsExc();
    			 }

    			 LgUser user = new LgUser();
    			 String name = newUserFromClient.getName();
    			 user.setName(name);
    			 user.setPassword(newUserFromClient.getPassword());
    			 user.setEmail(newUserFromClient.getEmail());
    			 user.setTel(newUserFromClient.getTel());
    			 
    			 daUser.save(user);
    			 daUser.findByName(name);
    			 return user;
    		 }
    	 }.execute();
    	 
    	 if (!response.isSuccess()) {
    		 printDebug(response);
    		 throw new WebApplicationException("User with this name exists", 500);
    	 }
    	 return response;
 	}
     
     @Path("/delete")
     @DELETE
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject deleteUser(final LgUser oldUserFromClient){
    	 return new Transaction<LgUser>() {
    		 public LgUser executeWithThrows() throws Exception {
    			 DaFactory jpaDaFactory = new JpaDaFactory();
    			 DaUser daUser = jpaDaFactory.getDaUser();
    			 //throws Exc if Id not exist
    			 LgUser userFromDb = daUser.find(oldUserFromClient.getOid()); 
    			 daUser.delete(userFromDb);
    			 return null;
    		 }
    	 }.execute();
 	} 
     
     @Path("/update")
     @POST
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject updateUser(final LgUser updateUserFromClient){
    	 ResponseObject response = new Transaction<LgUser>() {
    		 public LgUser executeWithThrows() throws Exception {
    			 DaFactory jpaDaFactory = new JpaDaFactory();
    			 DaUser daUser = jpaDaFactory.getDaUser();
    			 LgUser userFromDb = daUser.find(updateUserFromClient.getOid());
    			 userFromDb.updateWith(updateUserFromClient);
    			 daUser.save(userFromDb);
    			 return userFromDb;
    		 }
    	 }.execute();

    	 if (!response.isSuccess()) {
    		 printDebug(response);
    		 throw new WebApplicationException("Illegal update failed", 500);
    	 }
    	 return response;
     }
     
     
     private void printDebug(ResponseObject response) {
 		System.out.println(response.isSuccess());
 		for (DbObject o: response.getData()) {
 			System.out.println(o.getOid());
 		}
 		for (String s: response.getServerMessages()) {
 			System.out.println(s);
 		}
 	}
}

