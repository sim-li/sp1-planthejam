package de.bht.comanche.rest;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import de.bht.comanche.logic.LgMember;
import de.bht.comanche.logic.LgGroup;
import de.bht.comanche.logic.LgTransaction;

@Path("/group/")
public class ReGroupService extends RestService {
	
	@Path("getGroups")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public List<LgGroup> get(@Context final HttpServletRequest request) {
		return new LgTransaction<List<LgGroup>>(request) {
			@Override
			public List<LgGroup> execute() throws Exception {
				List<LgGroup> groups = null;
				try {
					groups = startSession().getGroups();
				} catch (Exception e) {
//					create new Multex
				}
				return groups;
			}
		}.getResult();
	}
	
	/**
	 * Could not get group with id "{1}" and user id "{2}"
	 */
	@SuppressWarnings("serial")
	public static final class LgGroupNotFoundExc extends multex.Failure {}
	
	
	@Path("save")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgGroup save(final LgGroup group, @Context final HttpServletRequest request) {
		return new LgTransaction<LgGroup>(request) {
			@Override
			public LgGroup execute() throws Exception {
				return startSession().save(group);
			}
		}.getResult();
	}
	
	@Path("saveMember")
	@POST
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgMember saveMember(final LgGroup group, @Context final HttpServletRequest request) {
		return new LgTransaction<LgMember>(request) {
			@Override
			public LgMember execute() throws Exception {
				return startSession().save(new LgMember().setUser(getSession().getUser(), group));
			}
		}.getResult();
	}
	
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgGroup delete(final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction<LgGroup>(request) {
			@Override
			public LgGroup execute() throws multex.Exc{
				startSession().deleteGroup(oid);
				return null;
			}
		}.getResult();
	}
}
