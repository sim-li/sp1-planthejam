package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.ResponseObject;
import de.bht.comanche.server.Transaction;
import de.bht.comanche.server.exceptions.ArgumentCountException;
import de.bht.comanche.server.exceptions.ArgumentTypeException;
import de.bht.comanche.server.exceptions.NoPersistentClassException;
import de.bht.comanche.server.exceptions.NoQueryClassException;
import de.bht.comanche.server.exceptions.NoUserWithThisNameException;
import de.bht.comanche.server.exceptions.WrongPasswordException;

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
		lgUser.setTel("0301234567");
		lgUser.setOid(111111111);
		
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
		} catch (NoPersistentClassException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoQueryClassException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArgumentCountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArgumentTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		daUser.endTransaction(ok);
		assertEquals("Ralf", nameField);
	}
}
