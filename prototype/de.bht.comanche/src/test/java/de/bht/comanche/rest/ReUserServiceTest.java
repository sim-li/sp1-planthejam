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
public class ReUserServiceTest {

	@Test
	public void testHelloWorld(){
		final String body = get("/rest/user/hello").asString();
		assertThat(body, equalTo("Hello World!"));
	}
	
//	@Test
//    public void getInvites() {
//	
//		LgUser testUser = new LgUser();
//		testUser.setOid(1);
//    	
//		Response response = expect().statusCode(200).given().body(testUser).contentType("application/json")
//		.when().post("/rest/survey/getInvites");
//		response.prettyPrint();
//		
//	}
	
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
