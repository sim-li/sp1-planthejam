package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
	
	@BeforeClass 
	public static void initializeDb() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);

		LgSession session = new LgSession();
		session.getApplication().beginTransaction();
		
		final LgUser alice = new LgUser();
		alice.setName("Alice");
		alice.setEmail("test@test.de");
		alice.setPassword("testtest");
		session.register(alice);
		
		final LgUser bob = new LgUser();
		bob.setName("Bob");
		bob.setEmail("bob@test.de");
		bob.setPassword("testtest");
		session.register(bob);
		
		final LgUser pit = new LgUser();
		pit.setName("Pit");
		pit.setEmail("pit@test.de");
		pit.setPassword("testtest");
		session.register(pit);
		session.getApplication().endTransaction(true);	
	}
	
	public LgSession start(){
		LgSession session = new LgSession();
		session.getApplication().beginTransaction();
		return session;
	}
	
	@Test
	public void test1SaveGroup(){
		final LgSession session = start();
		startForUser(session).save(new LgGroup().setName("Group").setUser(session.getUser()));
		end(session);
		final LgGroup aliceGroup = startFor().getGroups().get(0);
		assertEquals("Group", getAliceGroup(aliceGroup.getOid()).getName());
	}
	
	@Test
	public void test2changeGroupName(){
		final LgSession session = start();
		startForUser(session).getGroups().get(0).setName("NewGroup");
		end(session);
		final LgGroup aliceGroup = startFor().getGroups().get(0);
		assertEquals("NewGroup", getAliceGroup(aliceGroup.getOid()).getName());
	}

	@Test
	public void test3addMember(){
		final LgSession session = start();
		final LgUser user = startForUser(session);
		final LgGroup aliceGroup = user.getGroups().get(0);
		//changed findByName->startFor: same functionality
		final LgUser bob = session.startFor("Bob"); 		
		final LgUser pit = session.startFor("Pit");
		user.save(new LgMember().setUser(bob).setGroup(aliceGroup));
		user.save(new LgMember().setUser(pit).setGroup(aliceGroup));
		end(session);
		
		assertEquals("Bob", getAliceGroup(aliceGroup.getOid()).getUsers().get(0).getName());
		assertEquals("Pit", getAliceGroup(aliceGroup.getOid()).getUsers().get(1).getName());
		}
	
//	@Test
	public void test4deleteMember(){
		final LgSession session = start();
		final LgUser sessionUser = startForUser(session);
		final LgGroup aliceGroup = sessionUser.getGroups().get(0);
		final LgUser bob = session.startFor("Bob");
		final long bob_moid = session.getUser().search(aliceGroup.getOid(), bob.getOid()).get(0).getOid();
		sessionUser.getGroup(aliceGroup.getOid()).deleteMember(bob_moid);
		end(session);
		assertEquals(null, getAliceGroup(aliceGroup.getOid()).getMember(bob_moid));
	}
	
//	@Test
	public void test5deleteGroup(){
		final LgSession session = start();
		final LgUser sessionUser = startForUser(session);
		sessionUser.deleteGroup(sessionUser.getGroups().get(0).getOid());
		end(session);
		assertEquals(0, startFor().getGroups().size());
	}
	
	public void end(LgSession session){
		session.getApplication().endTransaction(true);
	}
	
	public LgUser startForUser(final LgSession session){
		return session.startFor("Alice");
	}
	
	public LgUser startFor(){
		return new LgSession().startFor("Alice");
	}
	
	public LgGroup getAliceGroup(final long oid){
		return startFor().getGroup(oid);
	}
	
}
