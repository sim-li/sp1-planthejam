package de.bht.comanche.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaSurvey;
import de.bht.comanche.persistence.DaUser;
import de.bht.comanche.server.exceptions.logic.NoUserWithThisIdException;
import de.bht.comanche.server.exceptions.logic.SurveyWithThisNameExistsException;
import de.bht.comanche.server.exceptions.persistence.NotFoundException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

@Path("/invite/")
public class InviteService extends Service {
	public InviteService() {
		super();
	}

	@POST
	@Path("getInvites")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ResponseObject<LgInvite> getInvites(final LgUser userFromClient) {
		final DaUser daUser = factory.getDaUser();
		ResponseObject<LgInvite> response = new TransactionWithList<LgInvite>(
				daUser.getPool()) {
			public List<LgInvite> executeWithThrows() throws Exception {
				List<LgInvite> invites = null;
				try {
					LgUser lgUser = daUser.find(userFromClient.getOid());
					invites = lgUser.getInvites();
				} catch (OidNotFoundException oid) {
					throw new NoUserWithThisIdException();
				}
				return invites;
			}
		}.execute();
		if (response.hasError()) {
			throw new WebApplicationException(response.getResponseCode());
		}
		return response;
	}

	@Path("save")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ResponseObject saveSurvey(final LgSurvey newSurveyFromClient) {
		final DaSurvey daSurvey = factory.getDaSurvey();
		ResponseObject response = new Transaction<LgSurvey>(daSurvey.getPool()) {
			public LgSurvey executeWithThrows() throws Exception {
				List<LgSurvey> survey = daSurvey.findByName(newSurveyFromClient
						.getName());
				if (!survey.isEmpty()) {
					throw new SurveyWithThisNameExistsException();
				}
				daSurvey.save(newSurveyFromClient);
				return newSurveyFromClient;
			}
		}.execute();
		if (response.hasError()) {
			throw new WebApplicationException(response.getResponseCode());
		}
		return response;
	}

	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ResponseObject deleteUser(final LgSurvey surveyFromClient) {
		final DaSurvey daSurvey = factory.getDaSurvey();
		ResponseObject response = new Transaction<LgSurvey>(daSurvey.getPool()) {
			public LgSurvey executeWithThrows() throws Exception {

				System.out.println(surveyFromClient);

				LgSurvey surveyFromDb = null;
				try {
					surveyFromDb = (LgSurvey) daSurvey
							.findByName(surveyFromClient.getName());
				} catch (NotFoundException exc) {
					// throw new NoSurveyWithThisNameException();
				}
				daSurvey.delete(surveyFromDb);
				return null;
			}
		}.execute();

		if (response.hasError()) {
			throw new WebApplicationException(response.getResponseCode());
		}

		return response;
	}

}
