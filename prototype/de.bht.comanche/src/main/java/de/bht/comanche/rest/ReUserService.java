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
	
	public ReUserService() {
		super();
	}

	@Path("login")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser login(final LgUser i_user, @Context final HttpServletRequest request) {
		final LgSession session = new LgSession();
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
				session.login(i_user); //throw exc when login failure
				final LgUser o_user = session.getUser();
				RestService.setUserName(request, o_user.getName());
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
					session.registerUser(i_user);
					LgUser o_user = session.getUser();
					RestService.setUserName(request, o_user.getName());
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
					session.startFor(RestService.getUserName(request)); // throw Exception if no info in request
					final LgUser user = session.getUser();
					user.delete(); // must throw if Exception if null or user not found
					RestService.removeUserName(request);
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
				    session.startFor(RestService.getUserName(request));
				    final LgUser user = session.getUser();
					user.update(i_user);
					RestService.setUserName(request, user.getName()); //Bug on multiple updates!
					//How do we transfer name back with HTTP Serv request?
				return null;
			}
		}.getResult();
	}
}