package de.bht.comanche.rest;

import static multex.MultexUtil.create;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.rest.ReInviteService.RestGetInviteFailure;
import de.bht.comanche.rest.ReInviteService.RestGetInvitesFailure;
import de.bht.comanche.rest.ReInviteService.RestSaveInviteFailure;


@Path("/survey/")
public class ReSurveyService {
	
	@GET
	@Path("get/{oid}")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey get(@PathParam("oid") final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction<LgSurvey>(request) {
			@Override
			public LgSurvey execute() throws Exception {
				final LgSurvey result;
				try {
					result = startSession().getSurvey(oid);//TODO change the method
				} catch (Exception ex) {
//					throw create(RestGetInviteFailure.class, ex, oid, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}
	
	@GET
	@Path("get")
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgSurvey> get(@Context final HttpServletRequest request) {
		return new LgTransaction<List<LgSurvey>>(request) {
			@Override
			public List<LgSurvey> execute() throws Exception {
				final List<LgSurvey> result;
				try {
					result = startSession().getInvites();//TODO change the method
				} catch (Exception ex) {
//					throw create(RestGetInvitesFailure.class, ex, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}
	
	@Path("save")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey save(final LgSurvey survey, @Context final HttpServletRequest request) {
		return new LgTransaction<LgSurvey>(request) {
			public LgSurvey execute() throws Exception {
				final LgSurvey result;
				try {
					result = startSession().save(survey);//TODO change the method
				} catch (Exception ex) {
//					throw create(RestSaveInviteFailure.class, ex, invite.getOid(), getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}
	
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey delete(final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction <LgSurvey>(request) {
			public LgSurvey execute() throws Exception {
				try {
					startSession().deleteSurvey(oid);//TODO change the method
				} catch (Exception ex) {
//					throw create(RestDeleteInviteFailure.class, ex, oid, getSession().getUser().getName());
				}
				return null;
			}
		}.getResult();
	}

}
