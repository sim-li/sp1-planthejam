package de.bht.comanche.rest;

import static multex.MultexUtil.create;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import multex.Exc;
import de.bht.comanche.logic.LgGroup;
import de.bht.comanche.logic.LgTransaction;

@Path("/member/")
public class ReMemberService extends RestService {
	
	// TODO -- we need to be able to delete members
	
	@Path("delete")
	@DELETE
	@Consumes("application/json")
	@Produces({ "application/json" })
	public LgGroup delete(final long oid, @Context final HttpServletRequest request) {
		return new LgTransaction<LgGroup>(request) {
			@Override
			public LgGroup execute() throws Exception {
				
				throw create(Exc.class, "TODO delete member not implented");
				
//				try {
////					startSession().deleteMember(oid)
//					
//				} catch (Exception ex) {
//					throw create(RestDeleteMemberFailure.class, ex, oid, getSession().getUser().getName() );
//					
//				}
//				return null;
			}
		}.getResult();
	}
	
	/**
	 * Could not delete member with oid "{0}" for user "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class RestDeleteMemberFailure extends multex.Failure {}
	
}
