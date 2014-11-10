package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Request;
import org.junit.Test;

//import com.jayway.restassured.path.json.JsonPath;

import static com.jayway.restassured.RestAssured.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.jayway.restassured.path.json.JsonPath;
import de.bht.comanche.logic.LgGroup;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgTransaction;
//import com.jayway.restassured.response.Response;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.*;

public class ReGroupServiceTest {
	
	@BeforeClass 
	public static void initializeDb(){
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaEmProvider.persistenceUnitName, properties);
		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);
	}
	
	@Before 
	public void setUp() {
		final LgUser alice = new LgUser();
		final LgUser bob = new LgUser();
		alice.setName("Alice");
		bob.setName("Bob1");
		
		final HttpServletRequest rq = new Request(null, null);
		
		new LgTransaction<LgUser>(rq) {
			@Override
			public LgUser execute() throws multex.Exc {
					final LgUser o_user = getSession()
						.register(alice);
					return o_user;
			}
		};	
		
		final LgInvite li = new LgInvite();
		
		 new LgTransaction<LgInvite>(rq) {
			public LgInvite execute() throws multex.Exc{
				return startSession()
					.save(li);
			}
		 };
		 
		final LgGroup lg = new LgGroup();
		lg.setName("Test");
		alice.save(lg);
		
		 new LgTransaction<LgGroup>(rq) {
			@Override
			public LgGroup execute() throws Exception {
				return startDummySession().save(new LgGroup().setName("Bier trinken"));
			}
		};	
			
		
		assertTrue("Persisting test users Alice & Bob", true);
//		final LgUser o_user = getSession().register(i_user);
//		pool.
	}	
	
	@Test
	public void testSaveGroup() {
		LgUser testUser = new LgUser();
//		testUser.setName("Alice1");
//		
//		
//		List<LgGroup> dummyGroups = Arrays.asList(new LgGroup[] {
//				new LgGroup().setName("Bier trinken")
//		});
//		
//		LgGroup lg = new LgGroup();
//		lg.setName("Test");
//		
//		testUser.save(lg);
		
//		com.jayway.restassured.response.Response response = expect().statusCode(0).given().body(dummyGroups)
//				.contentType("application/json").when()
//				.post("/rest/group/save");
//		
//		JsonPath jsonPath = response.getBody().jsonPath();
//		jsonPath.prettyPrint();
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

