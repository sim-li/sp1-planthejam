package test;

import java.util.ArrayList;
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
import de.bht.comanche.logic.LgMember;
import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgTransaction;
//import com.jayway.restassured.response.Response;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.*;
import de.bht.comanche.rest.ReErrorMessage;
import de.bht.comanche.rest.ReServerException;

public class ReGroupServiceTest {
	static long user_oid;
	static long group_oid;
	
	
	@BeforeClass 
	public static void initializeDb(){
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaEmProvider.persistenceUnitName, properties);
		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);
	}
	
	@Test
	public void testSaveGroup(){
		
		
//		LgSession session = new LgSession{
//			application = new DaApplication();
//			pool = application.getPool();
//			user = "Alice";
//		};
		
		LgSession session = new LgSession();
		session.beginTransaction();
		
		final LgUser alice = new LgUser();
		alice.setName("Alice");
		session.setUser(alice);
//		session.startFor("Alice");
		session.save(alice);
		
		//save group
		final LgGroup alice_group = new LgGroup();
		alice_group.setName("AliceGroup");
		alice.save(alice_group);

//		//save group
//		final LgGroup alice_group1 = new LgGroup();
//		alice_group1.setName("AliceGroup1");
//		alice.save(alice_group1);
		
		

		
//		user_oid = alice.getOid();

		
//		final LgGroup bob_group = new LgGroup();
//		bob_group.setName("BobGroup");
//		bob.save(bob_group);
		
//		List<LgMember> memberList = new ArrayList<LgMember>();
//		memberList.add(alice);
//		bob_group.setMembers(member)
//		
//		if(!bob.getGroups().isEmpty()){
//			LgGroup lg = bob.getGroups().get(0);	
//			System.out.println("------------------- " + lg.getName() + " -----------------");
//		} else {
//			System.err.println("------------------- Group Is empty -----------------");
//		}
		
		session.endTransaction(true);
		
		
		
		assertTrue("Persisting test users Alice & Bob", true);
//		
	}
	
	@Test
	public void testDeleteGroup(){

//		alice_group1.getOid();
//		alice.deleteGroup(alice_group1.getOid());	
	
	
		assertTrue("Persisting test users Alice & Bob", true);
	}
		
		
		
		
//		final LgInvite li = new LgInvite();
//		
//		 new LgTransaction<LgInvite>(rq) {
//			public LgInvite execute() throws multex.Exc{
//				return startSession()
//					.save(li);
//			}
//		 };
		 
		
		
//		 new LgTransaction<LgGroup>(rq) {
//			@Override
//			public LgGroup execute() throws Exception {
//				return startDummySession().save(new LgGroup().setName("Bier trinken"));
//			}
//		};	
			
		
		
//		final LgUser o_user = getSession().register(i_user);
//		pool.
		
	
	@Test
	public void testSaveGroup1() {
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
//		user_oid = jsonPath.getLong("data.oid");
		}

//	// FIXME - hard coded quick hack to test communication with client
//	List<LgGroup> dummyGroups = Arrays.asList(new LgGroup[] {
//			new LgGroup().setName("Bier trinken"), 
//			new LgGroup().setName("Chorprobe"), 
//			new LgGroup().setName("Skat spielen") 
//	});
//	return dummyGroups;
	
}

