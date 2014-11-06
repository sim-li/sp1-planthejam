package de.bht.comanche.rest;

import static multex.MultexUtil.create;

import java.util.Date;
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
			public List<LgInvite> execute() throws multex.Exc {
				return startSession()
					.getInvites();
			}
		}.getResult();
	}
	
	@Path("save")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgInvite save(final LgInvite invite, @Context final HttpServletRequest request) {
		return new LgTransaction<LgInvite>(request) {
			public LgInvite execute() {
				return startSession()
					.save(invite);
			}
		}.getResult();
	}
	
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgInvite delete(final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction <LgInvite>(request) {
			public LgInvite execute() {
				startSession()
			    	.deleteInvite(oid);	
				return null;
			}
		}.getResult();
	}
	
//	private String createTimeStamp() {
//		return new Date(System.currentTimeMillis()).toString();
//	}
//	
//	/**
//	 * Occured at "{0}". Could not save invite with id "{1}" and user id "{2}"
//	 */
//	@SuppressWarnings("serial")
//	public static final class DaInviteNotSavedExc extends multex.Exc {}
//	
//	/**
//	 * Occured at "{0}". Could not delete invite with id "{1}"
//	 */
//	@SuppressWarnings("serial")
//	public static final class DaInviteNotDeletedExc extends multex.Exc {}
//	
//	/**
//	 * Occured at "{0}". No user with id "{1}" found in the database
//	 */
//	@SuppressWarnings("serial")
//	public static final class LgNoUserWithThisIdExc extends multex.Exc {}
//	
//	/**
//	 * Occured at "{0}". No invite with id "{1}" found in the database
//	 */
//	@SuppressWarnings("serial")
//	public static final class DaInviteIdNotFoundExc extends multex.Exc {}
}
