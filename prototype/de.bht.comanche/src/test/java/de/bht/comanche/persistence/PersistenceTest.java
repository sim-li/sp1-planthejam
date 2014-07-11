package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.TransactionRequiredException;

import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;

public class PersistenceTest {
	
	private DaFactory factory;
	
	@Before public void setUp(){
		factory = new JpaDaFactory();
	}
	
//	@Test public void saveUserTest() {
//		DaUser daUser = factory.getDaUser();
//		daUser.beginTransaction();
//		LgUser lgUser = new LgUser();
//		lgUser.setName("Ralf");
//		lgUser.setEmail("simon@a-studios.org");
//		lgUser.setPassword("myPwIsEasy");
//		lgUser.setTel("030-3223939");
//		
//		
//		boolean ok = false;
//		try {
//			daUser.save(lgUser);
//			ok = true;
//		} catch (EntityExistsException e) {
//			e.printStackTrace();
//		} catch (TransactionRequiredException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {			
//			daUser.endTransaction(ok);
//		}
//		assertEquals(ok, true);
//    }
	
	@Test public void saveInviteTest() throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException, EntityExistsException, TransactionRequiredException, IllegalArgumentException {
		EntityManager entityManager;
		EntityManagerFactory entityManagerFactory;
		entityManagerFactory = Persistence.createEntityManagerFactory("planthejam.jpa");
		entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tr = entityManager.getTransaction();
		tr.begin();
		LgUser lgUser = new LgUser();
		lgUser.setName("Ralf");
		lgUser.setEmail("simon@a-studios.org");
		lgUser.setPassword("myPwIsEasy");
		lgUser.setTel("030-3223939");
		LgInvite lgInvite = new LgInvite();
		LgInvite lgInvite2 = new LgInvite();
		lgUser.addInvite(lgInvite);
		lgUser.addInvite(lgInvite2);
		entityManager.persist(lgInvite);
		entityManager.persist(lgInvite2);
		entityManager.persist(lgUser);
		tr.commit();
		entityManager.close();
		
		assertEquals(true,true);
	}
//	
//	@Test public void saveUserMoreComplete() {
//		DaUser daUser = factory.getDaUser();
//		
//		daUser.beginTransaction(); // FIXME --> Transaction
//		
//		LgUser alice = new LgUser();
//		alice.setName("Alice");
//		alice.setEmail("alice@user.tst");
//		alice.setPassword("nosafepwd");
//		alice.setTel("0301234567");
//		
//		LgUser bob = new LgUser();
//		bob.setName("Bob");
//		bob.setEmail("bob@test.usr");
//		bob.setPassword("hiiambob");
//		bob.setTel("0309876543");
////		bob.addHasContact(bob);
//		
//		boolean ok = false;
//		
//		try {
//			daUser.save(alice);
//			daUser.save(bob);
//			ok = true;
//		} catch (Exception  e) {
//			e.printStackTrace();
////			ok = true;
//		} finally {			
//			daUser.endTransaction(ok); // FIXME --> Transaction
//		}
//		
//		
//		assertEquals(ok, true);
////		assertEquals(ok, false);
//    }
//	
//	@Test public void getByNameTest() {
//		JpaDaFactory factory = new JpaDaFactory();
//		DaUser daUser = factory.getDaUser();
//		daUser.beginTransaction();
//		String nameField = "";
//		boolean ok = false;
//		try {
//			Collection<LgUser> foundUsers = daUser.findByName("Ralf");
//			if (foundUsers.size() > 0) {
//				nameField = foundUsers.iterator().next().getName();
//			}
//			ok = true;
//		} catch (NoPersistentClassException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoQueryClassException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ArgumentCountException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ArgumentTypeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		daUser.endTransaction(ok);
//		assertEquals("Ralf", nameField);
//	}
	
//	@After public void tearDown() {
//		// TODO clean up the database when the tests are done 
//	}
	
}
