package de.bht.comanche.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Authentication.User;

import de.bht.comanche.logic.DtTimeperiod;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaFactory;
import de.bht.comanche.persistence.DaUser;
import de.bht.comanche.persistence.JpaDaFactory;

@Path("/user/")
@Produces({"text/xml", "application/json"})
public class UserService {
	
     
     @Path("/login")
     @GET
//     @Consumes(MediaType.APPLICATION_JSON) ????
     public User login(@QueryParam("name") final String name,
    		 		   @QueryParam("password") final String password) {

    	 new Transaction<LgUser>() {
    		 public LgUser executeWithThrows() throws Exception {
    			 LgUser lgUser1 = new LgUser();
    			 Validation.validateName(name);
    			 Validation.validatePassword(password);
    			 DaFactory jpaDaFactory = new JpaDaFactory();
    			 DaUser daUser = jpaDaFactory.getDaUser();
    			 daUser.save(lgUser1);
    			 lgUser1 = daUser.findByName(name);
    			 lgUser1.validatePassword(password);
    			 return lgUser1;
    		 }
    	 }.execute();
    	 
    	 if (lgUser1.passwordCorrect) {
    		 // Build Sucess JSON
    		 
    	 } else {
    		 // Build Error JSON
    	 }
     } 
	
     @Path("/create")
     @PUT
     public User create(@QueryParam("name") final String name,
    		 			@QueryParam("telephone") final String telephone,
    		 			@QueryParam("email") final String email,
    		 			@QueryParam("password") final String password) {
    	 
    	 new Transaction<LgUser>() {
 			public LgUser executeWithThrows() throws Exception {
 				LgUser lgUser1 = new LgUser();
 				Validation.validateName(name);
 				lgUser1.setName(name);
 				lgUser1.setPassword(password);
 				lgUser1.setTelephone(telephone);
 				lgUser1.setEmail(email); // will throw NoValidEmailExc 
 				DaFactory jpaDaFactory = new JpaDaFactory();
 				DaUser daUser = jpaDaFactory.getDaUser();
 				daUser.save(lgUser1);
 				return lgUser1;
 			}
			
 		}.execute();
    	 
         return dao.create(name, telephone, email, password);
     }
     
     @Path("/list")
     @GET
     public List<User> list(@QueryParam("first") @DefaultValue("0") int first,
                            @QueryParam("max") @DefaultValue("20") int max) {
         return dao.list(first, max);
     }

     @Path("/show/{id}")
     @GET
     public User show(@PathParam("id") long id) {
         return dao.find(id);
     }

     @Path("/delete/{id}")
     @DELETE
     public void delete(@PathParam("id") long id) {
         dao.delete(id);
     }

     @Path("/update/{id}")
     @POST
     public User update(@PathParam("id") long id,
                        @QueryParam("name") String name,
                        @QueryParam("telephone") String telephone,
                        @QueryParam("email") String email,
                        @QueryParam("password") String password) {
         return dao.update(id, name, telephone, email, password);
     }
	
//	private String name;
//    private String telephone;
//    private String email;
//    private String password;
	
	
}
