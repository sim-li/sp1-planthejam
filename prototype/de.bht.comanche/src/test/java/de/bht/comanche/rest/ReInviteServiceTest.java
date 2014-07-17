package de.bht.comanche.rest;

import static com.jayway.restassured.RestAssured.expect;

import java.util.Date;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReInviteServiceTest {

	static long oid = 0;
	static String testSurveyname = "testSurveyname4";

	@Test
	public void test1saveInvite() {
		
		LgSurvey testSurvey = new LgSurvey();
		
		testSurvey.setName(testSurveyname);
		testSurvey.setDescription("TestDescription");
		testSurvey.setFrequencyDist(5);

		Response response = expect().statusCode(200).given().body(testSurvey)
				.contentType("application/json").when()
				.post("/rest/invite/save");
		
		JsonPath jsonPath = response.getBody().jsonPath();
		oid = jsonPath.getLong("data[0].oid");
	}

//	@Test
//	public void test2getSurvey() {
//
//		LgSurvey testSurvey = new LgSurvey();
//		testSurvey.setName(testSurveyname);
//
//		Response response = expect().statusCode(200).given().body(testSurvey)
//				.contentType("application/json").when()
//				.post("/rest/invite/getInvites");
//
//		JsonPath jsonPath = response.getBody().jsonPath();
//		assertEquals(testSurveyname, jsonPath.get("data[0].name"));
//		assertEquals("TestDescription", jsonPath.get("data[0].description"));
//		assertEquals(5, jsonPath.get("data[0].frequencyDist"));
//	}

////	@Test
//	public void test3updateSurvey() {
//
//		LgSurvey testSurvey = new LgSurvey();
//		testSurvey.setName(testSurveyname);
//		testSurvey.setDescription("UpdatedTestDescription");
//		testSurvey.setFrequencyDist(10);
//
//		Response response = expect().statusCode(200).given().body(testSurvey)
//				.contentType("application/json").when()
//				.post("/rest/invate/save");
//
//		JsonPath jsonPath = response.getBody().jsonPath();
//		assertEquals(testSurveyname, jsonPath.get("data[0].name"));
//		assertEquals("UpdatedTestDescription", jsonPath.get("data[0].description"));
//		assertEquals(10, jsonPath.get("data[0].frequencyDist"));
//
//	}

////	@Test
//	public void test4DeletedSurvey() {
//
//		LgSurvey testSurvey = new LgSurvey();
//		testSurvey.setName(testSurveyname);
//
//		Response response = expect().statusCode(200).given().body(testSurvey)
//				.contentType("application/json").when()
//				.delete("/rest/invite/delete");
//
//		JsonPath jsonPath = response.getBody().jsonPath();
//		jsonPath.prettyPrint();
//		
//		assertNull(jsonPath.get("data[0]"));
//
//	}
}