package de.bht.comanche.server;

import static com.jayway.restassured.RestAssured.given;
import java.util.ArrayList;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import de.bht.comanche.logic.LgUser;

public class UserServiceTest {
//	@Test
    public void registerUser() {
	
		LgUser testUser = new LgUser();
		testUser.setName("John");
		testUser.setEmail("test@test.com");
		testUser.setPassword("password");
		testUser.setTel("123456789");
    	
		given().contentType("application/json")
		       .body(testUser)
		       .expect().statusCode(200)
		       .when().post("/plan-the-jam/rest/user/register");

    	}
    
//    @Test
	public void getUser() {
	   expect().statusCode(200).body(
	       "success", equalTo(true),
	       "serverMessages", equalTo("success"),
	       "data.name", equalTo("John"),
	       "data.tel", equalTo("123456789"),
	       "data.email", equalTo("password"),
	       "id", equalTo(1)).
	     when().
	     get("/plan-the-jam/rest/user/getTestUser");
	}    
}

//returns: { "success": boolean, 
//    "serverMessages": String, 
//    "data": { "user": User } } 
