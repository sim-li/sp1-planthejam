package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.bht.comanche.exceptions.DaException;
import de.bht.comanche.logic.LgLowLevelTransaction;
import de.bht.comanche.logic.LgTransactionWithStackTrace;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.logic.LgUserDummyFactory;
public class DaUserTest {
	final String userName0 = "ALICE";
	final String userName1 = "BOB";
	private static final boolean THROW_STACKTRACE = true;
	private static final boolean ROLLBACK = false;
	private static DaUser daUser;
	private static DaFactory daFactory;
	private LgUser alice;
	private LgUser bob;
	
	@BeforeClass public static void initializeDb() throws DaException {
		daFactory = new DaFactoryJpaImpl();
		daUser = daFactory.getDaUser();
		boolean success = new LgLowLevelTransaction(THROW_STACKTRACE) {
			public void executeWithThrows() throws Exception {
				DaTestUtils persistenceUtils = new DaTestUtils(daUser.getPool());
				persistenceUtils.initializeDb();
			}
		}.execute();
		
		assertTrue("Initializing DB", success);
	}
	
	@Before public void setUp() {
		LgUserDummyFactory userFactory = new LgUserDummyFactory();
		alice = userFactory.getUser0();
		bob = userFactory.getUser1();
		boolean success = new LgTransactionWithStackTrace<LgUser>(daUser.getPool(), true, ROLLBACK) {
			public void executeWithThrows() throws Exception {
					daUser.save(alice);
					daUser.save(bob);
			}
		}.execute();
		System.out.println("ALICE AFTER PERSIST: " + alice.getOid());
		System.out.println("BOB AFTER PERSIST: " + bob.getOid());
		assertTrue("Persisting test users Alice & Bob", success);
	}
	
	@Test 
	public void findByNameTest() {
		boolean success = new LgTransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
				LgUser aliceFromDb = daUser.findByName(alice.getName()).get(0);
				LgUser bobFromDb = daUser.findByName(bob.getName()).get(0);
				assertUser(userName0, alice, aliceFromDb);
				assertUser(userName1, bob, bobFromDb);
			}
		}.execute();
		assertTrue("DA - operations with exceptions (see TransactionObject)", success);
    }
	
	@Test 
	public void updateWithSeperateTransactionsTest() {
		boolean success = new LgTransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
				// Alice was persisted in other context during Setup
				alice.setName("AliciaTeba");
				daUser.update(alice);
				forceRestartTransaction();
				LgUser aliceAfterUpdate = daUser.find(alice.getOid());
				assertEquals("Alice after update", alice.getOid(), aliceAfterUpdate.getOid());
				assertEquals("Alice's name after update", alice.getName(), aliceAfterUpdate.getName());
			}
		}.execute();
		assertTrue("DA - operations with exceptions (see TransactionObject)", success);
    }
	
	@Test 
	public void updateModificationAfterMerge() {
		boolean success = new LgTransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
				// Alice was persisted in other context during Setup
				alice.setName("AliciaTeba");
				LgUser aliceInThisContext;
				aliceInThisContext = daUser.update(alice);
				aliceInThisContext.setEmail("littleangel@angel.de");
				// Alice 
				forceRestartTransaction();
				LgUser aliceAfterUpdate = daUser.find(alice.getOid());
				assertEquals("Alice after update", alice.getOid(), aliceAfterUpdate.getOid());
				assertEquals("Alice's name after update", "AliciaTeba", aliceAfterUpdate.getName());
				assertEquals("Alice's email after update", "littleangel@angel.de", aliceAfterUpdate.getEmail());
			}
		}.execute();
		assertTrue("DA - operations with exceptions (see TransactionObject)", success);
    }
	
	
	
	@Test public void findByIdTest() {
		final DaUser daUser = daFactory.getDaUser();
		boolean success = new LgTransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
			LgUser aliceFromDb = daUser.find(alice.getOid());
			LgUser bobFromDb = daUser.find(bob.getOid());
			assertEquals("Comparing " + userName0 + "> LOCAL: " + alice.getOid() + ", FROM DB: " +  aliceFromDb.getOid(),
					alice.getOid(), aliceFromDb.getOid());
			assertEquals("Comparing " + userName1 + "> LOCAL: " + bob.getOid() + ", FROM DB: " +  bobFromDb.getOid(),
					bob.getOid(), bobFromDb.getOid());
			assertUser(userName0, alice, aliceFromDb);
			assertUser(userName1, bob, bobFromDb);
			}
		}.execute();
		assertTrue("DA - operations with exceptions (see TransactionObject)", success);
	}
	
	public void assertUser(String userName, LgUser user, LgUser userFromDb) {
		assertEquals(userName + " > NAME", user.getName(), userFromDb.getName());
		assertEquals(userName + " > EMAIL", user.getEmail(), userFromDb.getEmail());
		assertEquals(userName + " > TEL", user.getTel(), userFromDb.getTel());
		assertEquals(userName + " > PASSWORD", user.getPassword(), userFromDb.getPassword());
	}

	@After public void tearDown() {
		final DaUser daUser = daFactory.getDaUser();
		boolean success = new LgTransactionWithStackTrace<LgUser>(daUser.getPool(), true, ROLLBACK) {
			public void executeWithThrows() throws Exception {
				LgUser aliceFromDb = daUser.find(alice.getOid());
				LgUser bobFromDb = daUser.find(bob.getOid());
				daUser.delete(aliceFromDb);
				daUser.delete(bobFromDb);
			}
		}.execute();
		assertTrue("Deleting Alice & Bob: |Alice ID|> " + alice.getOid() + " |Bob ID|> " + bob.getOid(), success);
		DaTestUtils pu = new DaTestUtils(daUser.getPool());
	}
	
}