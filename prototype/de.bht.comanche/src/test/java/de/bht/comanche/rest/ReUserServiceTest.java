package de.bht.comanche.rest;

import static com.jayway.restassured.RestAssured.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import de.bht.comanche.logic.LgUser;

@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReUserServiceTest {

	LgUser alice;
	
	@Test
	public void test1LoginUser() {
		alice = new LgUser()
		.setName("Alice")
		.setPassword("testtest");

		expect().statusCode(204).given().body(alice)
				.contentType("application/json").when()
				.post("/rest/user/login");
		}
	
	@Test
	public void test1GetUser() {
		RequestSpecification requestSpec = new RequestSpecBuilder().addCookie("NAME",
                "Alice").build();

		 given().
		         spec(requestSpec).
		 expect().
		         body("x.y.z", equalTo("something")).
		 when().
		        get("/rest/user/");
	}
	
	
//	@Test
//	public void test1registerUser() {
//		LgUser testUser = new LgUser();
//		testUser.setName("ALice");
//		testUser.setEmail("muster@test.com");
//		testUser.setPassword("password");
//		testUser.setTel("123456789");
//
//		Response response = expect().statusCode(200).given().body(testUser)
//				.contentType("application/json").when()
//				.post("/rest/user/register");
//		
//		JsonPath jsonPath = response.getBody().jsonPath();
////		oid = jsonPath.getLong("data.oid");
//		}
//
//	@Test
//	public void test2loginUser() {
//
//		LgUser testUser = new LgUser();
//		testUser.setName(testUsername);
//		testUser.setPassword("password");
//
//		Response response = expect().statusCode(200).given().body(testUser)
//				.contentType("application/json").when()
//				.post("/rest/user/login");
//
//		JsonPath jsonPath = response.getBody().jsonPath();
//		assertEquals(testUsername, jsonPath.get("data.name"));
//		assertEquals("password", jsonPath.get("data.password"));
//		assertEquals("muster@test.com", jsonPath.get("data.email"));
//		assertEquals("123456789", jsonPath.get("data.tel"));
//	}
//
//	@Test
//	public void test3updateUser() {
//
//		LgUser testUser = new LgUser();
//		testUser.setName("UpdatedMuster");
//		testUser.setPassword("updatedPassword");
//		testUser.setEmail("updated@test.com");
//		testUser.setTel("1010101010");
//
//		Response response = expect().statusCode(200).given().body(testUser)
//				.contentType("application/json").when()
//				.post("/rest/user/update");
//
//		JsonPath jsonPath = response.getBody().jsonPath();
//		assertEquals("UpdatedMuster", jsonPath.get("data.name"));
//		assertEquals("updatedPassword", jsonPath.get("data.password"));
//		assertEquals("updated@test.com", jsonPath.get("data.email"));
//		assertEquals("1010101010", jsonPath.get("data.tel"));
//
//	}
//
//	@Test
//	public void test4DeletedUser() {
//
//		LgUser testUser = new LgUser();
//
//		Response response = expect().statusCode(200).given().body(testUser)
//				.contentType("application/json").when()
//				.delete("/rest/user/delete");
//
//		JsonPath jsonPath = response.getBody().jsonPath();
//		assertNull(jsonPath.get("data"));
//
//	}

}
