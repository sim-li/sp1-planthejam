// package logic;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import javax.persistence.Persistence;
// import javax.servlet.http.HttpServletRequest;
// import javax.ws.rs.core.Response;
// import org.eclipse.jetty.server.Request;
// import org.junit.Test;
// import static com.jayway.restassured.RestAssured.expect;
// import static multex.MultexUtil.create;
// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNull;
// import static org.junit.Assert.assertTrue;
// import org.junit.Before;
// import org.junit.BeforeClass;
// import org.junit.FixMethodOrder;
// import org.junit.Test;
// import org.junit.runners.MethodSorters;
// import com.jayway.restassured.path.json.JsonPath;
// import de.bht.comanche.logic.LgGroup;
// import de.bht.comanche.logic.LgInvite;
// import de.bht.comanche.logic.LgMember;
// import de.bht.comanche.logic.LgSession;
// import de.bht.comanche.logic.LgTransaction;
// import de.bht.comanche.logic.LgUser;
// import de.bht.comanche.logic.LgSession.LgWrongPasswordExc;
// import de.bht.comanche.persistence.*;
// import de.bht.comanche.rest.ReErrorMessage;
// import de.bht.comanche.rest.ReServerException;

// @Ignore
// public class LgGroupTest {
// 	static long user_oid;
// 	static long group_oid;
//  // TOOD: Session probably goes here, also SessionUser


// 	@BeforeClass
// 	public static void initializeDb(){
// 		Map<String, String> properties = new HashMap<String, String>(1);
// 		properties.put("hibernate.hbm2ddl.auto", "create");
// 		Persistence.createEntityManagerFactory(DaEmProvider.persistenceUnitName, properties);
// 		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);
// 	}

// 	@BeforeClass
// 	public static void initializeUser(){
//         LgSession session = new LgSession();
// 		session.beginTransaction();
// 		final LgUser alice = new LgUser();
// 		alice.setName("Alice")
//             .setEmail("test@test.de")
// 		        .setPassword("testtest");
// 		session.save(alice);
// 		final LgUser bob = new LgUser();
// 		bob.setName("Bob").
// 		    setEmail("bob@test.de")
// 		        .setPassword("testtest");
// 		session.save(bob);
// 		final LgUser pit = new LgUser();
// 		pit.setName("Pit")
// 		    .setEmail("pit@test.de")
// 		        .setPassword("testtest");
// 		session.save(pit);
// 		session.endTransaction(true);
// 	}

// 	@Test
// 	public void testSaveGroup(){
// 		LgSession session = new LgSession();
// 		session.beginTransaction();
// 		session.startFor("Alice");
// 		LgUser sessionUser = session.getUser();

// 		final LgGroup alice_group = new LgGroup();
// 		alice_group.setGroupName("Group");
// 		alice_group.setUser(sessionUser);
// 		sessionUser.save(alice_group);

//     }

//     @Test
//     public void testDeleteGroup() {
//         sessionUser.deleteGroup(sessionUser.getGroups().get(0).getOid());
//         // TODO:
//         // user.getGroup(xxx).delete()
//         // assert user.getGroup() == null
//     }

//     @Test
//     public void testAddMember() {
//         //user.getGroup.addMember...
//         // assert ussr.getGroup(...)
//     }

//     @Test
//     public void testDeleteMember() {
// 		LgGroup aliceGroup = sessionUser.getGroups().get(0);
// 		LgUser bob = session.findByName("Bob");
// 		LgMember lg = sessionUser.search(aliceGroup.getOid(), bob.getOid()).get(0);
// 		System.out.println("------------getMembersByGroupId: " + sessionUser.search(aliceGroup.getOid(), bob.getOid()).get(0).getOid() + " -----------------");
//         sessionUser
//             .getGroup(aliceGroup.getOid())
//                 .deleteUser(bob.getOid());  //fix Bug
//     }

//     @Test
//     public void testDeleteUser() {
//     }

// 	// @AfterClass closeSession() {
//  //        session.endTransaction(true);
//  //        assertTrue("Persisting test users Alice & Bob", true);
//  //    }

// }

