package test;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

//import com.jayway.restassured.path.json.JsonPath;

import static com.jayway.restassured.RestAssured.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.jayway.restassured.path.json.JsonPath;
import de.bht.comanche.logic.LgGroup;
//import com.jayway.restassured.response.Response;
import de.bht.comanche.logic.LgUser;

public class ReGroupServiceTest {
	@Test
	public void testSaveGroup() {
		LgUser testUser = new LgUser();
		testUser.setName("Alice");
		testUser.getOid();
		
		List<LgGroup> dummyGroups = Arrays.asList(new LgGroup[] {
				new LgGroup().setName("Bier trinken")
		});

		com.jayway.restassured.response.Response response = expect().statusCode(0).given().body(dummyGroups)
				.contentType("application/json").when()
				.post("/rest/group/save");
		
		JsonPath jsonPath = response.getBody().jsonPath();
		jsonPath.prettyPrint();
//		oid = jsonPath.getLong("data.oid");
		}

//	// FIXME - hard coded quick hack to test communication with client
//	List<LgGroup> dummyGroups = Arrays.asList(new LgGroup[] {
//			new LgGroup().setName("Bier trinken"), 
//			new LgGroup().setName("Chorprobe"), 
//			new LgGroup().setName("Skat spielen") 
//	});
//	return dummyGroups;
	
}

