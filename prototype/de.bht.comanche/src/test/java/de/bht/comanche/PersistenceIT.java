package de.bht.comanche;
import static org.junit.Assert.assertEquals;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersistenceIT {
	private EntityManagerFactory entityManagerFactory;
	
    @Before 
    public void setUp() {
    		entityManagerFactory = Persistence.createEntityManagerFactory("planthejam.jpa");
    		
    }
    
    @Test public void saveAndReadUser() {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	entityManager.getTransaction().begin();
    	User user = new User();
    	user.setFirstName("Vorname");
    	user.setLastName("Nachname");
    	user.setPassword("Password");
    	//user.setBirthdate(new Date());
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    	entityManager.close();
    	entityManager = entityManagerFactory.createEntityManager();
    	entityManager.getTransaction().begin();
    	List<User> result = entityManager.createQuery("from User", User.class).getResultList();
    	for (User userFromQuery: result) {
    		assertEquals(userFromQuery.getFirstName(), "Vorname");
    		assertEquals(userFromQuery.getLastName(), "Nachname");
    		assertEquals(userFromQuery.getPassword(), "Password");
    	}
        entityManager.getTransaction().commit();
        entityManager.close();
	}
    
    @After
    public void cleanUp() { 
    	//
    }
    
}
