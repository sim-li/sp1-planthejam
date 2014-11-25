package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.bht.comanche.persistence.DaEmProvider;

/**
 * @author Maxim Novichkov;
 *
 */

//@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgGroupTest {
	static long bob_moid;
	static LgGroup aliceGroup;
	static LgUser sessionUser;
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
	
	@Before
	public void setUp(){
		session = new LgSession();
		session.beginTransaction();
		session.startFor("Alice");
		sessionUser = session.getUser();
	}
	
	@Test
	public void test1SaveGroup(){
		sessionUser.save(new LgGroup().setName("Group").setUser(sessionUser));
		end();
		aliceGroup = startFor().getGroups().get(0);
		assertEquals("Group", getAliceGroup().getName());
	}
	
	@Test
	public void test2changeGroupName(){
		sessionUser.getGroups().get(0).setName("NewGroup");
		end();
		assertEquals("NewGroup", getAliceGroup().getName());
	}
	
	@Test
	public void test3addMember(){
		aliceGroup = sessionUser.getGroups().get(0);
		final LgUser bob = session.findByName("Bob");
		final LgUser pit = session.findByName("Pit");
		sessionUser.save(new LgMember().setUser(bob, aliceGroup));
		sessionUser.save(new LgMember().setUser(pit, aliceGroup));
		bob_moid = session.getUser().
				search(aliceGroup.getOid(), bob.getOid()).get(0).getOid();
		end();
		
		LgUser lg = getAliceGroup().getUsers().get(1);
		System.out.println("--------------------lg.toString()" + lg.toString() + "------------");
		System.out.println("--------------------pit.toString()" + pit.toString() + "------------");
		
		assertEquals("Bob", getAliceGroup().getUsers().get(0).getName());
		assertEquals("Pit", getAliceGroup().getUsers().get(1).getName());
//		assertEquals(true, getAliceGroup().getUsers().contains(lg));
	}
	
	@Test
	public void test4deleteMember(){
		aliceGroup = sessionUser.getGroups().get(0);
		sessionUser.getGroup(aliceGroup.getOid()).deleteMember(bob_moid);
		end();
		assertEquals(null, getAliceGroup().getMember(bob_moid));
	}
	
	@Test
	public void test5deleteGroup(){
		sessionUser.deleteGroup(sessionUser.getGroups().get(0).getOid());
		end();
		assertEquals(0, startFor().getGroups().size());
	}
	
	public void end(){
		session.endTransaction(true);
	}
	
	public LgUser startFor(){
		return new LgSession().startFor("Alice");
	}
	
	public LgGroup getAliceGroup(){
		return startFor().getGroup(aliceGroup.getOid());
	}
	
}

