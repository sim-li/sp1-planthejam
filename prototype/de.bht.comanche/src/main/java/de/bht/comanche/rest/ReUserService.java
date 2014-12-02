package de.bht.comanche.rest;

import static multex.MultexUtil.create;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;

@Path("/user/")
public class ReUserService extends RestService {
	
//	final String USER_OID = "userOid";

	@Path("login")
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
					setUserName(request, o_user.getName());
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

	@Path("register")
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

	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgUser delete(@Context final HttpServletRequest request) { // TODO: Don't send OID from client
		return new LgTransaction<LgUser>(request) {
			@Override
			public LgUser execute() throws Exception {
				try {
					startSession().deleteAccount();
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

	@Path("get")
	@POST
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

	@Path("update")
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


	//	/* TODO
	//	 * - implement DaUser.selectAllUsersWhereNameIsLike(String searchString)
	//	 * - provide access to selectAllUsersWhereNameIsLike from session
	//	 */
	//	@Path("findUsers")
	//	@DELETE
	//	@Consumes("application/json")
	//	@Produces({ "application/json" })
	//	public LgInvite findUsers(final String searchString, @Context final HttpServletRequest request) {
	//		return new LgTransaction<List<LgUser>>(request) {
	//			@Override
	//			public List<LgUser> execute() throws Exception {
	//				return startSession().selectAllUsersWhereNameIsLike(searchString);
	//			}
	//		}.getResult();
	//	}

	@Path("getAllUsers")
	@POST
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
	 * Could not found any user
	 */
	@SuppressWarnings("serial")
	public static final class RestGetAllUsersFailure extends multex.Failure {}


	@Path("logout")
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

}
