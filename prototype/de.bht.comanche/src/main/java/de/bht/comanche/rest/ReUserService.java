package de.bht.comanche.rest;

import static multex.MultexUtil.create;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaPoolImpl;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaUser;

@Path("/user/")
public class ReUserService extends ReService {
	
	public ReUserService() {
		super();
	}

	//-------------------------------------multex ready------
	@Path("login")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgUser> loginUser(final LgUser userFromClient) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws multex.Exc {
				List<LgUser> users = daUser.findByName(userFromClient.getName());
				/*
				 * Available for Client: MultipleUsersWithThisNameException()
				 */
				// if (users.size() > 1) {
				// throw new MultipleUsersWithThisNameException();
				// }
				if (users.isEmpty()) {
					throw create(LgNoUserWithThisNameExc.class, createTimeStamp(), userFromClient.getName());
				}
				LgUser userFromDb = users.get(0);
				if (!userFromDb.passwordMatchWith(userFromClient)) {
					throw create(LgWrongPasswordExc.class, createTimeStamp(), userFromClient.getName());
				}
				return userFromDb;
			}
		}.execute();
	}

	//-------------------------------------multex ready------
	@Path("register")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgUser> registerUser(final LgUser newUserFromClient) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws multex.Exc {
				if (!daUser.findByName(newUserFromClient.getName()).isEmpty()) {
					throw create(LgUserWithThisNameExistsExc.class, createTimeStamp(), newUserFromClient.getName());
				}
				try{
					daUser.save(newUserFromClient);
				} catch (Exception ex){
					throw create(LgUserNotSavedExc.class, ex, createTimeStamp(), newUserFromClient.getName());
				}
				return newUserFromClient;
			}
		}.execute();
	}

	//------------------------------------------ multex-ready ----
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgUser> deleteUser(final LgUser userFromClient) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws multex.Exc {
				LgUser userFromDb = null;
				try {
					userFromDb = daUser.find(userFromClient.getOid()); // TODO use multex.Ex in DaUser.find
					userFromDb.clearInvites(); // FIXME ! should not be necessary -> JPA does this; otherwise implement daUserImpl.delete and call user.clearInvites there !
					daUser.delete(userFromDb);
				} catch (Exception ex) {
					throw create(DaPoolImpl.DataAccessExc.class, ex,
							createTimeStamp(),
							userFromClient.getOid(), 
							userFromClient.getName());
				}
				return null;
			}
		}.execute();
	} 

	//-------------------------------------multex ready---------
	@Path("update")
	@POST
	@Consumes("application/json")
	@Produces({"application/json"})
	public ReResponseObject<LgUser> updateUser(final LgUser dirtyUser) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws multex.Exc {
				try {
					daUser.find(dirtyUser.getOid()); // TODO use multex.Ex in DaUser.find
					daUser.update(dirtyUser);
				} catch (DaOidNotFoundExc oid) {
					throw create(LgNoUserWithThisIdNameExc.class, createTimeStamp(), dirtyUser.getOid(), 
							dirtyUser.getName());
				} catch (Exception ex){
					throw create(LgUserNotUpdatedExc.class, ex,	createTimeStamp(), dirtyUser.getOid(), 
							dirtyUser.getName());
				}
				return (LgUser) daUser;
			}
		}.execute();
	}
	
	private String createTimeStamp() {
		return new Date(System.currentTimeMillis()).toString();
	}
	
	/**
	 * Occured at "{0}". Could not save user with id "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class LgUserNotSavedExc extends multex.Exc {}
	
	/**
	 * Occured at "{0}". No user with id "{1}" and name "{2}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgNoUserWithThisIdNameExc extends multex.Exc {}
	
	/**
	 * Occured at "{0}". Could not update user with id "{1}" and name "{2}"
	 */
	@SuppressWarnings("serial")
	public static final class LgUserNotUpdatedExc extends multex.Exc {}
	
	/**
	 * Occured at "{0}". Wrong password for user with name "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class LgWrongPasswordExc extends multex.Exc {}
	
	/**
	 * Occured at "{0}". No user with name "{1}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgNoUserWithThisNameExc extends multex.Exc {}
	
	/**
	 * Occured at "{0}". A user with name "{1}" already exists in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgUserWithThisNameExistsExc extends multex.Exc {}
}
