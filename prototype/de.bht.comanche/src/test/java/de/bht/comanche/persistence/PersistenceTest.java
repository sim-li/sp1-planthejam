package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.logic.LgUser;

public class PersistenceTest {
	
	private DaFactory factory;
	
	@Before public void setUp(){
		factory = new JpaDaFactory();
	}
	
	@Test public void saveUserTest() {
		JpaDaFactory factory = new JpaDaFactory();
		DaUser daUser = factory.getDaUser();
		daUser.beginTransaction();
		LgUser lgUser = new LgUser();
		lgUser.setName("Ralf");
		lgUser.setEmail("simon@a-studios.org");
		lgUser.setPassword("myPwIsEasy");
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
	
	@Test public void saveUser2() {
		DaUser daUser = factory.getDaUser();
		
		daUser.beginTransaction(); // FIXME
		
		LgUser lgUser = new LgUser();
		lgUser.setName("Ralf");
		lgUser.setEmail("test@user.tst");
		lgUser.setPassword("nosafepwd");
		lgUser.setTelephone("0301234567");
		lgUser.setOid(111111111);
//		lgUser.set
		
		boolean ok = false;
		
		try {
			daUser.save(lgUser);
			ok = true;
		} catch (Exception  e) {
			e.printStackTrace();
		}
		
		daUser.endTransaction(ok); // FIXME
		
		assertEquals(ok, true);
    }
	
	@Test public void getByNameTest() {
		JpaDaFactory factory = new JpaDaFactory();
		DaUser daUser = factory.getDaUser();
		daUser.beginTransaction();
		String nameField = "";
		boolean ok = false;
		try {
			Collection<LgUser> foundUsers = daUser.findByName("Ralf");
			if (foundUsers.size() > 0) {
				nameField = foundUsers.iterator().next().getName();
			}
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
		assertEquals("Ralf", nameField);
	}
}
