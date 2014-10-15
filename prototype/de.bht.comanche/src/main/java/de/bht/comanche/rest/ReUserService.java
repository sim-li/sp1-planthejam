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
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.exceptions.LgNoUserWithThisIdException;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaUser;
//import javax.interceptor.AroundInvoke;
//import javax.ws.rs.

@Path("/user/")
public class ReUserService extends ReService {
	
	/**
	 * Wrong password for user with name "{0}". Occured at "{1}"
	 */
	public static final class LgWrongPasswordExc extends multex.Exc {
		private static final long serialVersionUID = 1053325287184937876L;
	};
	
	/**
	 * No user with name "{0}" found in the database. Occured at "{1}"
	 */
	public static final class LgNoUserWithThisNameExc extends multex.Exc {
		private static final long serialVersionUID = 997957804551407265L;
	};
	
	/**
	 * A user with name "{0}" already exists in the database. Occured at "{1}"
	 */
	public static final class LgUserWithThisNameExistsExc extends multex.Exc {
		private static final long serialVersionUID = -2460348018637263735L;
	};
	
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
//					throw new LgNoUserWithThisNameException();
					throw MultexUtil.create(LgNoUserWithThisNameExc.class,
							userFromClient.getName(), 
							createTimeStamp());
				}
				LgUser userFromDb = users.get(0);
				if (!userFromDb.passwordMatchWith(userFromClient)) {
					throw MultexUtil.create(LgWrongPasswordExc.class, 
							userFromClient.getName(), 
							createTimeStamp());
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
			public LgUser executeWithThrows() throws Exception {
				if (!daUser.findByName(newUserFromClient.getName()).isEmpty()) {
					throw MultexUtil.create(LgWrongPasswordExc.class, 
							newUserFromClient.getName(), 
							createTimeStamp());
				}
				daUser.save(newUserFromClient);
				return newUserFromClient;
			}
		}.execute();
	}

	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgUser> deleteUser(final LgUser userFromClient) {
		final DaUser daUser = factory.getDaUser();
		return new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws Exception {
				LgUser userFromDb = null;
				try {
					userFromDb = daUser.find(userFromClient.getOid()); // TODO use multex.Ex in DaUser.find
				} catch (DaOidNotFoundException oid) {
					throw new LgNoUserWithThisIdException();
				}
				userFromDb.clearInvites();
				daUser.delete(userFromDb);
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
				} catch (DaOidNotFoundException oid) {
					throw new LgNoUserWithThisIdException();
				}
				return daUser.update(dirtyUser);
			}
		}.execute();
	}

	public static final String CLICHED_MESSAGE = "Hello World!";

	@Path("hello")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHello() {
		return CLICHED_MESSAGE;
	}
	
	private String createTimeStamp() {
		return new Date(System.currentTimeMillis()).toString();
	}
}
