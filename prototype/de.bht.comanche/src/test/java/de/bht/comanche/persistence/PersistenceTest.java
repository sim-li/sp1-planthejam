package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import org.junit.Test;

import de.bht.comanche.logic.LgUser;

public class PersistenceTest {

	@Test public void saveUserTest() {
		JpaDaFactory factory = new JpaDaFactory();
		DaUser daUser = factory.getDaUser();
		daUser.beginTransaction();
		LgUser lgUser = new LgUser();
		lgUser.setName("Ralf");
		lgUser.setEmail("simon@a-studios.forg");
		lgUser.setPassword("myPw");
		lgUser.setTel("030-3223939");
		boolean ok = false;
		try {
			daUser.save(lgUser);
			ok = true;
		} catch (EntityExistsException e) {
			e.printStackTrace();
		} catch (TransactionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		daUser.endTransaction(ok);
		assertEquals(ok, true);
    }
	
	@Test public void getByNameTest() {
		JpaDaFactory factory = new JpaDaFactory();
		DaUser daUser = factory.getDaUser();
		daUser.beginTransaction();
		Collection<LgUser> foundUsers;
		boolean ok = false;
		try {
			foundUsers = daUser.findByName("Ralf");
			ok = true;
		} catch (NoPersistentClassExc e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoQueryClassExc e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArgumentCountExc e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArgumentTypeExc e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		daUser.endTransaction(ok);
		assertEquals(ok, true);
	}
}
