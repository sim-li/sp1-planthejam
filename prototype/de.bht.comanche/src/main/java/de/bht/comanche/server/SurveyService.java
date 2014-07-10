//package de.bht.comanche.server;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//
//import de.bht.comanche.logic.LgSurvey;
//import de.bht.comanche.logic.LgUser;
//import de.bht.comanche.persistence.DaFactory;
//import de.bht.comanche.persistence.DaSurvey;
//import de.bht.comanche.persistence.DaUser;
//import de.bht.comanche.persistence.JpaDaFactory;
//
//@Path("/survey/")
//public class SurveyService {
//	
//	@Path("/save")
//    @POST
//    @Consumes("application/json")
//    @Produces({"application/json"})
//    public ResponseObject registerUser(final LgSurvey newSurveyFromClient){
//   	 return new Transaction<LgSurvey>() {
//			public LgSurvey executeWithThrows() throws Exception {
//				DaFactory jpaDaFactory = new JpaDaFactory();
//				DaSurvey daSurvey = jpaDaFactory.getDaSurvey();
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
//
//}
