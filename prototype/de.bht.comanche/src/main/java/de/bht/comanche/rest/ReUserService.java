package de.bht.comanche.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
	public LgUser loginUser(final LgUser user, @Context final HttpServletRequest request) {
		final LgSession session = new LgSession();
		return new LgTransaction<LgUser>(session) {//(session, username) 
			@Override
			public LgUser execute() throws multex.Exc {
				final LgUser loggedIn = session.login(user); 
				final HttpSession httpSession = request.getSession();
				httpSession.setAttribute(USER_OID, Long.toString(loggedIn.getOid()));
//				try {
					return loggedIn; 
//				} catch (Exception ex) {
//					throw create(LoginExc.class, ex, user.getName());
//				}
			}
		}.getResult();
	}
	
	/**
	 * Could not login user with name "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class LoginExc extends multex.Exc {}
	
//	
//	@SuppressWarnings("serial")
//	public static final class LgWrongPasswordExc extends multex.Exc {}
//	
//	//------------------------------------- not fully multex ready------ TODO
	@Path("register")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser registerUser(final LgUser user) {
		final LgSession session = new LgSession();
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
//				if (!daUser.findByName(newUserFromClient.getName()).isEmpty()) {
//					throw create(LgUserWithThisNameExistsExc.class, createTimeStamp(), newUserFromClient.getName());
//				}
				try{
					return session.registerUser(user);
				} catch (Exception ex) {
//					throw create(LgUserNotSavedExc.class, ex, createTimeStamp(), newUserFromClient.getName());
				}
				return null; 
			}
		}.getResult();
	}
//
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser deleteUser(final LgUser userX, @Context final HttpServletRequest request) {
		final LgSession session = new LgSession();
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
				try {
					final HttpSession httpSession = request.getSession();
				    final long oid = Long.parseLong(httpSession.getAttribute(USER_OID).toString());
					session.deleteUser(oid);
				} catch (Exception ex) {
					ex.printStackTrace();
//					throw create(DeleteFailure.class, ex,
//							createTimeStamp(),
//							userFromClient.getOid(), 
//							userFromClient.getName());
				}
				return null;
			}
		}.getResult();
	} 
	
	/**
	 * Ocurred at "{0}". Could not delete user with oid "{1}" and name "{2}"
	 */
	@SuppressWarnings("serial")
	public static final class DeleteFailure extends multex.Failure {}

	//------------------------------------- not ready multex ready--------- TODO
	@Path("update")
	@POST
	@Consumes("application/json")
	@Produces({"application/json"})
	public LgUser updateUser(final LgUser user) {
		final LgSession session = new LgSession();
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
				try {
//					session.receiveObject(user);
//					user.update();
				} catch (Exception ex) {
//					throw create(DeleteFailure.class, ex,
//							createTimeStamp(),
//							userFromClient.getOid(), 
//							userFromClient.getName());
				}
				return null;
			}
		}.getResult();
	}
//	
//	private String createTimeStamp() {
//		return new Date(System.currentTimeMillis()).toString();
//	}
//	
////	TODO check if exc are used:
//	
//	/**
//	 * Occured at "{0}". Could not save user with id "{1}"
//	 */
//	@SuppressWarnings("serial")
//	public static final class LgUserNotSavedExc extends multex.Exc {}
//	
//	/**
//	 * Occured at "{0}". No user with id "{1}" and name "{2}" found in the database
//	 */
//	@SuppressWarnings("serial")
//	public static final class LgNoUserWithThisIdNameExc extends multex.Exc {}
//	
//	/**
//	 * Occured at "{0}". Could not update user with id "{1}" and name "{2}"
//	 */
//	@SuppressWarnings("serial")
//	public static final class LgUserNotUpdatedExc extends multex.Exc {}
//		
//	/**
//	 * Occured at "{0}". A user with name "{1}" already exists in the database
//	 */
//	@SuppressWarnings("serial")
//	public static final class LgUserWithThisNameExistsExc extends multex.Exc {}
}
