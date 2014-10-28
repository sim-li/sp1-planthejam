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
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaPoolImpl;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaUser;

@Path("/user/")
public class ReUserService extends RestService {
	
	public ReUserService() {
		super();
	}
	
	//-----------------last multex ready------------------------------
	@Path("login")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser loginUser(final LgUser userFromClient) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws Exception {
				List<LgUser> users;
				try {
					users = daUser.findByName(userFromClient.getName());
				} catch (Exception ex) {
					throw create(LoginFailure.class, ex, createTimeStamp(), userFromClient.getName());
				}
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
	
	/**
	 * Ocurred at "{0}". Could not login user with name "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class LoginFailure extends multex.Failure {}
	
	/**
	 * Ocurred at "{0}". No user with name "{1}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgNoUserWithThisNameExc extends multex.Exc {}

	/**
	 * Ocurred at "{0}". Wrong password for user with name "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class LgWrongPasswordExc extends multex.Exc {}

	//-----------------last multex ready------------------------------
	@Path("register")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser registerUser(final LgUser newUserFromClient) {
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
					throw create(SaveFailure.class, ex, createTimeStamp(), newUserFromClient.getName());
				}
				return newUserFromClient;
			}
		}.execute();
	}
	
	/**
	 * Occured at "{0}". Could not save user with id "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class SaveFailure extends multex.Failure {}
	
	/**
	 * Occured at "{0}". A user with name "{1}" already exists in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgUserWithThisNameExistsExc extends multex.Exc {}
	
	//-----------------last multex ready------------------------------
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser deleteUser(final LgUser userFromClient) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws Exception {
				LgUser userFromDb = null;
				try {
					userFromDb = daUser.find(userFromClient.getOid()); //should it throw DaNotFoudExc seperate?
					daUser.delete(userFromDb);
				} catch (Exception ex) {
					throw create(DeleteFailure.class, ex,
							createTimeStamp(),
							userFromClient.getOid(), 
							userFromClient.getName());
				}
				return null;
			}
		}.execute();
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
	public LgUser updateUser(final LgUser dirtyUser) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			LgUser lguser = null;
			@Override
			public LgUser executeWithThrows() throws multex.Exc {
				try {
					daUser.find(dirtyUser.getOid()); 
					lguser = daUser.update(dirtyUser);
				} catch (DaOidNotFoundExc oid) {
					throw create(LgNoUserWithThisIdNameExc.class, createTimeStamp(), dirtyUser.getOid(), 
							dirtyUser.getName());
				} catch (Exception ex){
					throw create(UpdateFailure.class, ex, createTimeStamp(), dirtyUser.getOid(), 
							dirtyUser.getName());
				}
				return lguser;
			}
		}.execute();
	}
	
	/**
	 * Occured at "{0}". Could not update user with id "{1}" and name "{2}"
	 */
	@SuppressWarnings("serial")
	public static final class UpdateFailure extends multex.Failure {}
	
	/**
	 * Occured at "{0}". No user with id "{1}" and name "{2}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgNoUserWithThisIdNameExc extends multex.Exc {}
	
	
	private String createTimeStamp() {
		return new Date(System.currentTimeMillis()).toString();
	}
}
