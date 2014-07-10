//package de.bht.comanche;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import org.eclipse.jetty.server.Authentication.User;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import de.bht.comanche.logic.LgUser;
//
//public class PersistenceIT {
//	private EntityManagerFactory entityManagerFactory;
//	
//    @Before 
//    public void setUp() {
//    		entityManagerFactory = Persistence.createEntityManagerFactory("planthejam.jpa");
//    		
//    }
//    
//    @Test public void saveAndReadUser() {
//    	EntityManager entityManager = entityManagerFactory.createEntityManager();
//    	entityManager.getTransaction().begin();
//    	LgUser user = new LgUser();
//    	user.setName("Name");
//    	user.setPassword("Password");
//    	//user.setBirthdate(new Date());
//        entityManager.persist(user);
//        entityManager.getTransaction().commit();
//    	entityManager.close();
//    	entityManager = entityManagerFactory.createEntityManager();
//    	entityManager.getTransaction().begin();
//    	List<LgUser> result = entityManager.createQuery("from User", LgUser.class).getResultList();
//    	for (LgUser userFromQuery: result) {
//    		assertEquals(userFromQuery.getName(), "Name");
//    		assertEquals(userFromQuery.getPassword(), "Password");
//    	}
//        entityManager.getTransaction().commit();
//        entityManager.close();
//	}
//    
//    @After
//    public void cleanUp() { 
//    	//
//    }
//    
//}
