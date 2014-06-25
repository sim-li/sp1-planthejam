package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgUser;

public class PersistenceTest {
	
   
    @Test public void saveUser() {
    	
    	final LgUser user = new LgUser(
			"Herbert",
			"1234",
			"hallo@test.de",
			"passwort"
		);
    	user.save();
    	try {
    		LgUser result = (LgUser) new LgSession().find(LgUser.class, user.getId());
			assertEquals("Herbert", result.getName());
    		assertEquals("1234", result.getTelephone());
    		assertEquals("passwort", result.getPassword());
    		assertEquals("hallo@test.de", result.getEmail());
		} catch (NoPersistentClassExc e) {
			e.printStackTrace();
		} catch (OidNotFoundExc e) {
			e.printStackTrace();
		}
	}
}
