package de.bht.comanche.server;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Ignore;

import de.bht.comanche.logic.LgSurvey;
@Ignore
public class SurveyServiceTest {
//	@Test
    public void saveSurvey() {
	
		LgSurvey testSurvey = new LgSurvey();
		testSurvey.setName("TestSurvey");
		testSurvey.setDescription("TestDescription");
		testSurvey.setFrequencyDist(5);
    	
		given().contentType("application/json")
		       .body(testSurvey)
		       .expect().statusCode(200)
		       .when().post("/survey/register");

    	}
	
}

