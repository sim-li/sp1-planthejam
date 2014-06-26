package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import de.bht.comanche.logic.DtTimeperiod;
import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgUser;

public class PersistenceTest {
	
   
    @Test public void saveUser() {
    	final Date testDate = new Date(581140800L);
    	final ArrayList<DtTimeperiod> timeperiods = new ArrayList<DtTimeperiod>();
    	timeperiods.add(new DtTimeperiod(
				testDate,
				20
	    ));
    	
    	final LgUser user = new LgUser(
			"Herbert",
			"1234",
			"hallo@test.de",
			"passwort",
			timeperiods
		);
    	
    	
    	user.save();
    	
    	try {
    		LgUser result = (LgUser) new LgSession().find(LgUser.class, user.getId());
			assertEquals("Herbert", result.getName());
    		assertEquals("1234", result.getTelephone());
    		assertEquals("Password", result.getPassword());
    		assertEquals("hallo@test.de", result.getEmail());
    		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    		final String testDateAsString = dateFormatter.format(testDate);
    		final String resultingDateAsString = dateFormatter.format(result.getAvailability().get(0).getStartTime());
    		assertEquals(testDateAsString, resultingDateAsString);
    		assertEquals(20, result.getAvailability().get(0).getDurationInMinutes());
		} catch (NoPersistentClassExc e) {
			e.printStackTrace();
		} catch (OidNotFoundExc e) {
			e.printStackTrace();
		}
	}
}
