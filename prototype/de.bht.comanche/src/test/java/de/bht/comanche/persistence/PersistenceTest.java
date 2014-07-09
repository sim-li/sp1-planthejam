package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import org.junit.Test;

import de.bht.comanche.logic.LgSession;
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
}
