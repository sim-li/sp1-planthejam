package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Persistence;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import de.bht.comanche.persistence.DaEmProvider;
import junit.framework.TestCase;

/**
 * @author Maxim Novichkov;
 *
 */

//@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgGroupTest {
	static long bob_moid;
	static long pit_moid;
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
	
	public void setUp(){
		session = new LgSession();
		session.beginTransaction();
		session.startFor("Alice");
		sessionUser = session.getUser();
	}
	
	public void end(){
		session.endTransaction(true);
	}
	
	@Test//setUp tearDown
	public void test1SaveGroup(){
		setUp();
		final LgGroup alice_group = new LgGroup();
		alice_group.setName("Group").setUser(sessionUser);
		sessionUser.save(alice_group);
		end();
		assertEquals("Group", new LgSession().startFor("Alice").getGroups().get(0).getName());
	}
	
	@Test
	public void test2changeGroupName(){
		setUp();
		sessionUser.getGroups().get(0).setName("NewGroup");
		end();
		assertEquals("NewGroup", sessionUser.getGroups().get(0).getName());
	}
	
	@Test
	public void test3addMember(){
		setUp();
		aliceGroup = sessionUser.getGroups().get(0);
		bob = session.findByName("Bob");
		pit = session.findByName("Pit");
		sessionUser.save(new LgMember().setUser(bob, aliceGroup));
		sessionUser.save(new LgMember().setUser(pit, aliceGroup));
		
		bob_moid = session.getUser().
				search(aliceGroup.getOid(), bob.getOid()).get(0).getOid();
		pit_moid = session.getUser().
				search(aliceGroup.getOid(), pit.getOid()).get(0).getOid();
		
//		List<LgMember> list = session.getUser().
//				search(aliceGroup.getOid(), bob.getOid());
		
		
		end();
		
		LgGroup lg = sessionUser.getGroups().get(0);
		System.out.println("--------------------lg.getName()" + lg.getName() + "------------");
		System.out.println("--------------------lg.getMembers().get(0)" + sessionUser.getGroups().get(0).getMembers().size() + "------------");
		
//		System.out.println("--------------------lg.getMembers().get(0)" + lg.getMembers().get(0) + "------------");
		assertEquals(1, bob_moid);
		assertEquals(2, pit_moid);
		
//		assertEquals(true, list.contains(bob));
//		assertEquals(true, lg.getMembers().contains(bob));
	}
	
//	@Test
//	public void test4deleteMember(){
//		setUp();
////		sessionUser.getGroup(aliceGroup.getOid()).deleteUser(memberOid);
//		aliceGroup = sessionUser.getGroups().get(0);
//		sessionUser.getGroup(aliceGroup.getOid()).deleteMember(bob_moid);
//		end();
//		assertEquals(null, new LgSession().startFor("Alice").getGroups().get(0).getMember(bob_moid));
//	}
//	
//	@Test
//	public void test5deleteGroup(){
//		setUp();
//		sessionUser.deleteGroup(sessionUser.getGroups().get(0).getOid());
//		end();
//		assertEquals(0, new LgSession().startFor("Alice").getGroups().size());
//	}
}

