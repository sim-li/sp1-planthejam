package de.bht.comanche.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.bht.comanche.exceptions.DaInviteNotFoundException;
import de.bht.comanche.exceptions.LgNoUserWithThisIdException;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaInvite;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaUser;

//TODO not ready for multex ------> ???

@Path("/invite/")
public class ReInviteService extends ReService {
	
	public ReInviteService() {
		super();
	}
	
	@POST
	@Path("getInvites")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<List<LgInvite>> getInvites(final long userFromClientOid) {
		final DaUser daUser0 = factory.getDaUser();
		return new LgTransaction<List<LgInvite>>(daUser0.getPool()) {
			public List<LgInvite> executeWithThrows() throws Exception {
				List<LgInvite> invites = null;
				try {
					LgUser lgUser = daUser0.find(userFromClientOid);
					invites = lgUser.getInvites();
				} catch (DaOidNotFoundExc oid) {
					throw new LgNoUserWithThisIdException();
				}
				return invites;
			}
		}.execute();
	}

	@Path("save")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgInvite> saveInvite(final LgInvite newInviteFromClient) {
		final DaInvite daInvite = factory.getDaInvite();
		final DaUser daUser = factory.getDaUser();
		daUser.setPool(daInvite.getPool());
		return new LgTransaction<LgInvite>(daInvite.getPool()) {
			public LgInvite executeWithThrows() throws Exception {
				System.out.println("ID: " + newInviteFromClient.getOid());
				LgInvite invite = daInvite.find(newInviteFromClient.getOid());
				if (invite != null) {
					daInvite.update(newInviteFromClient);
				} else {
					newInviteFromClient.setUser(daUser.find(newInviteFromClient.getUser().getOid()));
					daInvite.save(newInviteFromClient);
				} 
				return newInviteFromClient;
			}
		}.execute();
	}

	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgInvite> deleteUser(final long inviteFromClientOid) {
		final DaInvite daInvite = factory.getDaInvite();
		return new LgTransaction<LgInvite>(daInvite.getPool()) {
			public LgInvite executeWithThrows() throws Exception {
				LgInvite inviteFromDb = null;
				try {
					inviteFromDb = daInvite.find(inviteFromClientOid);
				} catch (NotFoundException exc) {
					 throw new DaInviteNotFoundException();
				}
				inviteFromDb.removeInvite();
				daInvite.delete(inviteFromDb);
				return null;
			}
		}.execute();
	}

}
