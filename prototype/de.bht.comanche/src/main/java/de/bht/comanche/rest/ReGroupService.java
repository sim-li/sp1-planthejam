package de.bht.comanche.rest;

import static multex.MultexUtil.create;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import de.bht.comanche.logic.LgGroup;
import de.bht.comanche.logic.LgTransaction;

/**
 * This class provide a LgGroup service as a network-accessible endpoint by using Representational State Transfer (RESTful) web service (JAX-RS).
 * Jersey implements support for the annotations defined in the specification and used in this class. Resources are identified by URIs,
 * which provide a global addressing space for resource and service discovery. The @Path annotation identifies the URI path template to which the
 * resource responds and is specified at the class or method level of a resource.
 *
 * @author Maxim Novichkov
 *
 *
 */
@Path("/groups/")
public class ReGroupService extends RestService {

	/**
	 * Returns the list of groups with all groups for current user.
	 * @param request The request information from HTTP service.
	 * @return Returns the list of LgGroups.
	 * @exception RestGetGroupsFailure if it was not possible to get all groups for LgUser.
	 */
	@Path("/")
	@GET
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgGroup> get(@Context final HttpServletRequest request) {
		return new LgTransaction<List<LgGroup>>(request) {
			@Override
			public List<LgGroup> execute() throws Exception {
				final List<LgGroup> result;
				try {
					result = startSession().getGroups();
				} catch (Exception ex) {
					throw create(RestGetGroupsFailure.class, ex, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not get groups for user "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class RestGetGroupsFailure extends multex.Failure {}

	/**
	 * Save group for current user.
	 * @param group The LgGroup to be save.
	 * @param request The request information from HTTP service.
	 * @return Returns saved LgGroup.
	 * @exception RestSaveGroupFailure if it was not possible to save LgGroup.
	 */
	@Path("/")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgGroup save(final LgGroup group, @Context final HttpServletRequest request) {
		return new LgTransaction<LgGroup>(request) {
			@Override
			public LgGroup execute() throws Exception {
				final LgGroup result;
				try {
					result = startSession().save(group);
				} catch (Exception ex) {
					throw create(RestSaveGroupFailure.class, ex, group.getName(), group.getOid(), getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}
	
	@Path("/{oid}")
	@PUT
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgGroup update(@PathParam("oid") final long oid, final LgGroup group, @Context final HttpServletRequest request) {
		return new LgTransaction<LgGroup>(request) {
			@Override
			public LgGroup execute() throws Exception {
				final LgGroup result;
				try {
					result = startSession().save(group);
				} catch (Exception ex) {
					throw create(RestSaveGroupFailure.class, ex, group.getName(), group.getOid(), getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not save group "{0}" with oid "{1}" for user "{2}"
	 */
	@SuppressWarnings("serial")
	public static final class  RestSaveGroupFailure extends multex.Failure {}

	/**
	 * Delete LgGrop specified by oid.
	 * @param oid 	  The LgGroup oid to be deleted.
	 * @param request The request information from HTTP service.
	 * @exception RestDeleteGroupFailure if it was not possible to delete LgGroup.
	 */
	@Path("/{oid}")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgGroup delete(@PathParam("oid") final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction<LgGroup>(request) {
			@Override
			public LgGroup execute() throws Exception {
				try {
					startSession().deleteGroup(oid);
				} catch (Exception ex) {
					throw create(RestDeleteGroupFailure.class, ex, oid, getSession().getUser().getName() );

				}
				return null;
			}
		}.getResult();
	}

	/**
	 * Could not delete group with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestDeleteGroupFailure extends multex.Failure {}

}
