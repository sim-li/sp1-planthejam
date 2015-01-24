package de.bht.comanche.persistence;
import java.util.Date;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgUser;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;


public class LgSurveyTest {
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;

	@BeforeClass
	public static void resetDatabase() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(
				DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
	}

	@Before
	public void buildUp() {
		saveUserAccounts();
	}

	/**
	 * Saves Alice, Bob, and Carols user accounts in three separate transactions
	 * using the register method which is also used in the rest service.
	 */
	private void saveUserAccounts() {
		final LgUser testAccountBob = new LgUser().setName("Bob")
				.setEmail("test@test.de").setPassword("testtest");
		final LgUser testAccountAlice = new LgUser().setName("Alice")
				.setEmail("test@test.de").setPassword("testtest");
		final LgUser testAccountCarol = new LgUser().setName("Carol")
				.setEmail("carol@test.de").setPassword("testtest");
		alice = new TestTransaction<LgUser>("Alice") {
			@Override
			public LgUser execute() {
				return getSession().register(testAccountAlice);
			}
		}.getResult();
		bob = new TestTransaction<LgUser>("Bob") {
			@Override
			public LgUser execute() {
				return getSession().register(testAccountBob);
			}
		}.getResult();
		carol = new TestTransaction<LgUser>("Bob") {
			@Override
			public LgUser execute() {
				return getSession().register(testAccountCarol);
			}
		}.getResult();
	}

	/**
	 * Note: User with active transaction will automatically be added as host
	 * when calling saveSurvey. That's why we check for Alice even though we
	 * only add Bob and Carol to the survey.
	 */
	//ITERABLES FAIL
	@Test
	public void saveSurveyWithInvitesPariticipantsTest() {
		final LgSurvey surveyForEvaluation = saveTestSurveyWithParticipants(bob, carol);
		assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsExactly(
				"Alice", "Bob", "Carol");
	}

	//ITERABLES FAIL
	@Test
	public void saveSurveyWithInvitesHostAttributeTest() {
		final LgSurvey surveyForEvaluation = saveTestSurveyWithParticipants(bob, carol);
		assertThat(
				extractProperty("isHost")
						.from(surveyForEvaluation.getInvites()))
				.containsExactly(true, false, false);
	}
	
	@Test
	public void deleteParticipantTest() {
		final LgSurvey aSurvey = saveTestSurveyWithParticipants(bob, carol);
		aSurvey.removeParticipants(carol);
		final LgSurvey surveyForEvaluation = saveSurveyForAlice(aSurvey);
	    assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsExactly(
				"Alice", "Bob");
	}
	
	@Test
	//ITERABLES FAIL
	public void addParticipantTest() {
		final LgSurvey aSurvey = saveTestSurveyWithParticipants(bob);
		aSurvey.addParticipants(carol);
		final LgSurvey surveyForEvaluation = saveSurveyForAlice(aSurvey);
	    assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsExactly(
				"Alice", "Bob", "Carol");
	}

	@Test
	public void deleteSurveyTest() {
		final LgSurvey surveyForEvaluation = saveTestSurveyWithParticipants(bob, carol);
		new TestTransaction<Object>("Alice") {
			@Override
			public Object execute() {
				startSession().deleteSurvey(surveyForEvaluation.getOid());
				return null;
			}
		}.getResult();
	}
	
	/**
	 * Saves survey given list of participants.
	 * 
	 * @return Persisted survey with OID.
	 */
	private LgSurvey saveTestSurveyWithParticipants(LgUser ... participants) {
		final LgSurvey aSurvey = new LgSurvey().addParticipants(participants);
		final LgSurvey surveyForEvaluation = saveSurveyForAlice(aSurvey);
		return surveyForEvaluation;
	}

	@Test
	public void saveSurveyWithTimePeriodsTest() {
		final LgSurvey freshSurvey = new LgSurvey()
				.setPossibleTimePeriods(buildTimePeriods(20, 40, 60));
		final LgSurvey surveyForEvaluation = saveSurveyForAlice(freshSurvey);
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.contains(20, 40, 60);
	}

	@Test
	public void saveSurveyWithDeterminedTimePeriodTest() {
		final LgSurvey freshSurvey = new LgSurvey()
				.setDeterminedTimePeriod(new LgTimePeriod().setDurationMins(20)
						.setStartTime(new Date()));
		final LgSurvey surveyForEvaluation = saveSurveyForAlice(freshSurvey);
		assertThat(
				surveyForEvaluation.getDeterminedTimePeriod().getDurationMins())
				.isEqualTo(20);
	}

	/**
	 * Generates a hash set of time periods, inserts the current date stamp to
	 * fill the field with a value.
	 * 
	 * @param durations
	 *            List of durations as integer. Every duration will generate a
	 *            time period.
	 * @return A set of time periods
	 */
	private HashSet<LgTimePeriod> buildTimePeriods(int... durations) {
		final HashSet<LgTimePeriod> timePeriods = new HashSet<LgTimePeriod>();
		for (int i = 0; i < durations.length; i++) {
			timePeriods.add(new LgTimePeriod().setStartTime(new Date())
					.setDurationMins(durations[i]));
		}
		return timePeriods;
	}

	/**
	 * Saves a survey for Alice.
	 * 
	 * @param freshSurvey
	 *            Survey to be persisted
	 * @return The persisted survey with OID.
	 */
	private LgSurvey saveSurveyForAlice(final LgSurvey freshSurvey) {
		final LgSurvey persistedSurvey = new TestTransaction<LgSurvey>("Alice") {
			@Override
			public LgSurvey execute() {
				return startSession().saveSurvey(freshSurvey);
			}
		}.getResult();
		return persistedSurvey;
	}

	//ITERABLES FAIL
	@Test
	public void updateSurveyByModifyingTimePeriods() {
		testTimePeriodsUpdateWith(20, 40, 80);
	}

	@Test
	public void updateSurveyByDeletingOneTimePeriods() {
		testTimePeriodsUpdateWith(20, 40);
	}

	@Test
	public void updateSurveyByDeletingTwoTimePeriods() {
		testTimePeriodsUpdateWith(20);
	}

	@Test
	public void updateSurveyByDeletingAllTimePeriods() {
		testTimePeriodsUpdateWith();
	}

	/**
	 * Checks that the durations of the time periods were updated successfully
	 * and the exact number of durations are contained in the survey.
	 * 
	 * Test will try to modify timePeriods with durations [20, 40, 60] to given
	 * values and check against database.
	 * 
	 * An empty parameter list will persist an empty collection.
	 * 
	 * @param durationUpdates
	 *            A series of durations
	 */
	private void testTimePeriodsUpdateWith(int... durationUpdates) {
		final LgSurvey freshSurvey = new LgSurvey()
				.setPossibleTimePeriods(buildTimePeriods(20, 40, 60));
		saveSurveyForAlice(freshSurvey);
		final LgSurvey updatedSurvey = new LgSurvey()
				.setPossibleTimePeriods(buildTimePeriods(durationUpdates));
		final LgSurvey surveyForEvaluation = new TestTransaction<LgSurvey>(
				"Alice") {
			@Override
			public LgSurvey execute() {
				return startSession().updateSurvey(updatedSurvey);
			}
		}.getResult();
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.containsExactly(durationUpdates);
	}

	/**
	 * Note: Delete has to be executed in separate transactions. Only when a
	 * transaction was executed, the contained invites and surveys are deleted.
	 */
	@After
	public void deleteTestData() {
		deleteAccountFor("Alice", "Bob", "Carol");
	}

	/**
	 * Runs a transaction with a delete command for every given user.
	 * 
	 * @param users
	 *            Users to be deleted
	 */
	public void deleteAccountFor(String... users) {
		for (String user : users) {
			new TestTransaction<LgUser>(user) {
				@Override
				public LgUser execute() {
					final LgUser user = startSession();
					user.deleteThisAccount();
					return user;
				}
			}.getResult();
		}
	}
}
