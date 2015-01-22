package de.bht.comanche.rest;

import static com.jayway.restassured.RestAssured.expect;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import de.bht.comanche.logic.LgUser;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReUserWithCollectionsTest {
	static long oid = 0;
	static String testSurveyname = "testSurveyname4";

	@Before
	public void setupLoginUser() {
		loginAlice();
	}

	private void loginAlice() {
		final LgUser user = new LgUser().setName("Alice").setPassword("testtest");
		expect().statusCode(204)
			.given()
				.body(user)
				.contentType("application/json")
			.when()
				.post("/rest/user/login");
	}
	
	@Test
	public void getUser() {

//	Response response = expect().statusCode(200).given().body(testSurvey)
//			.contentType("application/json").when()
//			.post("/rest/invite/getInvites");

	final Response response = expect().statusCode(200)
			.when()
				.get("/rest/user/");
	JsonPath jsonPath = response.getBody().jsonPath();
	assertEquals("Rest sends back user name of Alice", "Alice", jsonPath.get("data[0].name"));
	assertEquals("Rest sends back password of Alice", "testtest", jsonPath.get("data[0].password"));
	}
	
	@Test
	public void getAllUsers() {
		
	}
}