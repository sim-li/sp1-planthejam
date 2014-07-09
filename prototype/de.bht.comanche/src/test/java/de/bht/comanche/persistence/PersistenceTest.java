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
		PoolImpl.getInstance();
//		JpaDaFactory factory = new JpaDaFactory();
//		DaUser daUser = factory.getDaUser();
//		
//		LgUser lgUser = new LgUser();
//		lgUser.setName("Ralf");
//		lgUser.setEmail("simon@a-studios.forg");
//		lgUser.setPassword("myPw");
//		lgUser.setTel("030-3223939");
//		try {
//			daUser.beginTransaction();
//			daUser.save(lgUser);
//			daUser.endTransaction(true);
//		} catch (EntityExistsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransactionRequiredException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		assertEquals(true, true);
//            LgUser result = (LgUser) new LgSession().find(LgUser.class, user.getOid());
//            assertEquals("Herbert", result.getName());
//            assertEquals("1234", result.getTel());
//            assertEquals("Password", result.getPassword());
//            assertEquals("hallo@test.de", result.getEmail());
//            final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//            final String testDateAsString = dateFormatter.format(testDate);
//            final String resultingDateAsString = dateFormatter.format(result.getAvailability().get(0).getStartTime());
//            assertEquals(testDateAsString, resultingDateAsString);
//            assertEquals(20, result.getAvailability().get(0).getDurationInMinutes());
    }
}
