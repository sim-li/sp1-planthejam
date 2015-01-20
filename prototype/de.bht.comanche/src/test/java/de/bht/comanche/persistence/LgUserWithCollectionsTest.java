package de.bht.comanche.persistence;

import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaEmProvider;

/**
 * @author Simon Lischka
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgUserWithCollectionsTest {

	private LgUser alice;
	private LgTimePeriod timePeriod;
	private List<String> messages = new ArrayList<String>();
	private List<LgTimePeriod> variousTimePeriods = new ArrayList<LgTimePeriod>();
	private List<LgTimePeriod> persistedGeneralAvailabilty;
	
	@BeforeClass
	public static void resetDatabase() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(
				DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
	}

	@Before
	public void buildUp() {
		buildTestData();
		this.alice = (new TestTransaction<LgUser>("Alice") {
				@Override
				public LgUser execute() throws Exception {
					getSession().register(alice);
					return startSession();
				}
			}).getResult();
	}

	private void buildTestData() {
		this.alice = new LgUser().setName("Alice").setEmail("test@test.de")
				.setPassword("testtest");
		this.timePeriod = new LgTimePeriod().setDurationMins(10).setStartTime(
				new Date(8099));
	}
	
    @Test
    public void removeAndAddUpdateMessageTest() {
    	buildVariousMessages("Hello", "Kitty");
		saveMessages();
		updateMessges();
		assertTrue("Alice Kitty to UpdatedKitty", !this.alice.getMessages().contains("Kitty") 
			&& this.alice.getMessages().contains("UpdatedKitty"));
    }

	private void updateMessges() {
		retrieveMessages();
		this.messages.remove("Kitty");
		this.messages.add("UpdatedKitty");
		saveMessages();
		retrieveMessages();
	}

	@Test
	public void saveDemoMessagesTest() {
		buildVariousMessages("Hello", "Kitty");
		saveMessages();
		retrieveMessages();
		assertTrue("Alices persisted message contain 'Hello' and 'Kitty'",
				this.messages.contains("Hello") && this.messages.contains("Kitty")
		);
	}

	@Test
	public void saveTimePeriodTest() {
		buildVariousTimePeriods(20, 40, 60);
		saveGeneralAvailability();
		retrieveGeneralAvailability();
		for (LgTimePeriod t : persistedGeneralAvailabilty) {
			assertTrue(
					"Alices persisted timePeriods have a duraction of 20, 40 or 60",
					t.getDurationMins() == 20 || t.getDurationMins() == 40
							|| t.getDurationMins() == 60);
		}
	}

	private void fetchAlicesUserAccount() {
		this.alice  = (new TestTransaction<LgUser> ("Alice") {
			@Override
			/**
			 * Persist demo time periods to Alice's account
			 */
			public LgUser execute() throws Exception {
				final LgUser alice = startSession();
				return alice;
			}
		}).getResult();
	}

	private void retrieveMessages() {
		fetchAlicesUserAccount();
		this.messages = this.alice.getMessages();
	}
	
	private void retrieveGeneralAvailability() {
		fetchAlicesUserAccount();
		this.persistedGeneralAvailabilty = this.alice.getGeneralAvailability();
	}
	
	
	private void persistAlicesUserAccount() {
		new TestTransaction<LgUser> ("Alice") {
				@Override
				/**
				 * Persist demo time periods to Alice's account
				 */
				public LgUser execute() throws Exception {
					final LgUser alice = startSession();
					alice.save();
					return alice;
				}
			}.getResult();
	}
	
	private void saveGeneralAvailability() {
		this.alice.setGeneralAvailability(variousTimePeriods);
		persistAlicesUserAccount();
	}

	private void saveMessages() {
		this.alice.setMessages(messages);
		persistAlicesUserAccount();
	}
	
	private void buildVariousMessages(String ... messages) {
		for (int i = 0; i < messages.length; i++) {
			this.messages.add(messages[i]);
		}
	}
	
	private void buildVariousTimePeriods(int ... durations) {
		for (int i = 0; i < durations.length; i++) {
			this.variousTimePeriods.add(new LgTimePeriod().setStartTime(
				new Date()).setDurationMins(durations[i])
			);
		}
	}

}
