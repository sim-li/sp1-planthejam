package de.bht.comanche.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import org.jboss.logging.annotations.Param;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaUser;
import de.bht.comanche.server.exceptions.logic.NoUserWithThisIdException;
import de.bht.comanche.server.exceptions.logic.NoUserWithThisNameException;
import de.bht.comanche.server.exceptions.logic.UserWithThisNameExistsException;
import de.bht.comanche.server.exceptions.logic.WrongPasswordException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

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
				List<LgUser> users = daUser.findByName(userFromClient.getName());
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
	
	/**
	 * Return complete User by Id
	 * @param userFromClient
	 * @return
	 */
	@POST
	@Path("getUser")
	@Consumes("application/json")
	@Produces({"application/json"})
	public ResponseObject getUser(final LgUser userFromClient){
		final DaUser daUser = factory.getDaUser();
		ResponseObject response = new Transaction<LgUser>(daUser.getPool()) {
			public LgUser executeWithThrows() throws Exception {
				System.out.println("HUHU from GET");
				System.out.println(userFromClient.getOid());
				
				LgUser lgUser = null;
				try{
					lgUser = daUser.find(userFromClient.getOid());
				} catch (OidNotFoundException oid){
					System.out.println("WRONG ID");
					throw new NoUserWithThisIdException();
				}
				
				System.out.println("RETURNING USER");
				return lgUser;
			}
		}.execute();

		if (response.hasError()) {
			System.out.println("RESPONSE HAS ERROR");
			throw new WebApplicationException(response.getResponseCode());
		}
		
		System.out.println("RESPONSE HAS RESPONSE");
		return response;
	}
   	 
     @Path("register")
     @POST
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject registerUser(final LgUser newUserFromClient){
    	 final DaUser daUser = factory.getDaUser();
    	 ResponseObject response = new Transaction<LgUser>(daUser.getPool()) {
    		 public LgUser executeWithThrows() throws Exception {
    			 if (!daUser.findByName(newUserFromClient.getName()).isEmpty()) {
    				 throw new UserWithThisNameExistsException();
    			 }
    			 daUser.save(newUserFromClient);
    			 return newUserFromClient;
    		 }
    	 }.execute();

    	 if (response.hasError()) {
 			throw new WebApplicationException(response.getResponseCode());
 		}
    	 
    	 return response;
 	}
     
   @Path("delete")
//     @Path("delete{id}")
//     @DELETE
   @POST
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject deleteUser(final LgUser userFromClient){
//     public ResponseObject deleteUser(@PathParam("oid") final long oid){
    	final DaUser daUser = factory.getDaUser();
  		ResponseObject response = new Transaction<LgUser>(daUser.getPool()) {
  			public LgUser executeWithThrows() throws Exception {
  				
  				System.out.println(userFromClient);
  				
  				LgUser userFromDb = null;
  				try{
					userFromDb = daUser.find(userFromClient.getOid());
  				} catch (OidNotFoundException oid) {
  					throw new NoUserWithThisIdException();
  				}
//    			daUser.delete(userFromClient);
  				daUser.delete(userFromDb);
    			return null;
    		 }
    	 }.execute();
    	 
    	 if (response.hasError()) {
  			throw new WebApplicationException(response.getResponseCode());
  		}
     	 
     	 return response;
 	} 
     
     @Path("update")
     @POST
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject updateUser(final LgUser updateUserFromClient){
    	final DaUser daUser = factory.getDaUser();
  		ResponseObject response = new Transaction<LgUser>(daUser.getPool()) {
  			public LgUser executeWithThrows() throws Exception {
//				List<LgUser> users = daUser.findByName(updateUserFromClient.getName());
//				if (users.isEmpty()) {
//					throw new NoUserWithThisNameException();
//				}
//				LgUser saveUsertoDb = users.get(0);^
  				
  				System.out.println(updateUserFromClient);
  				LgUser fromDb = daUser.find(updateUserFromClient.getOid());
  				fromDb.updateWith(updateUserFromClient);
//				daUser.save(updateUserFromClient);
  				daUser.save(fromDb);
				
//				saveUsertoDb.updateWith(updateUserFromClient);
//    			daUser.save(saveUsertoDb);
//    			return saveUsertoDb;
  				return null;
    		 }
    	 }.execute();

    	 if (response.hasError()) {
   			throw new WebApplicationException(response.getResponseCode());
   		}
      	 
      	 return response;
     }
}

