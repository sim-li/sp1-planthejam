package de.bht.comanche.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;


//Delete this line plese

@Path("/user/")
public class ReUserService extends RestService {
	final String USER_OID = "userOid";
	
	@Path("login")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser login(final LgUser i_user, @Context final HttpServletRequest request) {
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws multex.Exc {
				 //throw exc when login failure
				final LgUser o_user = getSession()
				    .login(i_user);
				setUserName(request, o_user.getName());
				return o_user;
			}
		}.getResult();
	}
	
	@Path("register")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser register(final LgUser i_user, @Context final HttpServletRequest request) {
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws multex.Exc {
					final LgUser o_user = getSession()
						.register(i_user);
					setUserName(request, o_user.getName());
					return o_user;
			}
		}.getResult();
	}

	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser delete(@Context final HttpServletRequest request) { // TODO: Don't send OID from client
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws multex.Exc {
				 // throw Exception if no info in request 
				//must throw if Exception if null or user not found
			    startSession()
			        .deleteAccount();
				removeUserName(request);
				return null;
			}
		}.getResult();
	} 
	
	@Path("update")
	@POST
	@Consumes("application/json")
	@Produces({"application/json"})
	public LgUser update(final LgUser i_user, @Context final HttpServletRequest request) {
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws multex.Exc {
				    final LgUser user = getSession() 
				    	.save(i_user);
				    setUserName(request, user.getName());
				return null;
			}
		}.getResult();
	}
}