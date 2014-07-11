package de.bht.comanche.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaUser;
import de.bht.comanche.server.exceptions.NoUserWithThisNameException;
import de.bht.comanche.server.exceptions.WrongPasswordException;

@Path("/user/")
public class UserService extends Service {
	public UserService() {
		super();
	}

	@Path("login")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ResponseObject loginUser(final LgUser userFromClient) {
		final DaUser daUser = factory.getDaUser();
		ResponseObject response = new Transaction<LgUser>(daUser.getPool()) {
			public LgUser executeWithThrows() throws Exception {
				List<LgUser> users = daUser
						.findByName(userFromClient.getName());
				if (users.isEmpty()) {
					throw new NoUserWithThisNameException();
				}
				LgUser userFromDb = users.get(0);
				if (!userFromDb.passwordMatchWith(userFromClient)) {
					throw new WrongPasswordException();
				}
				LgUser userWithId = new LgUser();
				userWithId.setIdFrom(userFromDb);
				return userWithId;
			}
		}.execute();
		if (response.hasError()) {
			throw new WebApplicationException(response.getResponseCode());
		}
		return response;
	}
	
//	/**
//	 * Return complete User by Id
//	 * @param userFromClient
//	 * @return
//	 */
//	@POST
//	@Path("get")
//	@Consumes("application/json")
//	@Produces({"application/json"})
//	public ResponseObject getUser(final LgUser userFromClient){
//		ResponseObject response = new Transaction<LgUser>() {
//			public LgUser executeWithThrows() throws Exception {
//				DaFactory jpaDaFactory = new JpaDaFactory();
//				DaUser daUser = jpaDaFactory.getDaUser();
//				daUser.beginTransaction();
//				System.out.println("OID ---------------------->" + userFromClient.getOid());
////				LgUser userFromDb = daUser.find(userFromClient.getOid()); //  <--  the real thing
//				LgUser userFromDb = daUser.getDummy(); //  <-- test
//				daUser.endTransaction(true);
//				System.out.println("from client: " + userFromClient);
//				System.out.println("from server: " + userFromDb);
//				return userFromDb;
//			}
//		}.execute();
//
//		if (!response.isSuccess()) {
//			printDebug(response);
////			throw new WebApplicationException("[get] Something went wrong", 500); // TODO message
//		}
//		return response;
//	}
//   	 
//     @Path("/register")
//     @POST
//     @Consumes("application/json")
//     @Produces({"application/json"})
//     public ResponseObject registerUser(final LgUser newUserFromClient){
//    	 ResponseObject response = new Transaction<LgUser>() {
//    		 public LgUser executeWithThrows() throws Exception {
//    			 DaFactory jpaDaFactory = new JpaDaFactory();
//    			 DaUser daUser = jpaDaFactory.getDaUser();
//    			 //throws Exc if name not exist - need boolin
//
//    			 if (daUser.findByName(newUserFromClient.getName()).iterator().hasNext()) {
//    				 throw new UserWithThisNameExistsExc();
//    			 }
//
//    			 LgUser user = new LgUser();
//    			 String name = newUserFromClient.getName();
//    			 user.setName(name);
//    			 user.setPassword(newUserFromClient.getPassword());
//    			 user.setEmail(newUserFromClient.getEmail());
//    			 user.setTel(newUserFromClient.getTel());
//    			 
//    			 daUser.save(user);
//    			 daUser.findByName(name);
//    			 return user;
//    		 }
//    	 }.execute();
//    	 
//    	 if (!response.isSuccess()) {
//    		 printDebug(response);
//    		 throw new WebApplicationException("User with this name exists", 500);
//    	 }
//    	 return response;
// 	}
//     
//     @Path("/delete")
//     @DELETE
//     @Consumes("application/json")
//     @Produces({"application/json"})
//     public ResponseObject deleteUser(final LgUser oldUserFromClient){
//    	 return new Transaction<LgUser>() {
//    		 public LgUser executeWithThrows() throws Exception {
//    			 DaFactory jpaDaFactory = new JpaDaFactory();
//    			 DaUser daUser = jpaDaFactory.getDaUser();
//    			 //throws Exc if Id not exist
//    			 LgUser userFromDb = daUser.find(oldUserFromClient.getOid()); 
//    			 daUser.delete(userFromDb);
//    			 return null;
//    		 }
//    	 }.execute();
// 	} 
//     
//     @Path("/update")
//     @POST
//     @Consumes("application/json")
//     @Produces({"application/json"})
//     public ResponseObject updateUser(final LgUser updateUserFromClient){
//    	 ResponseObject response = new Transaction<LgUser>() {
//    		 public LgUser executeWithThrows() throws Exception {
//    			 DaFactory jpaDaFactory = new JpaDaFactory();
//    			 DaUser daUser = jpaDaFactory.getDaUser();
//    			 LgUser userFromDb = daUser.find(updateUserFromClient.getOid());
//    			 userFromDb.updateWith(updateUserFromClient);
//    			 daUser.save(userFromDb);
//    			 return userFromDb;
//    		 }
//    	 }.execute();
//
//    	 if (!response.isSuccess()) {
//    		 printDebug(response);
////    		 throw new WebApplicationException("Illegal update failed", 500);
//    	 }
//    	 return response;
//     }
//     
//     
//     private void printDebug(ResponseObject response) {
// 		System.out.println(response.isSuccess());
// 		for (DbObject o: response.getData()) {
// 			System.out.println(o.getOid());
// 		}
// 		for (String s: response.getServerMessages()) {
// 			System.out.println(s);
// 		}
// 	}
}
