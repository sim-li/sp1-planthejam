package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.testresources.logic.UserFactory;
import de.bht.comanche.testresources.server.TransactionWithStackTrace;

public class PersistenceBasicOperationsTest {
	private final boolean THROW_STACKTRACE = true;
	private final boolean ROLLBACK = false;
	private DaUser daUser;
	private DaFactory daFactory;
	private LgUser alice;
	private LgUser bob;
	
	@Before public void setUp(){
		daFactory = new JpaDaFactory();
		UserFactory userFactory = new UserFactory();
		daUser = daFactory.getDaUser();
		alice = userFactory.getUser0();
		bob = userFactory.getUser1();
		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), true, false) {
			public void executeWithThrows() throws Exception {
					daUser.save(alice);
					daUser.save(bob);
			}
		}.execute();
		assertTrue("SET UP ( SAVE ) for ALICE&BOB", success);
	}
	
	@Test 
	public void findByNameTest() {
		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
				LgUser aliceFromDb = daUser.findByName(alice.getName()).get(0);
				LgUser bobFromDb = daUser.findByName(bob.getName()).get(0);
				final String userName0 = "ALICE";
				assertEquals(userName0 + " > NAMEL", aliceFromDb.getName(), alice.getName());
				assertEquals(userName0 + " > EMAIL", aliceFromDb.getEmail(), alice.getEmail());
				assertEquals(userName0 + " > TEL", aliceFromDb.getTel(), alice.getTel());
				assertEquals(userName0 + " > PASSWORD", aliceFromDb.getPassword(), alice.getPassword());
				final String userName1 = "BOB";
				assertEquals(userName1 + " > NAME", bobFromDb.getName(), bob.getName());
				assertEquals(userName1 + " > EMAIL", bobFromDb.getEmail(), bob.getEmail());
				assertEquals(userName1 + " > TEL", bobFromDb.getTel(), bob.getTel());
				assertEquals(userName1 + " > PASSWORD", bobFromDb.getPassword(), bob.getPassword());
			}
		}.execute();
		assertTrue("FIND BY NAME DA OPERATIONS, THROW EXCEPTIONS", success);
    }
	
	//TODO ( Missing assertion ) 
	@Ignore
	@Test
	public void addContactsTest(){
		final DaUser daUser = daFactory.getDaUser();
		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
				LgUser aliceFromDb = daUser.findByName("Alice").get(0);
				LgUser bobFromDb  = daUser.findByName("Bob").get(0);
				aliceFromDb.addContact(bobFromDb);
			}
		}.execute();
		assertTrue("ADD CONTACTS DA OPERATIONS, THROW EXCEPTIONS", success);
	}
	
	//TODO ( Missing assertion )
	@Ignore
	@Test public void findIdTest() {
		final DaUser daUser = daFactory.getDaUser();
		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
//			LgUser aliceFromDb = daUser.find(aliceFromDb.getOid());
//			assertEquals(id, aliceFromFind.getOid());
			}
		}.execute();
		assertTrue(success);
	}


	@After public void tearDown() {
		final DaUser daUser = daFactory.getDaUser();
		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
				LgUser aliceFromDb = daUser.find(alice.getOid());
				LgUser bobFromDb = daUser.find(bob.getOid());
				daUser.delete(aliceFromDb);
				daUser.delete(bobFromDb);
			}
		}.execute();
		assertTrue("TEAR DOWN ( DELETE ) for ALICE&BOB: ALICE ID " + alice.getOid() + "BOB ID " + bob.getOid(), success);
	}
	
}
