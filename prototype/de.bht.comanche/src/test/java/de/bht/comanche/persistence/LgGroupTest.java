//package de.bht.comanche.persistence;
//
//import static org.junit.Assert.assertTrue;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.persistence.Persistence;
//
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
//import de.bht.comanche.logic.LgInvite;
//import de.bht.comanche.logic.LgLowLevelTransaction;
//import de.bht.comanche.logic.LgSurvey;
//import de.bht.comanche.logic.LgSurveyDummyFactory;
//import de.bht.comanche.logic.LgTransaction;
//import de.bht.comanche.logic.LgUser;
//
//public class LgGroupTest {
//	
//	@BeforeClass public static void initializeDb(){
//		Map<String, String> properties = new HashMap<String, String>(1);
//		properties.put("hibernate.hbm2ddl.auto", "create");
//		Persistence.createEntityManagerFactory(DaEmProvider.persistenceUnitName, properties);
//		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);
//	}
//	
//	@Test public void detachedTest() {
//		assertTrue("Persisting test users Alice & Bob", true);
//	}
//	
//}
