package de.bht.comanche.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import jersey.repackaged.com.google.common.collect.Lists;
import de.bht.comanche.logic.DbObject;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaFactory;
import de.bht.comanche.persistence.DaUser;
import de.bht.comanche.persistence.JpaDaFactory;

@Path("/user/")
//@Produces({"text/xml", "application/json"})
//@Consumes({"text/xml", "application/json"})
public class UserService {
	
	@Path("/login")
    @POST
    @Consumes("application/json")
    @Produces({"application/json"})
    public ResponseObject loginUser(final LgUser userFromClient) {
		 ResponseObject response = new Transaction<LgUser>() {
			 public LgUser executeWithThrows() throws Exception {
				 DaFactory jpaDaFactory = new JpaDaFactory();
				 DaUser daUser = jpaDaFactory.getDaUser();
				 
				 Iterator<LgUser> it = daUser.findByName(userFromClient.getName()).iterator();
				 if (!it.hasNext()) {
					 throw new NoUserWithThisNameExc();
				 }
				 LgUser userFromDb = it.next();
				 if (!userFromDb.validatePassword(userFromClient.getPassword())) {

					 System.out.println(userFromClient.getPassword());
					 System.out.println(userFromDb.getPassword());

					 throw new WrongPasswordExc();
				 }
				 LgUser userWithId = new LgUser();
				 userWithId.setIdFrom(userFromDb);
				 System.out.println(userFromClient);
				 System.out.println(userFromDb);
				 System.out.println(userWithId);
				 return userWithId;
			 }
		 }.execute();
		
		if (!response.isSuccess()) { // user with specified name not found or wrong password
			
			//-- for debugging ----
			System.out.println(response.isSuccess());
			for (DbObject o: response.getData()) {
				System.out.println(o.getOid());
			}
			for (String s: response.getServerMessages()) {
				System.out.println(s);
			}
			//---------------------
			
			throw new WebApplicationException("Wrong name or password", 500) {
				private static final long serialVersionUID = -1427317534342289811L;
			};
		}
		return response;
		
//---- TODO for the future: try to send Exceptions via REST to client like this:  
//		throw new WebApplicationException("Wrong password", 500) {
//		};
   	 
    }
	
	//get full User by Id
	@POST
	@Path("get/")
	@Consumes("application/json")
	@Produces({"application/json"})
	public ResponseObject getUser(final LgUser userIdFromClient){
		return new Transaction<LgUser>() {
			public LgUser executeWithThrows() throws Exception {
				DaFactory jpaDaFactory = new JpaDaFactory();
				DaUser daUser = jpaDaFactory.getDaUser();
				//throws Exc if Id not exist
				LgUser userFromDb = daUser.find(userIdFromClient.getOid());
				return userFromDb;
			}
   	 }.execute();
	}
   	 
     @Path("/register")
     @POST
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject registerUser(final LgUser newUserFromClient){
    	 return new Transaction<LgUser>() {
 			public LgUser executeWithThrows() throws Exception {
 				DaFactory jpaDaFactory = new JpaDaFactory();
 				DaUser daUser = jpaDaFactory.getDaUser();
 				//throws Exc if name not exist - need boolin
 				LgUser userFromDb = daUser.findByName(newUserFromClient.getName()).iterator().next(); 
// 				if(userFromDb){
// 					if not exist -> save
// 				}
 				
 				//save new User to DbUser or LgUser?
 				LgUser newUserSaveToDb = new LgUser(); 
 				newUserSaveToDb.setName(newUserFromClient.getName());
				newUserSaveToDb.setEmail(newUserFromClient.getEmail());
				newUserSaveToDb.setPassword(newUserFromClient.getPassword());
 				return newUserSaveToDb;
 			}
    	 }.execute();
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
 				//if deleted set ID to -1? 
 				return null;
 			}
    	 }.execute();
 	} 
     
//     update
     @Path("/update")
     @POST
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject updateUser(final LgUser updateUserFromClient){
    	 return new Transaction<LgUser>() {
 			public LgUser executeWithThrows() throws Exception {
 				DaFactory jpaDaFactory = new JpaDaFactory();
 				DaUser daUser = jpaDaFactory.getDaUser();
 				//throws Exc if name not exist
 				LgUser userFromDb = daUser.find(updateUserFromClient.getOid());  
 				
 				//update new User to DbUser or LgUser?
 				userFromDb.setName(updateUserFromClient.getName());
 				userFromDb.setEmail(updateUserFromClient.getEmail());
 				userFromDb.setPassword(updateUserFromClient.getPassword());
				//add other fields for update too
 				return null;
 			}
    	 }.execute();
 	}
}




//@Path("/login")
//@GET
//@Consumes(MediaType.APPLICATION_JSON) ????
//public User login(@QueryParam("name") final String name,
//		 		   @QueryParam("password") final String password) {
//
//	 new Transaction<LgUser>() {
//		 public LgUser executeWithThrows() throws Exception {
//			 LgUser lgUser1 = new LgUser();
//			 Validation.validateName(name);
//			 Validation.validatePassword(password);
//			 DaFactory jpaDaFactory = new JpaDaFactory();
//			 DaUser daUser = jpaDaFactory.getDaUser();
//			 daUser.save(lgUser1);
//			 
//			 lgUser1 = daUser.findByName(name);
//			 lgUser1.validatePassword(password);
//			 
//			 return lgUser1;
//		 }
//	 }.execute();
//	 
//
//	 if (lgUser1.passwordCorrect) {
//		 // Build Sucess JSON
//		 
//	 } else {
//		 // Build Error JSON
//	 }
	 
	 
//}
