package de.bht.comanche.rest;

import static multex.MultexUtil.create;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaInvite;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaSurvey;
import de.bht.comanche.persistence.DaUser;

@Path("/invite/")
public class ReInviteService extends RestService {
	public ReInviteService() {
		super();
	}
	@POST
	@Path("getInvites")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgInvite> getInvites(final long userFromClientOid) {
		final DaUser daUser0 = factory.getDaUser();
		return new LgTransaction<List<LgInvite>>(daUser0.getPool()) {
			public List<LgInvite> executeWithThrows() throws Exception {
				List<LgInvite> invites = null;
				try {
					LgUser lgUser = daUser0.find(userFromClientOid);
					invites = lgUser.getInvites();
				} catch (DaOidNotFoundExc oid) {
					throw create(LgNoUserWithThisIdExc.class, createTimeStamp(), userFromClientOid);
				} catch (Exception ex){
					throw create(GetInviteFailure.class, ex, createTimeStamp(),	userFromClientOid);
				}
				return invites;
			}
		}.execute();
	}
	
	/**
	 * Ocurred at "{0}". Could not find invite with oid "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class GetInviteFailure extends multex.Failure {}

	/**
	 * Occured at "{0}". No user with id "{1}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgNoUserWithThisIdExc extends multex.Exc {}
	
	// TODO: Save Error doesn't seem to pop up from DaPoolImpl ----> TODO changed to SaveInviteFailure, is it ok now?
	@Path("save")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgInvite saveInvite(final LgInvite invite) {
		final DaInvite daInvite = factory.getDaInvite();
		final DaUser daUser = factory.getDaUser();
		final DaSurvey daSurvey = factory.getDaSurvey();
		daUser.setPool(daInvite.getPool());
		daSurvey.setPool(daInvite.getPool());
		return new LgTransaction<LgInvite>(daInvite.getPool()) {
			LgInvite invitePers;
			public LgInvite executeWithThrows() throws Exception {
				try {	
					invite.setSurvey((LgSurvey)daUser.getPool().merge(invite.getSurvey()));
					invitePers = daInvite.update(invite); 
				} catch (Exception ex) {
					throw create(SaveInviteFailure.class, ex, createTimeStamp(), invite.getOid(), 
							invite.getUser().getOid());
				}
				return invitePers;
			}
		}.execute();
	}
	
	/**
	 * Occured at "{0}". Could not save invite with id "{1}" and user id "{2}"
	 */
	@SuppressWarnings("serial")
	public static final class SaveInviteFailure extends multex.Failure {}
	
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgInvite deleteUser(final long inviteFromClientOid) {
		final DaInvite daInvite = factory.getDaInvite();
		return new LgTransaction<LgInvite>(daInvite.getPool()) {
			public LgInvite executeWithThrows() throws Exception {
				LgInvite inviteFromDb = null;
				try {
					inviteFromDb = daInvite.find(inviteFromClientOid);
					inviteFromDb.removeInvite();
					daInvite.delete(inviteFromDb);
				} catch (DaOidNotFoundExc exc) {
					 throw create(DaInviteIdNotFoundExc.class, createTimeStamp(), inviteFromClientOid);
				} catch (Exception ex) {
					throw create(DeleteInviteFailure.class, ex, createTimeStamp(), inviteFromClientOid);
				}
				return null;
			}
		}.execute();
	}
	
	/**
	 * Occured at "{0}". Could not delete invite with id "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class DeleteInviteFailure extends multex.Failure {}
	
	/**
	 * Occured at "{0}". No invite with id "{1}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class DaInviteIdNotFoundExc extends multex.Exc {}

	private String createTimeStamp() {
		return new Date(System.currentTimeMillis()).toString();
	}
	
}
