package de.bht.comanche.rest;

import static com.jayway.restassured.RestAssured.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import de.bht.comanche.logic.LgUser;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReUserServiceTest {

	static long oid = 0;
	static String testUsername = "Muster10";

	@Test
	public void test1registerUser() {
		LgUser testUser = new LgUser();
		testUser.setName(testUsername);
		testUser.setEmail("muster@test.com");
		testUser.setPassword("password");
		testUser.setTel("123456789");

		Response response = expect().statusCode(200).given().body(testUser)
				.contentType("application/json").when()
				.post("/rest/user/register");
		
		JsonPath jsonPath = response.getBody().jsonPath();
		oid = jsonPath.getLong("data[0].oid");
		}

	@Test
	public void test2loginUser() {

		LgUser testUser = new LgUser();
		testUser.setName(testUsername);
		testUser.setPassword("password");

		Response response = expect().statusCode(200).given().body(testUser)
				.contentType("application/json").when()
				.post("/rest/user/login");

		JsonPath jsonPath = response.getBody().jsonPath();
		assertEquals(testUsername, jsonPath.get("data[0].name"));
		assertEquals("password", jsonPath.get("data[0].password"));
		assertEquals("muster@test.com", jsonPath.get("data[0].email"));
		assertEquals("123456789", jsonPath.get("data[0].tel"));
	}

	@Test
	public void test3updateUser() {

		LgUser testUser = new LgUser();
		testUser.setOid(oid);
		testUser.setName("UpdatedMuster");
		testUser.setPassword("updatedPassword");
		testUser.setEmail("updated@test.com");
		testUser.setTel("1010101010");

		Response response = expect().statusCode(200).given().body(testUser)
				.contentType("application/json").when()
				.post("/rest/user/update");

		JsonPath jsonPath = response.getBody().jsonPath();
		assertEquals("UpdatedMuster", jsonPath.get("data[0].name"));
		assertEquals("updatedPassword", jsonPath.get("data[0].password"));
		assertEquals("updated@test.com", jsonPath.get("data[0].email"));
		assertEquals("1010101010", jsonPath.get("data[0].tel"));

	}

	@Test
	public void test4DeletedUser() {

		LgUser testUser = new LgUser();
		testUser.setOid(oid);

		Response response = expect().statusCode(200).given().body(testUser)
				.contentType("application/json").when()
				.delete("/rest/user/delete");

		JsonPath jsonPath = response.getBody().jsonPath();
		assertNull(jsonPath.get("data[0]"));

	}

}
