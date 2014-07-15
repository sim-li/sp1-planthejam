package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import antlr.collections.List;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.exceptions.PersistenceException;
import de.bht.comanche.testresources.logic.SurveyFactory;
import de.bht.comanche.testresources.logic.UserFactory;
import de.bht.comanche.testresources.persistence.PersistenceUtils;
import de.bht.comanche.testresources.server.LowLevelTransaction;
import de.bht.comanche.testresources.server.TransactionWithStackTrace;

public class DaSurveyTest {
	final String userName0 = "ALICE";
	final String userName1 = "BOB";
	private static final boolean THROW_STACKTRACE = true;
	private static final boolean ROLLBACK = false;
	private static DaUser daUser;
	private static DaSurvey daSurvey;
	private static DaFactory daFactory;
	private LgUser alice;
	private LgUser bob;
	private LgSurvey survey0;
	private LgInvite invite0;
	private LgInvite invite1;
	
	@BeforeClass public static void initializeDb() throws PersistenceException {
		daFactory = new JpaDaFactory();
		daSurvey = daFactory.getDaSurvey();
		daUser = daFactory.getDaUser();
		daUser.setPool(daSurvey.getPool());
		
		boolean success = new LowLevelTransaction(THROW_STACKTRACE) {
			public void executeWithThrows() throws Exception {
				PersistenceUtils persistenceUtils = new PersistenceUtils(daUser.getPool());
				persistenceUtils.initializeDb();
			}
		}.execute();
		assertTrue("Initializing DB", success);
	}
	
	@Before public void setUp() {
		UserFactory userFactory = new UserFactory();
		alice = userFactory.getUser0();
		bob = userFactory.getUser1();
		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), true, ROLLBACK) {
			public void executeWithThrows() throws Exception {
					daUser.save(alice);
					daUser.save(bob);
					SurveyFactory surveyFactory = new SurveyFactory();
					survey0 = surveyFactory.getSurvey0();
					invite0 = new LgInvite();
					invite1 = new LgInvite();
					invite0.setUser(alice);
					invite1.setUser(bob);
					invite0.setHost(true);
					invite1.setHost(false);
					invite0.setSurvey(survey0);
					invite1.setSurvey(survey0);
					daSurvey.save(survey0);
					daSurvey.getPool().save(invite1);
					daSurvey.getPool().save(invite0);
					daUser.save(alice);
					daUser.save(bob);
			}
		}.execute();
		assertTrue("Persisting test users Alice & Bob", success);
	}
	
	@Test 
	public void readSurveysTest() {
		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), THROW_STACKTRACE, ROLLBACK) {
			public void executeWithThrows() throws Exception {
				LgUser aliceFromDb = daUser.findByName(alice.getName()).get(0);
				LgUser bobFromDb = daUser.findByName(bob.getName()).get(0);
				assertUser(userName0, alice, aliceFromDb);
				assertUser(userName1, bob, bobFromDb);
				assertEquals("Check Alices first invite (BY ID):", invite0.getOid(), aliceFromDb.getInvites().get(0).getOid());
				assertEquals("Check Bobs first invite (BY ID):", invite1.getOid(),  bobFromDb.getInvites().get(0).getOid());
				assertEquals("Check Alices survey (BY ID):", survey0.getOid(),  aliceFromDb.getInvites().get(0).getSurvey().getOid());
				assertEquals("Check Bobs survey (BY ID):",  survey0.getOid(), bobFromDb.getInvites().get(0).getSurvey().getOid());
			}
		}.execute();
		assertTrue("DA - operations with exceptions (see TransactionObject)", success);
    }

	public void assertUser(String userName, LgUser user, LgUser userFromDb) {
		assertEquals(userName + " > NAME", user.getName(), userFromDb.getName());
		assertEquals(userName + " > EMAIL", user.getEmail(), userFromDb.getEmail());
		assertEquals(userName + " > TEL", user.getTel(), userFromDb.getTel());
		assertEquals(userName + " > PASSWORD", user.getPassword(), userFromDb.getPassword());
	}
	
	
//	@After public void tearDown() {
//		final DaUser daUser = daFactory.getDaUser();
//		boolean success = new TransactionWithStackTrace<LgUser>(daUser.getPool(), true, ROLLBACK) {
//			public void executeWithThrows() throws Exception {
//				LgUser aliceFromDb = daUser.find(alice.getOid());
//				LgUser bobFromDb = daUser.find(bob.getOid());
//				daUser.delete(aliceFromDb);
//				daUser.delete(bobFromDb);
//			}
//		}.execute();
//		assertTrue("Deleting Alice & Bob: |Alice ID|> " + alice.getOid() + " |Bob ID|> " + bob.getOid(), success);
//		PersistenceUtils pu = new PersistenceUtils(daUser.getPool());
//	}
//	
}
