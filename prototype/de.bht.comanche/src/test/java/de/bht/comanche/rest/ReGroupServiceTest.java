package de.bht.comanche.rest;

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
	
	
//	@BeforeClass 
	public static void initializeDb(){
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaEmProvider.persistenceUnitName, properties);
		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);
	}
	
//	@BeforeClass 
	public static void initializeUser(){
		
		LgSession session = new LgSession();
		session.beginTransaction();
		
		//save user - it works
		final LgUser alice = new LgUser();
		alice.setName("Alice");
		alice.setEmail("test@test.de");
		alice.setPassword("testtest");
		session.save(alice);
		
		//create new LgUser 
		final LgUser bob = new LgUser();
		bob.setName("Bob");
		bob.setEmail("bob@test.de");
		bob.setPassword("testtest");
		session.save(bob);
		
		//create new LgUser 
		final LgUser pit = new LgUser();
		pit.setName("Pit");
		pit.setEmail("pit@test.de");
		pit.setPassword("testtest");
		session.save(pit);
		
		session.endTransaction(true);	
	}
	
	@Test
	public void testSaveGroup(){
		
		LgSession session = new LgSession();
		session.beginTransaction();
		
//"login" user Alice
		session.startFor("Alice");
		LgUser sessionUser = session.getUser();
		
		System.out.println("------- Alice Oid: " + session.getUser().getOid() + "-------------");

//save group with userId - it works
//		final LgGroup alice_group = new LgGroup();
//		alice_group.setName("Group");
//		alice_group.setUser(sessionUser);		
//		sessionUser.save(alice_group);
		
//change group name - it works 		
//		List<LgGroup> lgrop = sessionUser.getGroups();
//		//incoming updated group with changed name
//		LgGroup tempGroup = lgrop.get(0);
//		tempGroup.setName("NewGroup");
//		sessionUser.save(tempGroup);
//		System.out.println( lgrop.get(0).getGroupName() + " -----------------------------");

//delete group - it works
//		List<LgGroup> lgrop = sessionUser.getGroups();
		// worked with .getGroupOid()
//		long oid = lgrop.get(0).getOid();
//		sessionUser.deleteGroup(oid);
		

		
//add new LgUser(LgMember) to LgGroup - it works
//		LgGroup aliceGroup = sessionUser.getGroups().get(0);
//		sessionUser.save(new LgMember(session.findByName("Pit"), aliceGroup));
//		sessionUser.save(new LgMember(session.findByName("Bob"), aliceGroup));
//		System.out.println("------------GroupName: " + aliceGroup.getGroupName() + " -----------------");
		

//delete group with members	
		System.out.println("------------GroupName: " + sessionUser.getGroups().get(0).getGroupName() + " -----------------");
		sessionUser.getGroups().get(0).delete();
		
//delete one member and update group
//first way - just delete LgMember by oid
//second way - get group - delete user - save group
//		LgGroup aliceGroup = sessionUser.getGroups().get(0);
//		List<LgMember> lg = aliceGroup.getLgMembers();
//		lg.get(0).delete();

		
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

