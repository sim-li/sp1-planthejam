package de.bht.comanche.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.TransactionWithStackTrace;

public class PersistenceBasicOperationsTest {
	private final boolean THROW_STACKTRACE = true;
	private final boolean ROLLBACK = false;
	
	private DaFactory daFactory;
	
	@Before public void setUp(){
		daFactory = new JpaDaFactory();
	}
	
	@Test 
	public void saveUserTest() {
		final DaUser daUser = daFactory.getDaUser();
	
		/*
		 * This transaction doesn't throw a stacktrace.
		 * (Throwstacktrace property set to false)
		 */
		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
					LgUser alice = new LgUser();
					alice.setName("Alice");
					alice.setEmail("alice@user.tst");
					alice.setPassword("nosafepwd");
					alice.setTel("0301234567");

					LgUser bob = new LgUser();
					bob.setName("Bob");
					bob.setEmail("bob@test.usr");
					bob.setPassword("hiiambob");
					bob.setTel("0309876543");
					
					daUser.save(alice);
					daUser.save(bob);
			}
		}.execute();
		assertTrue(success);
    }
		
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
		assertTrue(success);
	}

//	@Test public void findIdTest() {
//		final DaUser daUser = daFactory.getDaUser();
//		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
//			public void executeWithThrows() throws Exception {
//				LgUser aliceFromDb = daUser.findByName("Alice").get(0);
//				long id = aliceFromDb.getOid();
//				LgUser aliceFromFind = daUser.find(id);
//				
//				assertEquals(id, aliceFromFind.getOid());
//			}
//		}.execute();
//		assertTrue(success);
//    }
	
//	@Test
//	public void deleteUserTest(){
//		final DaUser daUser = daFactory.getDaUser();
//		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
//			public void executeWithThrows() throws Exception {
//				List<LgUser> aliceFromDb = daUser.findByName("Alice");
//				if(aliceFromDb != null){
//					daUser.delete(aliceFromDb.get(0));
//					
//				}
//			}
//		}.execute();
//		assertTrue(success);
//	}	
}
