package de.bht.comanche.server;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import de.bht.comanche.logic.LgUser;

public class UserServiceTest {

	@Test
	public void testHelloWorld(){
		final String body = get("/rest/user/hello").asString();
		assertThat(body, equalTo("Hello World!"));
	}
	
	@Test
    public void registerUser() {
	
		LgUser testUser = new LgUser();
		testUser.setName("John");
		testUser.setEmail("test@test.com");
		testUser.setPassword("password");
		testUser.setTel("123456789");
    	
		Response response = expect().statusCode(200).given().body(testUser).contentType("application/json")
		.when().post("/rest/user/register");
		
		
//		response.prettyPrint();
//		response.getBody().jsonPath();
		
		JsonPath jsonPath = response.getBody().jsonPath();
//		int user_id = jsonPath.getInt("user_id");
		
		jsonPath.prettyPrint();
		
		
//		String responseBody = response.getBody().asString();
//		JsonPath jsonPath = new JsonPath(responseBody);
//		long user_oid = jsonPath.getLong("oid");
		
//		System.out.println("user_oid" + user_oid + "++++++++++++++++++++++++++++");
		
//		getUser(user_oid);
		
//		given().contentType("application/json")
//		.body(testUser)
//		.expect().statusCode(200)
//		.when().post("/rest/user/register");
		
//		ResponseObject ro = new ResponseObject();
//		
//		given().request().body(ro);
//		System.out.println(ro.toString());
		
	}
	
//	@Test
    public void getUser(long user_oid) {
		
		
//		.when().post("/rest/user/getUser");
		
//		given().request().body(new ResponseObject());
//		
//		
//		ResponseObject ro = new ResponseObject();
//		
//		
//		 String json = get("/service/persons/json").asString();
//		  JsonPath jp = new JsonPath(json);
//		  jp.setRoot("person");
//		  Map person = jp.get("find {e -> e.email =~ /test@/}");
//		  assertEquals("test@hascode.com", person.get("email"));
//		  assertEquals("Tim", person.get("firstName"));
//		  assertEquals("Testerman", person.get("lastName"));
//		

    	}
    
//    @Test
//	public void getUser() {
//	   expect().statusCode(200).body(
//	       "success", equalTo(true),
//	       "serverMessages", equalTo("success"),
//	       "data.name", equalTo("John"),
//	       "data.tel", equalTo("123456789"),
//	       "data.email", equalTo("password"),
//	       "id", equalTo(1)).
//	     when().
//	     get("/user/getTestUser");
//	}    
    
    	

}


//returns: { "success": boolean, 
//    "serverMessages": String, 
//    "data": { "user": User } } 
