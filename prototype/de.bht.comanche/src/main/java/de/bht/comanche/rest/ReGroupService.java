//package de.bht.comanche.rest;
//
//import static multex.MultexUtil.create;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//
//import de.bht.comanche.logic.LgInvite;
//import de.bht.comanche.logic.LgSurvey;
//import de.bht.comanche.logic.LgTransaction;
//import de.bht.comanche.logic.LgUser;
//import de.bht.comanche.persistence.DaInvite;
//import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
//import de.bht.comanche.persistence.DaSurvey;
//import de.bht.comanche.persistence.DaUser;
//
//@Path("/group/")
//public class ReGroupService extends RestService {
//
//  // TODO should be changed to GET, because no data is sent from calling client
//	@Path("getGroups")
//	@POST
//	@Consumes("application/json")
//	@Produces({ "application/json" })
//	public List<LgInvite> get(@Context final HttpServletRequest request) {
//		return new LgTransaction<List<LgGroup>>(request) {
//			@Override
//			public List<LgGroup> execute() throws Exception {
//				return startSession().getGroups();
//			}
//		}.getResult();
//	}
//	
//	@Path("save")
//	@POST
//	@Consumes("application/json")
//	@Produces({ "application/json" })
//	public LgInvite save(final LgGroup group, @Context final HttpServletRequest request) {
//		return new LgTransaction<List<LgGroup>>(request) {
//			@Override
//			public List<LgGroup> execute() throws Exception {
//				return startSession().save(group);
//			}
//		}.getResult();
//	}
//	
//	@Path("delete")
//	@DELETE
//	@Consumes("application/json")
//	@Produces({ "application/json" })
//	public LgInvite delete(final long oid, @Context final HttpServletRequest request) {
//		return new LgTransaction<List<LgGroup>>(request) {
//			@Override
//			public List<LgGroup> execute() throws Exception {
//				startSession().deleteGroup(oid);
//				return null;
//			}
//		}.getResult();
//	}
//	
////	/* TODO
////	 * - move to ReUserService
////	 * - implement DaUser.selectAllUsersWhereNameIsLike(String searchString)
////	 * - provide access to selectAllUsersWhereNameIsLike from session
////	 */
////	@Path("findUsers")
////	@DELETE
////	@Consumes("application/json")
////	@Produces({ "application/json" })
////	public LgInvite findUsers(final String searchString, @Context final HttpServletRequest request) {
////		return new LgTransaction<List<LgUser>>(request) {
////			@Override
////			public List<LgUser> execute() throws Exception {
////				return startSession().selectAllUsersWhereNameIsLike(searchString);
////			}
////		}.getResult();
////	}
//	
//}
