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

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgTransaction;

@Path("/invite/")
public class ReInviteService extends RestService {

	@POST
	@Path("getInvites")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgInvite> get(@Context final HttpServletRequest request) {
		return new LgTransaction<List<LgInvite>>(request) {
			@Override
			public List<LgInvite> execute() throws Exception {
				final List<LgInvite> result;
				try {
					result = startSession().getInvites();
				} catch (Exception ex) {
					throw create(RestGetInvitesFailure.class, ex, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not get invites for user "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class RestGetInvitesFailure extends multex.Failure {}


	@POST
	@Path("get")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgInvite get(final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction<LgInvite>(request) {
			@Override
			public LgInvite execute() throws Exception {
				final LgInvite result;
				try {
					result = startSession().getInvite(oid);
				} catch (Exception ex) {
					throw create(RestGetInviteFailure.class, ex, oid, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not get invite with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestGetInviteFailure extends multex.Failure {}


	@Path("save")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgInvite save(final LgInvite invite, @Context final HttpServletRequest request) {
		return new LgTransaction<LgInvite>(request) {
			public LgInvite execute() throws Exception {
				final LgInvite result;
				try {
					result = startSession().save(invite);
				} catch (Exception ex) {
					throw create(RestSaveInviteFailure.class, ex, invite.getOid(), getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not save invite with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestSaveInviteFailure extends multex.Failure {}


	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgInvite delete(final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction <LgInvite>(request) {
			public LgInvite execute() throws Exception {
				try {
					startSession().deleteInvite(oid);
				} catch (Exception ex) {
					throw create(RestDeleteInviteFailure.class, ex, oid, getSession().getUser().getName());
				}
				return null;
			}
		}.getResult();
	}

	/**
	 * Could not delete invite with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestDeleteInviteFailure extends multex.Failure {}

}
