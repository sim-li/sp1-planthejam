package de.bht.comanche.server;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.jetty.server.Authentication.User;
import org.eclipse.jetty.util.ajax.JSON;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.JSONP;

import com.jayway.restassured.internal.mapping.Jackson1Mapper;
import com.owlike.genson.stream.JsonType;

import de.bht.comanche.logic.DtTimeperiod;
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
	
     @Path("/create")
     @POST
     @Consumes("application/json")
     @Produces({"application/json"})
     public ResponseObject create(LgUser obj){
    	 System.out.println(obj.toString());
    	DemoFactory dm = new DemoFactory();
 		return dm.getTransactionObject();
     }

     
     @Path("/create1")
     @POST
//     @Consumes("application/json")
     public void create1(Jackson1Mapper obj){
    	 System.out.println(obj.toString());
     
     }
     
//     @Path("/list")
//     @GET
//     public List<User> list(@QueryParam("first") @DefaultValue("0") int first,
//                            @QueryParam("max") @DefaultValue("20") int max) {
//         return dao.list(first, max);
//     }
//
//     @Path("/show/{id}")
//     @GET
//     public User show(@PathParam("id") long id) {
//         return dao.find(id);
//     }
//
//     @Path("/delete/{id}")
//     @DELETE
//     public void delete(@PathParam("id") long id) {
//         dao.delete(id);
//     }

//     @Path("/update/{id}")
//     @POST
//     public User update(@PathParam("id") long id,
//                        @QueryParam("name") String name,
//                        @QueryParam("telephone") String telephone,
//                        @QueryParam("email") String email,
//                        @QueryParam("password") String password) {
//         return dao.update(id, name, telephone, email, password);
//     }
	
//	private String name;
//    private String telephone;
//    private String email;
//    private String password;
	
	
}
