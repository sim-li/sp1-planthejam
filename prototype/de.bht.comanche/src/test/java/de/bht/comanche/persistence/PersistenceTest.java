package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgUser;

public class PersistenceTest {
	
   
    @Test public void saveUser() {
    	final Date testDate = new Date(581140800L);
    	final LgUser user = new LgUser(
    			"Vorname", 
    	    	"Nachname", 
    	    	"Password", 
    	    	testDate);
    	user.save();
    	try {
    		LgUser result = (LgUser) new LgSession().find(LgUser.class, user.getId());
			assertEquals("Vorname", result.getFirstName());
    		assertEquals("Nachname", result.getLastName());
    		assertEquals("Password", result.getPassword());
    		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    		final String testDateAsString = dateFormatter.format(testDate);
    		final String resultingDateAsString = dateFormatter.format(result.getBirthdate());
    		assertEquals(testDateAsString, resultingDateAsString);
		} catch (NoPersistentClassExc e) {
			e.printStackTrace();
		} catch (OidNotFoundExc e) {
			e.printStackTrace();
		}
	}
}
