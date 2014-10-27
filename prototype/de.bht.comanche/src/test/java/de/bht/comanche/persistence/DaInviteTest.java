package de.bht.comanche.persistence;

import static org.junit.Assert.assertTrue;

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
	final String userName0 = "ALICE";
	final String userName1 = "BOB";
	private static final boolean THROW_STACKTRACE = true;
	private static final boolean ROLLBACK = false;
	private static DaUser daUser;
	private static DaSurvey daSurvey;
	private static DaFactory daFactory;
	private static DaInvite daInvite;
	private LgSurvey survey;
	private static DaPool pool;
	
	@BeforeClass public static void initializeDb(){
		daFactory = new DaFactoryJpaImpl();
		daUser = daFactory.getDaUser();
		daSurvey = daFactory.getDaSurvey();
		daInvite = daFactory.getDaInvite();
		pool = daUser.getPool();
		daUser.setPool(pool);
		daSurvey.setPool(pool);
		daInvite.setPool(pool);
		
		boolean success = new LgLowLevelTransaction(THROW_STACKTRACE) {
			public void executeWithThrows() throws Exception {
				DaTestUtils persistenceUtils = new DaTestUtils(daUser.getPool());
				persistenceUtils.initializeDb();
			}
		}.execute();
		assertTrue("Initializing DB", success);
	}
	
	@Before public void setUp() {
		final LgUser alice = new LgUser();
		final LgUser bob = new LgUser();
		alice.setName("Alice");
		bob.setName("Bob");
		boolean success = new LgTransactionWithStackTrace<LgUser>(pool, true, ROLLBACK) {
			public void executeWithThrows() throws Exception {
					daUser.save(alice);
					daUser.save(bob);
			}
		}.execute();
		assertTrue("Persisting test users Alice & Bob", success);
	}
	
	@Test public void detachedTest() {
		LgSurveyDummyFactory surveyFactory = new LgSurveyDummyFactory();
		
		final LgInvite receivedInvite = new LgInvite();
		survey = surveyFactory.getSurvey0();
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
