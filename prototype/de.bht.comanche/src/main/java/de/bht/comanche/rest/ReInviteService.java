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
import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;

@Path("/invite/")
public class ReInviteService extends RestService {
	@POST
	@Path("getInvites")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgInvite> getInvites(@Context final HttpServletRequest request) {
		final LgSession session = new LgSession();
		return new LgTransaction<List<LgInvite>>(session) {
			@Override
			public List<LgInvite> execute() throws multex.Exc {
				return session.startFor(RestService.getUserName(request))
					.getUser()
						.getInvites();
			}
		}.getResult();
	}
	
//	@Path("save")
//	@POST
//	@Consumes("application/json")
//	@Produces({ "application/json" })
//	public LgInvite saveInvite(final LgInvite receivedInvite) {
//		final DaInvite daInvite = factory.getDaInvite();
//		final DaUser daUser = factory.getDaUser();
//		final DaSurvey daSurvey = factory.getDaSurvey();
//		daUser.setPool(daInvite.getPool());
//		daSurvey.setPool(daInvite.getPool());
//		return new LgTransaction<LgInvite>(daInvite.getPool()) {
//			public LgInvite executeWithThrows() throws multex.Exc {
//				LgInvite existingInvite = daInvite.find(receivedInvite.getOid());
//			    if (existingInvite == null) {
//			    	daSurvey.save(receivedInvite.getSurvey());
//			    	daInvite.save(receivedInvite);
//			    	
//			    } else {
//			    	LgSurvey existingSurvey = receivedInvite.getSurvey();
//			    	LgUser existingUser = receivedInvite.getUser();
//			        existingInvite.setSurvey(existingSurvey);
//			        existingSurvey.addInvite(existingInvite); 
//			        existingInvite.setUser(existingUser);
//			        existingUser.addInvite(existingInvite);
//			    }
////			    
////			    
////				} catch (Exception ex) {
////					throw create(DaInviteNotSavedExc.class, ex, createTimeStamp(), newInviteFromClient.getOid(), 
////							newInviteFromClient.getUser().getOid());
////				}
//				return existingInvite;
//			}
//		}.execute();
//	}
//	//-------------------------------------multex ready---------
//	@Path("delete")
//	@DELETE
//	@Consumes("application/json")
//	@Produces({ "application/json" })
//	public LgInvite deleteUser(final long inviteFromClientOid) {
//		final DaInvite daInvite = factory.getDaInvite();
//		return new LgTransaction<LgInvite>(daInvite.getPool()) {
//			public LgInvite executeWithThrows() throws multex.Exc {
//				LgInvite inviteFromDb = null;
//				try {
//					inviteFromDb = daInvite.find(inviteFromClientOid);
//					inviteFromDb.removeInvite();
//					daInvite.delete(inviteFromDb);
//				} catch (DaOidNotFoundExc exc) {
//					 throw create(DaInviteIdNotFoundExc.class, createTimeStamp(), inviteFromClientOid);
//				} catch (Exception ex) {
//					throw create(DaInviteNotDeletedExc.class, ex, createTimeStamp(), inviteFromClientOid);
//				}
//				return null;
//			}
//		}.execute();
//	}
//	
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
