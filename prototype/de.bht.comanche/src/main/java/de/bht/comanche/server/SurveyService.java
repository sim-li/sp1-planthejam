//package de.bht.comanche.server;
//
//import java.util.List;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
//
//import de.bht.comanche.logic.LgSurvey;
//import de.bht.comanche.logic.LgUser;
//import de.bht.comanche.persistence.DaSurvey;
//import de.bht.comanche.persistence.DaUser;
//import de.bht.comanche.server.exceptions.logic.NoUserWithThisNameException;
//
//@Path("/survey/")
//public class SurveyService extends Service{
//	public SurveyService() {
//		super();
//	}
//	
//	@Path("/update")
//    @POST
//    @Consumes("application/json")
//    @Produces({"application/json"})
//    public ResponseObject updateUser(final LgUser updateUserFromClient){
//   	final DaUser daUser = factory.getDaUser();
// 		ResponseObject response = new Transaction<LgUser>(daUser.getPool()) {
// 			public LgUser executeWithThrows() throws Exception {
// 				List<LgUser> users = daUser.findByName(updateUserFromClient.getName());
//				if (users.isEmpty()) {
//					throw new NoUserWithThisNameException();
//				}
//				LgUser saveUsertoDb = users.get(0);
//				saveUsertoDb.updateWith(updateUserFromClient);
//   			daUser.save(saveUsertoDb);
//   			return saveUsertoDb;
//   		 }
//   	 }.execute();
//
//   	 if (response.hasError()) {
//  			throw new WebApplicationException(response.getResponseCode());
//  		}
//     	 
//     	 return response;
//    }
	
//	@Path("/save")
//    @POST
//    @Consumes("application/json")
//    @Produces({"application/json"})
//    public ResponseObject registerUser(final LgSurvey newSurveyFromClient){
//		final DaUser daUser = factory.getDaUser();
//		final DaSurvey daSurvey = factory.getDaSurvey();
//		ResponseObject response = new Transaction<LgSurvey>(LgSurvey.getPool()) {
// 			public LgUser executeWithThrows() throws Exception {
// 				List<LgSurvey> users = LgSurvey.findByName(updateUserFromClient.getName());
//				//throws Exc if name not exist - need boolin
//				LgSurvey surveyFromDb = daSurvey.findByName(newSurveyFromClient.getName()).iterator().next(); 
////				if(surveyFromDb){
////					if not exist -> save
////				}
//				
//				//save new Survey to DbSurvey or LgUser?
//				LgSurvey newSurveySaveToDb = new LgSurvey(); 
//				newSurveySaveToDb.setName(newSurveyFromClient.getName());
//				newSurveySaveToDb.setDescription(newSurveyFromClient.getDescription());
//				return newSurveySaveToDb;
//			}
//   	 }.execute();
//	}
//}
