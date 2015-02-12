package de.bht.comanche.rest;

import static multex.MultexUtil.create;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.logic.LgMessage;
/**
 * This class provide a LgUser service as a network-accessible endpoint by using Representational State Transfer (RESTful) web service (JAX-RS). 
 * Jersey implements support for the annotations defined in the specification and used in this class. Resources are identified by URIs, 
 * which provide a global addressing space for resource and service discovery. The @Path annotation identifies the URI path template to which the 
 * resource responds and is specified at the class or method level of a resource. 
 * @author Maxim Novichkov
 *
 */
@Path("/user")
public class ReUserService extends RestService {
	
	/**
	 * Login a user on the platform.
	 * @param i_user The incoming user.
	 * @param request The request information from HTTP service.
	 * @return The LgUser object - current user.
	 * @exception RestLoginUserFailure if it was not possible to login the user.
	 */
	@Path("/login")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser login(final LgUser i_user, @Context final HttpServletRequest request) {
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws Exception {
				final LgUser o_user;
				try {
					o_user = getSession().login(i_user);
					setUserName(request, i_user.getName());
				} catch (Exception ex) {
					throw create(RestLoginUserFailure.class, ex, i_user.getName());
				}
				return o_user;
			}
		}.getResult();
	}

	/**
	 * Could not login user with name "{0}" and given password. The user name or password is incorrect.
	 */
	@SuppressWarnings("serial")
	public static final class RestLoginUserFailure extends multex.Failure {}
	
	/**
	 * Register a new user on the platform.
	 * @param i_user The incomig user.
	 * @param request The request information from HTTP service.
	 * @return The LgUser object - current user.
	 * @exception RestRegisterUserFailure if it was not register the user on the platform.
	 */
	@Path("/register")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser register(final LgUser i_user, @Context final HttpServletRequest request) {
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws Exception {
				final LgUser o_user;
				try {
					o_user = getSession().register(i_user);
					setUserName(request, o_user.getName());
				} catch (Exception ex) {
					throw create(RestRegisterUserFailure.class, ex, i_user.getName());
				}
				return o_user;
			}
		}.getResult();
	}

	/**
	 * Could not register user with name "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class RestRegisterUserFailure extends multex.Failure {}
	
	/**
	 * Delete user from the platform by using current user name from HTTP request.
	 * @param request The request information from HTTP service.
	 * @exception RestDeleteUserFailure if it was not possible to delete the user from the platform.
	 */
	@Path("/delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser delete(@Context final HttpServletRequest request) { // TODO: Don't send OID from client
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws Exception {
				try {
					startSession().deleteThisAccount();
					removeUserName(request);
				} catch (Exception ex) {
					throw create(RestDeleteUserFailure.class, ex, getSession().getUser().getName(), getSession().getUser().getOid());
				}
				return null;
			}
		}.getResult();
	}

	/**
	 * Could not delete user "{0}" with oid "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestDeleteUserFailure extends multex.Failure {}
	
	/**
	 * Get LgUser by using current user name from HTTP request.
	 * @param request The request information from HTTP service.
	 * @return The LgUser object - current user.
	 * @exception RestGetUserFailure if it was not possible to get current user.
	 */
	@Path("/")
	@GET
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser get(@Context final HttpServletRequest request) {
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws Exception {
				try {
					return startSession();
				} catch (Exception ex) {
					throw create(RestGetUserFailure.class, ex);
				}
			}
		}.getResult();
	}

	/**
	 * Could not get user
	 */
	@SuppressWarnings("serial")
	public static final class RestGetUserFailure extends multex.Failure {}
	
	/**
	 * Update LgUser information.
	 * @param i_user The incoming user.
	 * @param request The request information from HTTP service.
	 * @return The updated LgUser object.
	 * @exception RestUserUpdateFailure if it was not possible to update current user.
	 */
	@Path("/update")
	@POST
	@Consumes("application/json")
	@Produces({"application/json"})
	public LgUser update(final LgUser i_user, @Context final HttpServletRequest request) {
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws Exception {
				final LgUser user;
				try {
					user = getSession().save(i_user);
					setUserName(request, user.getName());
				} catch (Exception ex) {
					throw create(RestUserUpdateFailure.class, ex, i_user.getName(), i_user.getOid());
				}
				return user;
			}
		}.getResult();
	}

	/**
	 * Could not update user "{0}" with oid "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestUserUpdateFailure extends multex.Failure {}

	/**
	 * Get the list of all registered users.
	 * @param request The request information from HTTP service.
	 * @return The List of LgUsers.
	 * @exception RestGetAllUsersFailure if it was not possible to get list of users.
	 */
	@Path("/getAllUsers")
	@GET
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgUser> getAllUsers(@Context final HttpServletRequest request) {
		return new LgTransaction<List<LgUser>>(request) {
			@Override
			public List<LgUser> execute() throws Exception {
				final List<LgUser> result;
				try {
					result = getSession().getAllUsers();
					} catch (Exception ex) {
					throw create(RestGetAllUsersFailure.class, ex);
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not get any user
	 */
	@SuppressWarnings("serial")
	public static final class RestGetAllUsersFailure extends multex.Failure {}

	/**
	 * Logout user from the platform by invalidating the session.
	 * @param request The request information from HTTP service.
	 * @exception RestLogoutUserFailure if it was not possible to logout current user.
	 */
	@Path("/logout")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public Object logout(@Context final HttpServletRequest request) {
		return new LgTransaction<Object>(request) {
			@Override
			public Object execute() throws Exception {
				try {
					request.getSession().invalidate();
				} catch (Exception ex) {
					throw create(RestLogoutUserFailure.class, ex, getSession().getUser().getName(), getSession().getUser().getOid());
				}
				return null;
			}
		}.getResult();
	}

	/**
	 * Unable to logout user "{0}" with oid "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestLogoutUserFailure extends multex.Failure {}
    
    @Path("/messages")
    @GET
    @Consumes("application/json")
	@Produces({ "application/json" })
    public Set<LgMessage> getMessagesFor(@Context final HttpServletRequest request) {
        return new LgTransaction<Set<LgMessage>>(request) {
			@Override
			public Set<LgMessage> execute() throws Exception {
				try {
					return startSession().getMessages();
				} catch (Exception ex) {
					throw create(RestGetMessagesFailure.class, ex, getSession().getUser().getName(), getSession().getUser().getOid());
				}
			}
		}.getResult();
    }
    
    /**
	 * Unable to get messages for user "{0}" with oid "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestGetMessagesFailure extends multex.Failure {}

}
