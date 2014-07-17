package de.bht.comanche.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import de.bht.comanche.exceptions.DaNotFoundException;
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.exceptions.LgNoUserWithThisIdException;
import de.bht.comanche.exceptions.LgSurveyWithThisNameExistsException;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgTransactionWithList;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaSurvey;
import de.bht.comanche.persistence.DaUser;

@Path("/invite/")
public class ReInviteService extends ReService {
	public ReInviteService() {
		super();
	}
	@POST
	@Path("getInvites")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public ReResponseObject<LgInvite> getInvites(final LgUser userFromClient) {
		final DaUser daUser0 = factory.getDaUser();
		ReResponseObject<LgInvite> response = new LgTransactionWithList<LgInvite>(daUser0.getPool()) {
			public List<LgInvite> executeWithThrows() throws Exception {
				List<LgInvite> invites = null;
				try {
					LgUser lgUser = daUser0.find(userFromClient.getOid());
					invites = lgUser.getInvites();
				} catch (DaOidNotFoundException oid) {
					throw new LgNoUserWithThisIdException();
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
	public ReResponseObject saveSurvey(final LgSurvey newSurveyFromClient) {
		final DaSurvey daSurvey = factory.getDaSurvey();
		ReResponseObject response = new LgTransaction<LgSurvey>(daSurvey.getPool()) {
			public LgSurvey executeWithThrows() throws Exception {
				List<LgSurvey> survey = daSurvey.findByName(newSurveyFromClient
						.getName());
				if (!survey.isEmpty()) {
					throw new LgSurveyWithThisNameExistsException();
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
	public ReResponseObject deleteUser(final LgSurvey surveyFromClient) {
		final DaSurvey daSurvey = factory.getDaSurvey();
		ReResponseObject response = new LgTransaction<LgSurvey>(daSurvey.getPool()) {
			public LgSurvey executeWithThrows() throws Exception {

				System.out.println(surveyFromClient);

				LgSurvey surveyFromDb = null;
				try {
					surveyFromDb = (LgSurvey) daSurvey.findByName(surveyFromClient.getName());
				} catch (DaNotFoundException exc) {
//					 throw new NoSurveyWithThisNameException();
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
