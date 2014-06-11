package de.bht.comanche.server;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;


public class UserTest {
	
	@Test
	public void testGetSingleUser() {
	  expect().statusCode(200).body(
	      "email", equalTo("test@hascode.com"),
	      "firstName", equalTo("Tim"),
	      "lastName", equalTo("Testerman"),
	      "id", equalTo(1)).
	    when().
	    get("service/single-user");
	}
}
