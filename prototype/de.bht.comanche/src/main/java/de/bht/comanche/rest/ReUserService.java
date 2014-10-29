package de.bht.comanche.rest;

import static multex.MultexUtil.create;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import multex.Failure;
import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaHibernateJpaPool;

@Path("/user/")
public class ReUserService extends RestService {
	protected final LgSession session = new LgSession();
	
	public ReUserService() {
		super();
	}

	@Path("login")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser loginUser(final LgUser user) {
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
//				try {
					return session.login(user);
//				} catch (Exception ex) {
//					throw create(LoginExc.class, ex, user.getName());
//				}
			}
		}.execute();
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
		}.execute();
	}
//
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser deleteUser(final LgUser user) {
		return new LgTransaction<LgUser>(session) {
			@Override
			public LgUser execute() throws multex.Exc {
				try {
					session.deleteUser(user);
				} catch (Exception ex) {
//					throw create(DeleteFailure.class, ex,
//							createTimeStamp(),
//							userFromClient.getOid(), 
//							userFromClient.getName());
				}
				return null;
			}
		}.execute();
	} 
//	
//	/**
//	 * Ocurred at "{0}". Could not delete user with oid "{1}" and name "{2}"
//	 */
//	@SuppressWarnings("serial")
//	public static final class DeleteFailure extends multex.Failure {}
//
//	//------------------------------------- not ready multex ready--------- TODO
//	@Path("update")
//	@POST
//	@Consumes("application/json")
//	@Produces({"application/json"})
//	public LgUser updateUser(final LgUser dirtyUser) {
//		final DaUser daUser = factory.getDaUser();
//		return new LgTransaction<LgUser>(daUser.getPool()) {
//			LgUser lguser = null;
//			@Override
//			public LgUser executeWithThrows() throws multex.Exc {
//				try {
//					daUser.find(dirtyUser.getOid()); // TODO use multex.Ex in DaUser.find
//					lguser = daUser.update(dirtyUser);
//				} catch (DaOidNotFoundExc oid) {
//					throw create(LgNoUserWithThisIdNameExc.class, createTimeStamp(), dirtyUser.getOid(), 
//							dirtyUser.getName());
//				} catch (Exception ex){
//					throw create(LgUserNotUpdatedExc.class, ex,	createTimeStamp(), dirtyUser.getOid(), 
//							dirtyUser.getName());
//				}
//				return lguser;
//			}
//		}.execute();
//	}
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
