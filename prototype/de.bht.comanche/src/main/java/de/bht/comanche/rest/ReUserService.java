package de.bht.comanche.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import multex.MultexUtil;
import de.bht.comanche.exceptions.LgNoUserWithThisIdException;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaPoolImpl;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaUser;
//import javax.interceptor.AroundInvoke;
//import javax.ws.rs.

@Path("/user/")
public class ReUserService extends ReService {
	
	public ReUserService() {
		super();
	}

	@Path("login")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgUser> loginUser(final LgUser userFromClient) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws Exception {
				List<LgUser> users = daUser.findByName(userFromClient.getName());
				/*
				 * Available for Client: MultipleUsersWithThisNameException()
				 */
				// if (users.size() > 1) {
				// throw new MultipleUsersWithThisNameException();
				// }
				if (users.isEmpty()) {
					throw MultexUtil.create(LgNoUserWithThisNameExc.class, createTimeStamp(), userFromClient.getName());
				}
				LgUser userFromDb = users.get(0);
				if (!userFromDb.passwordMatchWith(userFromClient)) {
					throw MultexUtil.create(LgWrongPasswordExc.class, createTimeStamp(), userFromClient.getName());
				}
				return userFromDb;
			}
		}.execute();
	}

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
					throw MultexUtil.create(LgWrongPasswordExc.class, createTimeStamp(), newUserFromClient.getName());
				}
				daUser.save(newUserFromClient);
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
					throw MultexUtil.create(DaPoolImpl.DataAccessExc.class, ex,
							createTimeStamp(),
							userFromClient.getOid(), 
							userFromClient.getName());
				}
				return null;
			}
		}.execute();
	} 

	@Path("update")
	@POST
	@Consumes("application/json")
	@Produces({"application/json"})
	public ReResponseObject<LgUser> updateUser(final LgUser dirtyUser) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws Exception {
				try {
					daUser.find(dirtyUser.getOid()); // TODO use multex.Ex in DaUser.find
				} catch (DaOidNotFoundExc oid) {
					throw new LgNoUserWithThisIdException();
				}
				return daUser.update(dirtyUser);
			}
		}.execute();
	}
	
	// TODO unused? --> remove ------>
	public static final String CLICHED_MESSAGE = "Hello World!";

	@Path("hello")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHello() {
		return CLICHED_MESSAGE;
	}
	//<------
	
	
	private String createTimeStamp() {
		return new Date(System.currentTimeMillis()).toString();
	}
	
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
