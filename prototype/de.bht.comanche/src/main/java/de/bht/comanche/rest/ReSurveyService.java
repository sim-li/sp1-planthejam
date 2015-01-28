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

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.rest.ReInviteService.RestGetInviteFailure;
import de.bht.comanche.logic.LgUser;


@Path("/surveys")
public class ReSurveyService {

	@GET
	@Path("/{oid}")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey get(@PathParam("oid") final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction<LgSurvey>(request) {
			@Override
			public LgSurvey execute() throws Exception {
				final LgSurvey result;
				try {
					result = startSession().getSurvey(oid);
				} catch (Exception ex) {
					throw create(RestGetSurveyFailure.class, ex, oid, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not found any survey with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestGetSurveyFailure extends multex.Failure {}

	@GET
	@Path("/{oid}/invites")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgInvite> getInvitesFor(@PathParam("oid") final long surveyOid, @Context final HttpServletRequest request) {
		return new LgTransaction<List<LgInvite>>(request) {
			@Override
			public List<LgInvite> execute() throws Exception {
				final List<LgInvite> result;
				try {
					result = startSession().getInvitesForSurvey(surveyOid);
				} catch (Exception ex) {
					throw create(RestGetInvitesForSurveyFailure.class, ex, surveyOid, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not found any invites for survey with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestGetInvitesForSurveyFailure extends multex.Failure {}

	@GET
	@Path("/")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgSurvey> get(@Context final HttpServletRequest request) {
		return new LgTransaction<List<LgSurvey>>(request) {
			@Override
			public List<LgSurvey> execute() throws Exception {
				final List<LgSurvey> result;
				try {
					final LgUser user = startSession();
					user.evaluateAllSurveys();
					result = user.getSurveys();
				} catch (Exception ex) {
					throw create(RestGetAllSurveysFailure.class, ex, getSession().getUser().getName());
					}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not found any survey for user "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class RestGetAllSurveysFailure extends multex.Failure {}

	@Path("/")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey save(final LgSurvey survey, @Context final HttpServletRequest request) {
		return new LgTransaction<LgSurvey>(request) {
			public LgSurvey execute() throws Exception {
				final LgSurvey result;
				try {
					result = startSession().saveSurvey(survey);
				} catch (Exception ex) {
					throw create(RestSaveSurveyFailure.class, ex, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not save survey for user "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class RestSaveSurveyFailure extends multex.Failure {}


	@Path("/{oid}")
	@PUT
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey update(@PathParam("oid") final long oid, final LgSurvey survey, @Context final HttpServletRequest request) {
		return new LgTransaction<LgSurvey>(request) {
			public LgSurvey execute() throws Exception {
				final LgSurvey result;
				try {
					result = startSession().updateSurvey(survey);
				} catch (Exception ex) {
					throw create(RestUpdateSurveyFailure.class, ex, oid, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}

	/**
	 * Could not update survey with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestUpdateSurveyFailure extends multex.Failure {}


	@Path("/{oid}")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey delete(@PathParam("oid") final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction <LgSurvey>(request) {
			public LgSurvey execute() throws Exception {
				try {
					startSession().deleteSurvey(oid);
				} catch (Exception ex) {
					throw create(RestDeleteSurveyFailure.class, ex, oid, getSession().getUser().getName());
				}
				return null;
			}
		}.getResult();
	}

	/**
	 * Could not delete survey with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestDeleteSurveyFailure extends multex.Failure {}

	@Path("/{oid}/notifyParticipants")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey notify(@PathParam("oid") final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction <LgSurvey>(request) {
			public LgSurvey execute() throws Exception {
				try {
					
//					startSession().notifyParticipants(oid);
					
				} catch (Exception ex) {
					throw create(RestNotifyParticipantsFailure.class, ex, oid, getSession().getUser().getName());
				}
				return null;
			}
		}.getResult();
	}

	/**
	 * Could not save messages for survey with oid "{0}" and user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestNotifyParticipantsFailure extends multex.Failure {}

}
