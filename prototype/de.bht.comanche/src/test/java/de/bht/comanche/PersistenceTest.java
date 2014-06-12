package de.bht.comanche;
import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersistenceTest {
	private EntityManager entityManager;
	
    @Before 
    public void setUp() {
    	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("planthejam.jpa");
    	entityManager = entityManagerFactory.createEntityManager();
    	entityManager.getTransaction().begin();
    }
    
    @Test public void sayHelloWorldsaysHelloWorld() {
        assertEquals("Hello World!", "Hello World!");
	}
    
    @After
    public void cleanUp() { 
    	entityManager.getTransaction().commit();
    	entityManager.close();
    }
    
}
