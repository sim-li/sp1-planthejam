package de.bht.comanche.persistence;
import static org.junit.Assert.assertEquals;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.model.DmUser;

public class PersistenceTest {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
    @Before 
    public void setUp() {
    		entityManagerFactory = Persistence.createEntityManagerFactory("planthejam.jpa");
    		entityManager = entityManagerFactory.createEntityManager();
    		entityManager.getTransaction().begin();
    		Query q = entityManager.createQuery("DELETE FROM User");
    		q.executeUpdate();
        	entityManager.getTransaction().commit();
        	entityManager.getTransaction().begin();
    }
    
    @Test public void saveUser() {
    	DmUser user = new DmUser();
    	final Date testDate = new Date(581140800L);
    	final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    	final String testDateAsString = dateFormatter.format(testDate);
    	user.setFirstName("Vorname");
    	user.setLastName("Nachname");
    	user.setPassword("Password");
    	user.setBirthdate(testDate);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    	entityManager.getTransaction().begin();
    	List<DmUser> result = entityManager.createQuery("from User", DmUser.class).getResultList();
    	for (DmUser userFromQuery: result) {
    		assertEquals(userFromQuery.getFirstName(), "Vorname");
    		assertEquals(userFromQuery.getLastName(), "Nachname");
    		assertEquals(userFromQuery.getPassword(), "Password");
    		String resultingDateAsString = dateFormatter.format(userFromQuery.getBirthdate());
    		assertEquals(testDateAsString, resultingDateAsString);
    	}
	}
    
    @After
    public void cleanUp() { 
    	entityManager.getTransaction().commit();
        entityManager.close();
    }
    
}
