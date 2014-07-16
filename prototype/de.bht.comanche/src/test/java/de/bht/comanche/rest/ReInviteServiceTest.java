package de.bht.comanche.rest;

import static com.jayway.restassured.RestAssured.expect;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import de.bht.comanche.logic.LgUser;
@Ignore
public class ReInviteServiceTest {

	@Test
	public void testHelloWorld(){
		final String body = get("/rest/user/hello").asString();
		assertThat(body, equalTo("Hello World!"));
	}
	
//	@Test
//    public void getInvites() {
//		LgUser testUser = new LgUser();
//		testUser.setOid(1);
//		Response response = expect().statusCode(200).given().body(testUser).contentType("application/json")
//		.when().post("/rest/invite/getInvites");
//		
//		response.prettyPrint();
//		
//	}
}