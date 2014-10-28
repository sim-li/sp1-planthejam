package de.bht.comanche.persistence;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgLowLevelTransaction;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgSurveyDummyFactory;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgTransactionWithStackTrace;
import de.bht.comanche.logic.LgUser;

public class DaInviteTest {
	
	@BeforeClass public static void initializeDb(){
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaHibernateJpaPool.persistenceUnitName, properties);
		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);
	}
	
	@Before public void setUp() {
		final LgUser alice = new LgUser();
		final LgUser bob = new LgUser();
		alice.setName("Alice");
		bob.setName("Bob");
		assertTrue("Persisting test users Alice & Bob", success);
	}
	
	@Test public void detachedTest() {
		LgSurveyDummyFactory surveyFactory = new LgSurveyDummyFactory();
		final LgInvite receivedInvite = new LgInvite();
		final LgSurvey survey = surveyFactory.getSurvey0();
		receivedInvite.setSurvey(survey);
		
		
		final LgUser alice = new LgUser(1);
		alice.setName("Alice");
		receivedInvite.setUser(alice);
	    assertTrue("Invite not null", !(receivedInvite == null));
	    
		alice.addInvite(receivedInvite);
		survey.addInvite(receivedInvite);
		
		
		DaFactory factory = new DaFactoryJpaImpl();
		
			final DaInvite daInvite = factory.getDaInvite();
			final DaUser daUser = factory.getDaUser();
			final DaSurvey daSurvey = factory.getDaSurvey();
			daUser.setPool(daInvite.getPool());
			daSurvey.setPool(daInvite.getPool());
			new LgTransaction<LgInvite>(daInvite.getPool()) {
				public LgInvite executeWithThrows() throws multex.Exc {
					LgInvite existingInvite = daInvite.find(receivedInvite.getOid());
				    if (existingInvite == null) {
				    	daInvite.save(receivedInvite);
				    } else {
				    	LgSurvey existingSurvey = receivedInvite.getSurvey();
				    	LgUser existingUser = receivedInvite.getUser();
				        existingInvite.setSurvey(existingSurvey);
				        existingSurvey.addInvite(existingInvite); 
				        existingInvite.setUser(existingUser);
				        existingUser.addInvite(existingInvite);
				    }
//				    
//				    
//					} catch (Exception ex) {
//						throw create(DaInviteNotSavedExc.class, ex, createTimeStamp(), newInviteFromClient.getOid(), 
//								newInviteFromClient.getUser().getOid());
//					}
					return existingInvite;
				}
			}.execute();
		
	}
}
