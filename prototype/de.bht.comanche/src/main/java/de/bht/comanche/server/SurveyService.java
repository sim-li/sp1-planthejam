package de.bht.comanche.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.persistence.DaSurvey;
import de.bht.comanche.server.exceptions.logic.NoSurveyWithThisNameException;
import de.bht.comanche.server.exceptions.logic.SurveyWithThisNameExistsException;
import de.bht.comanche.server.exceptions.persistence.NotFoundException;


@Path("/survey/")
public class SurveyService extends Service{
	public SurveyService() {
		super();
	}

	@Path("save")
    @POST
    @Consumes("application/json")
    @Produces({"application/json"})
	
    public ResponseObject saveSurvey(final LgSurvey newSurveyFromClient){
		final DaSurvey daSurvey = factory.getDaSurvey();
		ResponseObject response = new Transaction<LgSurvey>(daSurvey.getPool()) {
 			public LgSurvey executeWithThrows() throws Exception {
 				List<LgSurvey> survey = daSurvey.findByName(newSurveyFromClient.getName());
 				if (!survey.isEmpty()) {
					throw new SurveyWithThisNameExistsException();
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
    @Produces({"application/json"})
    public ResponseObject deleteUser(final LgSurvey surveyFromClient){
   	final DaSurvey daSurvey = factory.getDaSurvey();
 		ResponseObject response = new Transaction<LgSurvey>(daSurvey.getPool()) {
 			public LgSurvey executeWithThrows() throws Exception {
 				
 				System.out.println(surveyFromClient);
 				
 				LgSurvey surveyFromDb = null;
 				try{
					surveyFromDb = (LgSurvey) daSurvey.findByName(surveyFromClient.getName());
 				} catch (NotFoundException exc) {
 					throw new NoSurveyWithThisNameException();
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
