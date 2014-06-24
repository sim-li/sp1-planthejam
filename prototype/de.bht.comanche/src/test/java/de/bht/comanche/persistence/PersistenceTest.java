package de.bht.comanche.persistence;
import static org.junit.Assert.assertEquals;




import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.jetty.server.Authentication.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.model.DmUser;

public class PersistenceTest {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
   
    @Test public void saveUser() {
    	final Date testDate = new Date(581140800L);
    	final DmUser user = new DmUser(
    			"Vorname", 
    	    	"Nachname", 
    	    	"Password", 
    	    	testDate);
    	
    	PoolImpl pool = PoolImpl.getInstance();
    	pool.save(user);
    	DmUser result;
    	try {
			result = (DmUser) pool.find(DmUser.class, user.getId());
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
