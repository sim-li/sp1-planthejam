package de.bht.comanche.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import static multex.MultexUtil.create;
import de.bht.comanche.logic.LgGroup;
import de.bht.comanche.logic.LgMember;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;

@Path("/group/")
public class ReGroupService extends RestService {
	
	@Path("getGroups")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgGroup> get(@Context final HttpServletRequest request) {
		return new LgTransaction<List<LgGroup>>(request) {
			@Override
			public List<LgGroup> execute() throws multex.Failure {
				List<LgGroup> result = null;
				try {
					result = startSession().getGroups();
				} catch (Exception ex) {
					throw create(RestGetGroupsFailure.class, ex, getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}
	
	/**
	 * Could not get groups for user "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class RestGetGroupsFailure extends multex.Failure {}
	
	
	@Path("save")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgGroup save(final LgGroup group, @Context final HttpServletRequest request) {
		return new LgTransaction<LgGroup>(request) {
			@Override
			public LgGroup execute() throws multex.Failure {
				LgGroup result = null;
				try {
					result = startSession().save(group);
				} catch (Exception ex) {
					throw create(RestSaveGroupFailure.class, ex, group.getName(), group.getOid(), getSession().getUser().getName());
				}
				return result;
			}
		}.getResult();
	}
	
	/**
	 * Could not save group "{0}" with oid "{1}" for user "{2}"
	 */
	@SuppressWarnings("serial")
	public static final class  RestSaveGroupFailure extends multex.Failure {}
	
	
//	@Path("saveMember")
//	@POST
//	@Consumes("application/json")
//	@Produces({ "application/json" })
//	public LgMember saveMember(final LgUser user, final LgGroup group, @Context final HttpServletRequest request) {
//		return new LgTransaction<LgMember>(request) {
//			@Override
//			public LgMember execute() throws multex.Failure {
//				LgMember result = null;
//				try {
//					result = startSession().save(new LgMember().setUser(user).setGroup(group));
//				} catch (Exception ex) {
//					throw create(RestSaveMemberFailure.class, ex, group.getName(), group.getOid(), getSession().getUser().getName() );
//				}
//				return result;
//			}
//		}.getResult();
//	}
//	
//	/**
//	 * Could not save member in group "{0}" with oid "{1}" for user "{2}"
//	 */
//	@SuppressWarnings("serial")
//	public static final class RestSaveMemberFailure extends multex.Failure {}
//	
	
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgGroup delete(final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction<LgGroup>(request) {
			@Override
			public LgGroup execute() throws multex.Exc{
				try {
					startSession().deleteGroup(oid);
				} catch (Exception ex) {
					throw create(RestDeleteGroupFailure.class, ex, oid, getSession().getUser().getName() );
					
				}
				return null;
			}
		}.getResult();
	}
	
	/**
	 * Could not delete group with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestDeleteGroupFailure extends multex.Failure {}
	
}
