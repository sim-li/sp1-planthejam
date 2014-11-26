package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Persistence;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import de.bht.comanche.logic.LgGroup;
import de.bht.comanche.logic.LgMember;
import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaEmProvider;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgGroupTest {
	static long user_oid;
	static LgGroup aliceGroup;
	static LgUser sessionUser;
	static LgUser bob;
	static LgUser pit;
	static LgSession session;
	
	
	@BeforeClass 
	public static void initializeDb(){
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaEmProvider.persistenceUnitName, properties);
		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);

		LgSession session = new LgSession();
		session.beginTransaction();
		
		final LgUser alice = new LgUser();
		alice.setName("Alice");
		alice.setEmail("test@test.de");
		alice.setPassword("testtest");
		session.save(alice);
		
		final LgUser bob = new LgUser();
		bob.setName("Bob");
		bob.setEmail("bob@test.de");
		bob.setPassword("testtest");
		session.save(bob);
		
		final LgUser pit = new LgUser();
		pit.setName("Pit");
		pit.setEmail("pit@test.de");
		pit.setPassword("testtest");
		session.save(pit);
		session.endTransaction(true);	
	}
	
	@Test
	public void test1SaveGroup(){
		start();
		final LgGroup alice_group = new LgGroup();
		alice_group.setName("Group").setUser(sessionUser);
		sessionUser.save(alice_group);
		save();
	}
	
	@Test
	public void test2changeGroupName(){
		start();
		sessionUser.getGroups().get(0).setName("NewGroup");
		assertEquals("NewGroup", sessionUser.getGroups().get(0).getName());
		save();
	}
	
	@Test
	public void test3addMember(){
		start();
		aliceGroup = sessionUser.getGroups().get(0);
		bob = session.findByName("Bob");
		pit = session.findByName("Pit");
		sessionUser.save(new LgMember().setUser(pit, aliceGroup));
		sessionUser.save(new LgMember().setUser(bob, aliceGroup));
		save();
	}
	
    // FIXME -- Seb says: commented out because of compilation failure: search(long, long) not found (-> line 95)
	// @Test
	// public void test4deleteMember(){
		// start();
		
		// //TODO back to old school
		// final long memberOid = session.getUser().
										// search(aliceGroup.getOid(), bob.getOid()).get(0).getOid();
		// aliceGroup = sessionUser.getGroups().get(0);
// //		sessionUser.getGroup(aliceGroup.getOid()).deleteUser(memberOid);
		// sessionUser.getGroup(aliceGroup.getOid()).deleteMember(memberOid);
		// save();
	// }
	
	@Test
	public void test5deleteGroup(){
		start();
		sessionUser.deleteGroup(sessionUser.getGroups().get(0).getOid());
		save();
		System.out.println("------------sessionUser.getGroups().size()"  + sessionUser.getGroups().size() + " -----------------");
		
		assertEquals(0, sessionUser.getGroups().size());
	}
	
	public void start(){
		session = new LgSession();
		session.beginTransaction();
		session.startFor("Alice");
		sessionUser = session.getUser();
	}
	
	public void save(){
		session.endTransaction(true);
	}
}

