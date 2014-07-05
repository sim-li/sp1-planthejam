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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Authentication.User;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaUser;

@Path("/user/")
public class UserService {
	private DaUser dao;
	
     @GET
     @Path("/login")
     @Consumes(MediaType.APPLICATION_JSON)
     public Response createDataInJSON(String data) { 

         String result = "Data post: "+data;

         return Response.status(201).entity(result).build(); 
     }
	
     @Path("/create")
     @PUT
     public User create(@QueryParam("name") String name,
    		 			@QueryParam("telephone") String telephone,
    		 			@QueryParam("email") String email,
    		 			@QueryParam("password") String password) {
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
