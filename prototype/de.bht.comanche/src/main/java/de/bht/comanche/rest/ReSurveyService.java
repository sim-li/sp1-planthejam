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
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTransaction;


@Path("/surveys/")
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
					result = startSession().getSurvey(oid);//TODO change and implement the method
				} catch (Exception ex) {
					throw create(TempFailure.class, ex);//TODO change and implement the failure
				}
				return result;
			}
		}.getResult();
	}
	
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
					result = startSession().getSurveys();//TODO change and implement the method
				} catch (Exception ex) {
					throw create(TempFailure.class, ex);//TODO change and implement the failure
				}
				return result;
			}
		}.getResult();
	}
	
	@Path("/")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey save(final LgSurvey survey, @Context final HttpServletRequest request) {
		return new LgTransaction<LgSurvey>(request) {
			public LgSurvey execute() throws Exception {
				final LgSurvey result;
				try {
					result = startSession().saveSurvey(survey);//TODO change and implement the method
				} catch (Exception ex) {
					throw create(TempFailure.class, ex);//TODO change and implement the failure
				}
				return result;
			}
		}.getResult();
	}
	
	@Path("/{oid}")
	@PUT
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey update(@PathParam("oid") final long oid, final LgSurvey survey, @Context final HttpServletRequest request) {
		return new LgTransaction<LgSurvey>(request) {
			public LgSurvey execute() throws Exception {
				final LgSurvey result;
				try {
					result = startSession().updateSurvey(oid, survey);//TODO change and implement the method
				} catch (Exception ex) {
					throw create(TempFailure.class, ex);//TODO change and implement the failure
				}
				return result;
			}
		}.getResult();
	}
	
	@Path("/{oid}")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgSurvey delete(@PathParam("oid") final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction <LgSurvey>(request) {
			public LgSurvey execute() throws Exception {
				try {
					startSession().deleteSurvey(oid);//TODO change and implement the method
				} catch (Exception ex) {
					throw create(TempFailure.class, ex);//TODO change and implement the failure
				}
				return null;
			}
		}.getResult();
	}
	
	/**
	 * Temp failure
	 */
	@SuppressWarnings("serial")
	public static final class TempFailure extends multex.Failure {}
	

}
