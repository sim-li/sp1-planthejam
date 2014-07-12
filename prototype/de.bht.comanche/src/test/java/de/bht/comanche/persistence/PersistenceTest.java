package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.TransactionWithStackTrace;
import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

public class PersistenceTest {
	
	private DaFactory daFactory;
	
	@Before public void setUp(){
		daFactory = new JpaDaFactory();
	}
	
	@Test public void simpleSaveUserTest() {
		final DaUser daUser = daFactory.getDaUser();
		final Pool pool = daUser.getPool();
		/*
		 * This transaction doesn't throw a stacktrace.
		 * (Throwstacktrace property set to false)
		 */
		boolean success = new TransactionWithStackTrace<LgUser>(pool) {
			public void executeWithThrows() throws Exception {
				LgUser lgUser = new LgUser();
				lgUser.setName("Ralf");
				lgUser.setEmail("simon@a-studios.org");
				lgUser.setPassword("myPwIsEasy");
				lgUser.setTel("030-3223939");
//				daUser.save(lgUser);
				/* forceNewTransaction();
				 * Ends current transaction and starts a new one, for example
				 * if we want to find an object in DB to see if ID was set correctly.
				 * Doesn't contain any rollback handling (only for adults).
				 * [ TO BE IMPLEMENTED ]
				 */
				LgUser lgUser1 = new LgUser();
				lgUser.setName("Jenna");
				lgUser.setEmail("simon@a-studios.org");
				lgUser.setPassword("myPwIsEasy");
				lgUser.setTel("030-3223939");
				daUser.save(lgUser);
			}
		}.execute();
		assertTrue(success);
    }
	
	@Test public void saveUserMoreComplete() {
		final DaUser daUser = daFactory.getDaUser();
		final Pool pool = daUser.getPool();
		boolean success = new TransactionWithStackTrace<LgUser>(pool) {
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
			bob.addContact(alice);
			alice.addContact(bob);
			bob.removeContact(alice);
	//		bob.getHasContacts().add(alice);
	//		alice.getIsContacts().add(bob);
	//		
	//		alice.getHasContacts().add(bob);
	//		bob.getIsContacts().add(alice);
	//		
	//		bob.getHasContacts().remove(alice);
	//		alice.getIsContacts().remove(bob);
	//		bob.removeContact(alice);
			daUser.save(alice);
			daUser.save(bob);
	//		assertTrue(bob.getHasContacts().contains(alice));
			assertTrue(alice.getHasContacts().contains(bob));
			assertTrue(bob.getIsContacts().contains(alice));
			}
		}.execute();
		assertTrue(success);
    }
	
	@Test public void getByNameTest() {
		final DaUser daUser = daFactory.getDaUser();
		final Pool pool = daUser.getPool();
		boolean success = new TransactionWithStackTrace<LgUser>(pool) {
			public void executeWithThrows() throws Exception {
				Collection<LgUser> foundUsers = daUser.findByName("Ralf");
				String nameField = foundUsers.iterator().next().getName();
				assertEquals("Ralf", nameField);
			}
		}.execute();
		assertTrue(success);
	}
	
	@Test public void basicTestLayout() throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException, EntityExistsException, TransactionRequiredException, IllegalArgumentException, OidNotFoundException, NotFoundException {
		final DaUser daUser = daFactory.getDaUser();
		final DaInvite daInvite = daFactory.getDaInvite();
		final Pool pool = daUser.getPool();
		daInvite.setPool(pool);
		/*
		 * SAVE DEMOUSER (JUST IN CASE), SO THAT JPA PROVIDES ID.
		 */
		boolean success = new TransactionWithStackTrace<LgUser>(pool) {
			public void executeWithThrows() throws Exception {
				System.out.println(">>> SAVE DEMOUSER (JUST IN CASE), SO THAT JPA PROVIDES ID.");
				LgUser lgUser = new LgUser();
				lgUser.setName("Ralf");
				lgUser.setEmail("simon@a-studios.org");
				lgUser.setPassword("myPwIsEasy");
				lgUser.setTel("030-3223939");
				daUser.save(lgUser);
				/*
				 * RETRIEVE FIRST USER IN DB.
				 */
				System.out.println(">>> RETRIEVE FIRST USER IN DB.");
				LgUser lgUserWithId = daUser.find(0);
				/*
				 * CREATE & SAVE INVITES 
				 */
				System.out.println("> CREATE & SAVE INVITES");
				LgInvite lgInvite = new LgInvite();
				LgInvite lgInvite2 = new LgInvite();
				lgInvite.setUser(lgUser);
				lgInvite2.setUser(lgUser);
				lgInvite2.setHost(true);
				daInvite.save(lgInvite);
				daInvite.save(lgInvite2);
				/*
				 * RETRIEVE INVITES FROM DB
				 */
				System.out.println(">>> RETRIEVE INVITES FROM DB");
				lgInvite = daInvite.find(0);
				lgInvite2 = daInvite.find(1);
				/*
				 * SET INVITES IN USER & SAVE
				 */
				System.out.println(">>> SET INVITES IN USER & SAVE");
				lgUser.addInvites(lgInvite);
				lgUser.addInvites(lgInvite2);
				daUser.save(lgUser);
			}
		}.execute();
		assertTrue(success);
	}
	
	@After public void tearDown() {
		// TODO clean up the database when the tests are done 
	}
	
}
