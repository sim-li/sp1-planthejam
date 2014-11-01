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

@Path("/user/")
public class ReUserService extends RestService {
	final String USER_OID = "userOid";
	
	@Path("login")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser login(final LgUser i_user, @Context final HttpServletRequest request) {
		final LgSession session = new LgSession();
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
				 //throw exc when login failure
				final LgUser o_user = session.login(i_user).getUser();
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
		final LgSession session = new LgSession();
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
					final LgUser o_user = session.registerUser(i_user).getUser();
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
		final LgSession session = new LgSession();
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
				 // throw Exception if no info in request 
				//must throw if Exception if null or user not found
				session.startFor(getUserName(request))
				 	.getUser()
				 		.delete(); 
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
		final LgSession session = new LgSession();
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
				    final LgUser user = session.startFor(getUserName(request))
				    		.getUser()
				    			.updateWith(i_user);
				    setUserName(request, user.getName());
				return null;
			}
		}.getResult();
	}
}