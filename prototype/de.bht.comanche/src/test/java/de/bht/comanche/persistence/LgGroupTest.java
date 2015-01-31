package de.bht.comanche.persistence;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Persistence;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import de.bht.comanche.logic.LgGroup;
import de.bht.comanche.logic.LgMember;
import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgUser;

/**
 * @author Maxim Novichkov;
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
	public void test1SaveEmptyGroup(){
		final LgSession session = start();
		startForAlice(session).save(new LgGroup().setName("Group").setUser(session.getUser()));
		end(session);
		assertThat("Group").isEqualTo(getAliceGroup().getName());
	}
	
	@Test
	public void test2UpdateGroupName(){
		final LgSession session = start();
		startForAlice(session).getGroups().get(0).setName("NewGroup");
		end(session);
		assertThat("NewGroup").isEqualTo(getAliceGroup().getName());
	}

	@Test
	public void test3updateGroup(){
		final LgSession session = start();
		final LgUser user = startForAlice(session);
		final LgGroup aliceGroup = user.getGroups().get(0);
		final LgUser bob = session.startFor("Bob"); 		
		final LgUser pit = session.startFor("Pit");
		user.save(setUserToGroup(user, aliceGroup, bob, pit));
		end(session);
		assertThat((extractProperty("name").from(getAliceGroup().getUsers()))).
														containsOnly("Bob", "Pit");
		}
	
	@Test
	public void test4deleteGroup(){
		final LgSession session = start();
		final LgUser sessionUser = startForAlice(session);
		sessionUser.deleteGroup(sessionUser.getGroups().get(0).getOid());
		end(session);
		start();
		assertThat(startFor().getGroups()).isEmpty();		
	}
	
	@Test
	public void test5deleteUser(){		
		test1SaveEmptyGroup();
		test3updateGroup();
		final LgSession session = start();
		final LgUser sessionUser = startForAlice(session);
		sessionUser.deleteThisAccount();
		assertThat(startForBob(session).findManyByKey(LgUser.class, "name", "Alice")).isEmpty();
		end(session);
	}
	
	public LgGroup setUserToGroup(LgUser alice, final LgGroup group, final LgUser ... users){
		for (LgUser user : users){
			alice.saveUnattached(new LgMember().setUser(user).setGroup(group));
		}
		return group;
	}
	
	public void end(LgSession session){
		session.getApplication().endTransaction(true);
	}
	
	public LgUser startForAlice(final LgSession session){
		return session.startFor("Alice");
	}
	
	public LgUser startForBob(final LgSession session){
		return session.startFor("Bob");
	}
	
	public LgUser startFor(){
		return new LgSession().startFor("Alice");
	}
	
	public LgGroup getAliceGroup(final long oid){
		return startFor().getGroup(oid);
	}
	
	public LgGroup getAliceGroup(){
		final LgGroup aliceGroup = startFor().getGroups().get(0);
		return startFor().getGroup(aliceGroup.getOid());
	}
	
}
