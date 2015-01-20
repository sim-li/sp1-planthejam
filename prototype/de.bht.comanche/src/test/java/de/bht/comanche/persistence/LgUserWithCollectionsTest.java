package de.bht.comanche.persistence;

import static multex.MultexUtil.create;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgTransaction;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaEmProvider;
import de.bht.comanche.rest.ReUserService.RestUserUpdateFailure;

/**
 * @author Maxim Novichkov;
 * @author Simon Lischka
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgUserWithCollectionsTest {

	private LgUser alice;
	private LgSession session;
	private LgTimePeriod timePeriod;
	private List<LgTimePeriod> variousTimePeriods = new ArrayList<LgTimePeriod>();
	private List<LgTimePeriod> persistedGeneralAvailabilty;
	
	@BeforeClass
	public static void resetDatabase() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(
				DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
	}

	/**
	 * Registers and logs in user Alice
	 */
	@Before
	public void buildUp() {
		buildTestData();
	    alice = (new TestTransaction<LgUser>("Alice") {
				@Override
				public LgUser execute() throws Exception {
					getSession().register(alice);
					return startSession();
				}
			}).getResult();
	}

	private void buildTestData() {
		alice = new LgUser().setName("Alice").setEmail("test@test.de")
				.setPassword("testtest");
		this.timePeriod = new LgTimePeriod().setDurationMins(10).setStartTime(
				new Date(8099));
	}


    /**
     * This test actually includes the checking of data, thus the part before the commit
     * is what would be called in a Rest Service.
     */
    @Test
    public void saveMessagesTest() {
        saveDemoMessages();
        commitAndRestartTransaction();
        alice = session.startFor("Alice");
        assertTrue("Alice has message 'Hello'", alice.getMessages().contains("Hello"));
        assertTrue("Alice has message 'Kitty'", alice.getMessages().contains("Kitty"));
    }

    @Test
    public void removeAndAddUpdateMessageTest() {
    	 saveDemoMessages();
    	 commitAndRestartTransaction();
    	 alice = session.startFor("Alice");
    	 alice.getMessages().remove("Hello");
    	 alice.getMessages().add("Update");
    	 commitAndRestartTransaction();
    	 alice = session.startFor("Alice");
    	 assertTrue("Alice has Updated message", alice.getMessages().contains("Update"));
    	 assertFalse("Alice does not have old message", alice.getMessages().contains("Hello"));
    }

	@Test
	public void saveDemoMessages() {
		commitAndRestartTransaction();
		alice = session.startFor("Alice");
		List<String> messages = new ArrayList<String>();
		messages.add("Hello");
		messages.add("Kitty");
		alice.setMessages(messages);
		alice.save();
		assertTrue(true);
		commitAndRestartTransaction();
	}

	@Test
	public void saveTimePeriodTest() {
		buildVariousTimePeriods(20, 40, 60);
		persistGeneralAvailability();
		retrieveGeneralAvailability();
		for (LgTimePeriod t : persistedGeneralAvailabilty) {
			assertTrue(
					"Alices persisted timePeriods have a duraction of 20, 40 or 60",
					t.getDurationMins() == 20 || t.getDurationMins() == 40
							|| t.getDurationMins() == 60);
		}
	}

	private void retrieveGeneralAvailability() {
		persistedGeneralAvailabilty  = (new TestTransaction<List<LgTimePeriod>> ("Alice") {
			@Override
			/**
			 * Persist demo time periods to Alice's account
			 */
			public List<LgTimePeriod> execute() throws Exception {
				final LgUser alice = startSession();
				return alice.getGeneralAvailability();
			}
		}).getResult();
	}

	private void persistGeneralAvailability() {
		alice.setGeneralAvailability(variousTimePeriods);
		alice = (new TestTransaction<LgUser> ("Alice") {
				@Override
				/**
				 * Persist demo time periods to Alice's account
				 */
				public LgUser execute() throws Exception {
					final LgUser alice = startSession();
					alice.save();
					return alice;
				}
			}).getResult();
	}

	private void buildVariousTimePeriods(int ... durations) {
		for (int i = 0; i < durations.length; i++) {
			variousTimePeriods.add(new LgTimePeriod().setStartTime(
				new Date()).setDurationMins(durations[i])
			);
		}
	}

	@After
	public void tearDown() {
		// alice.deleteThisAccount();
		// endTransaction();
	}

	private void beginTransaction() {
		session = new LgSession();
		session.getApplication().beginTransaction();
	}

	private void endTransaction() {
		session.getApplication().endTransaction(true);
	}

	private void commitAndRestartTransaction() {
		endTransaction();
		beginTransaction();
	}
}
