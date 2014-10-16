package de.bht.comanche.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import multex.MultexUtil;
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.exceptions.LgNoUserWithThisIdException;
import de.bht.comanche.exceptions.LgNoUserWithThisNameException;
import de.bht.comanche.exceptions.LgUserWithThisNameExistsException;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaUser;


@Path("/user/")
public class ReUserService extends ReService {
	
	/**
	 * Could not log in to account for user "{0}" with the given password at "{1}"
	 */
	public static final class LgWrongPasswordException extends multex.Exc {
		private static final long serialVersionUID = 1053325287184937876L;
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
					
					throw new LgNoUserWithThisNameException();
				}
				LgUser userFromDb = users.get(0);
				if (!userFromDb.passwordMatchWith(userFromClient)) {
//					throw new LgWrongPasswordException();
					throw MultexUtil.create(LgWrongPasswordException.class, 
							userFromClient.getName(), 
							new Date(System.currentTimeMillis()).toString());
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
		ReResponseObject<LgUser> response = new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws Exception {
				if (!daUser.findByName(newUserFromClient.getName()).isEmpty()) {
					throw new LgUserWithThisNameExistsException();
				}
				daUser.save(newUserFromClient);
				return newUserFromClient;
			}
		}.execute();
		if (response.hasError()) {
			throw new WebApplicationException(response.responseCode);
		}
		return response;
	}

	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgUser> deleteUser(final LgUser userFromClient) {
		final DaUser daUser = factory.getDaUser();
		ReResponseObject<LgUser> response = new LgTransaction<LgUser>(
				daUser.getPool()) {
			public LgUser executeWithThrows() throws Exception {
				LgUser userFromDb = null;
				try {
					userFromDb = daUser.find(userFromClient.getOid());
				} catch (DaOidNotFoundException oid) {
					throw new LgNoUserWithThisIdException();
				}
				userFromDb.clearInvites();
				daUser.delete(userFromDb);
				return null;
			}
		}.execute();
		if (response.hasError()) {
			throw new WebApplicationException(response.responseCode);
		}
		return response;
	} 

	@Path("update")
	@POST
	@Consumes("application/json")
	@Produces({"application/json"})
	public ReResponseObject<LgUser> updateUser(final LgUser dirtyUser) {
		final DaUser daUser = factory.getDaUser();
		ReResponseObject<LgUser> response = new LgTransaction<LgUser>(daUser.getPool()) {
			@Override
			public LgUser executeWithThrows() throws Exception {
				try {
					daUser.find(dirtyUser.getOid());
				} catch (DaOidNotFoundException oid) {
					throw new LgNoUserWithThisIdException();
				}
				return daUser.update(dirtyUser);
			}
		}.execute();
		if (response.hasError()) {
			throw new WebApplicationException(response.responseCode);
		}
		return response;
	}

	public static final String CLICHED_MESSAGE = "Hello World!";

	@Path("hello")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHello() {
		return CLICHED_MESSAGE;
	}
}
