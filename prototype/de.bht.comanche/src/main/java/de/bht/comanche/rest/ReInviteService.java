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
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaInvite;
import de.bht.comanche.persistence.DaPoolImpl.DaNotFoundExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaUser;

//TODO not ready for multex ------> ???

@Path("/invite/")
public class ReInviteService extends ReService {
	
	public ReInviteService() {
		super();
	}
	
	//-------------------------------------multex ready---------
	@POST
	@Path("getInvites")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<List<LgInvite>> getInvites(final long userFromClientOid) {
		final DaUser daUser0 = factory.getDaUser();
		return new LgTransaction<List<LgInvite>>(daUser0.getPool()) {
			public List<LgInvite> executeWithThrows() throws multex.Exc {
				List<LgInvite> invites = null;
				try {
					LgUser lgUser = daUser0.find(userFromClientOid);
					invites = lgUser.getInvites();
				} catch (DaOidNotFoundExc oid) {
					throw create(LgNoUserWithThisIdExc.class, createTimeStamp(), userFromClientOid);
				}
				
				return invites;
			}
		}.execute();
	}

	//-------------------------------------multex ready---------
	@Path("save")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgInvite> saveInvite(final LgInvite newInviteFromClient) {
		final DaInvite daInvite = factory.getDaInvite();
		final DaUser daUser = factory.getDaUser();
		daUser.setPool(daInvite.getPool());
		return new LgTransaction<LgInvite>(daInvite.getPool()) {
			public LgInvite executeWithThrows() throws multex.Exc {
//				System.out.println("ID: " + newInviteFromClient.getOid());
				LgInvite invite;
				try{
					
					invite = daInvite.find(newInviteFromClient.getOid());
				if (invite != null) {
					daInvite.update(newInviteFromClient);
				} else {
					newInviteFromClient.setUser(daUser.find(newInviteFromClient.getUser().getOid()));
					daInvite.save(newInviteFromClient);
				} 
				
				} catch (Exception ex){
					throw create(DaInviteNotSavedExc.class, ex, createTimeStamp(), newInviteFromClient.getOid());
				}
				return newInviteFromClient;
			}
		}.execute();
	}

	//-------------------------------------multex ready---------
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgInvite> deleteUser(final long inviteFromClientOid) {
		final DaInvite daInvite = factory.getDaInvite();
		return new LgTransaction<LgInvite>(daInvite.getPool()) {
			public LgInvite executeWithThrows() throws multex.Exc {
				LgInvite inviteFromDb = null;
				try {
					inviteFromDb = daInvite.find(inviteFromClientOid);
					inviteFromDb.removeInvite();
					daInvite.delete(inviteFromDb);
				} catch (DaOidNotFoundExc exc) {
					 throw create(DaInviteIdNotFoundExc.class, createTimeStamp(), inviteFromClientOid);
				} catch (Exception ex) {
					throw create(DaInviteNotDeletedExc.class, ex, createTimeStamp(), inviteFromClientOid);
				}
				return null;
			}
		}.execute();
	}
	
	private String createTimeStamp() {
		return new Date(System.currentTimeMillis()).toString();
	}
	
	/**
	 * Occured at "{0}". Could not save invite with id "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class DaInviteNotSavedExc extends multex.Exc {}
	
	
	/**
	 * Occured at "{0}". Could not delete invite with id "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class DaInviteNotDeletedExc extends multex.Exc {}
	
	
	/**
	 * Occured at "{0}". No user with id "{1}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgNoUserWithThisIdExc extends multex.Exc {}
	
	/**
	 * Occured at "{0}". No invite with id "{1}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class DaInviteIdNotFoundExc extends multex.Exc {}
	
}
