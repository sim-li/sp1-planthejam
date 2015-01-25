package de.bht.comanche.persistence;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import de.bht.comanche.logic.LgStatus;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgSurveyType;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgTimeUnit;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.persistence.DaHibernateJpaPool.DaFindOneByKeyExc;

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

	@Test
	public void getSurveyByOidTest() {
		final Date aDate = new Date();
		final LgTimePeriod aTimePeriod = new LgTimePeriod().setDurationMins(30).setStartTime(aDate);
		final Set<LgTimePeriod> severalTimePeriods = buildTimePeriods(20,30,40); 
		final LgSurvey aSurvey = new LgSurvey()
			.setAlgoChecked(false)
			.setDeadline(aDate)
			.setDescription("My description")
			.setDeterminedTimePeriod(aTimePeriod)
			.setFrequencyDist(30)
			.setFrequencyUnit(LgTimeUnit.MONTH)
			.addParticipants(bob, carol)
			.setName("My test survey")
		    .setPossibleTimePeriods(severalTimePeriods)
		    .setSuccess(LgStatus.UNDECIDED)
		    .setSurveyDurationMins(30)
		    .setType(LgSurveyType.ONE_TIME);
		final LgSurvey surveyWithOid = saveSurvey(aSurvey);
		final LgSurvey surveyEval = new TestTransaction<LgSurvey>(
				"Alice") {
			@Override
			public LgSurvey execute() {
				return startSession().getSurvey(surveyWithOid.getOid());
			}
		}.getResult();
		//Comment: Whats up with bool getters: Do these naming conventions work for JSONization? -- SIM, 24. JAN 2015
		assertThat(surveyEval.getAlgoChecked()).isFalse();
		assertThat(surveyEval.getDeadline()).isEqualTo(aDate);
		assertThat(surveyEval.getDescription()).isEqualTo("My description");
		assertThat(surveyEval.getDeterminedTimePeriod()).isEqualTo(aTimePeriod);
		assertThat(surveyEval.getFrequencyDist()).isEqualTo(30);
		assertThat(surveyEval.getFrequencyUnit()).isEqualTo(LgTimeUnit.MONTH);
		assertThat(surveyEval.getParticipants()).containsOnly(alice, bob, carol);
		assertThat(surveyEval.getHost()).isEqualTo(alice);
		assertThat(surveyEval.getName()).isEqualTo("My test survey");
		assertThat(surveyEval.getPossibleTimePeriods()).isEqualTo(severalTimePeriods);
		assertThat(surveyEval.getSuccess()).isEqualTo(LgStatus.UNDECIDED);
		assertThat(surveyEval.getSurveyDurationMins()).isEqualTo(30);
		assertThat(surveyEval.getType()).isEqualTo(LgSurveyType.ONE_TIME);
	}
	
	/**
	 * Note: The host is also returned with the getInvites method.
	 */
	@Test
	public void getInvitesForSurveyByOidTest() {
		final LgSurvey surveyWithOid = saveSurvey(new LgSurvey().addParticipants(bob, carol));
		final List<LgInvite> invites = new TestTransaction<List<LgInvite>>("Alice") {
			@Override
			public List<LgInvite> execute() {
				return startSession().getInvitesForSurvey(surveyWithOid.getOid());
			}
		}.getResult();
		assertThat(
				extractProperty("user.name").from(
						invites)).containsOnly(
				"Alice", "Bob", "Carol");
	}
	
	@Test
	public void getSurveysTest() {
		saveSurvey(new LgSurvey().setName("Survey1"));
		saveSurvey(new LgSurvey().setName("Survey2"));
		saveSurvey(new LgSurvey().setName("Survey3"));
		List<LgSurvey> surveysForEvaluation = new TestTransaction<List<LgSurvey>>("Alice") {
			@Override
			public List<LgSurvey> execute() {
				return startSession().getSurveys();
			}
		}.getResult();
		assertThat(
				extractProperty("name").from(
						surveysForEvaluation)).
						contains(
				"Survey1", "Survey2", "Survey3");
	}
	
	public List<LgSurvey> createSurveysWithNames(String ... names) {
		List<LgSurvey> surveys = new LinkedList<LgSurvey>();
		for (String name : names) {
			surveys.add(new LgSurvey().setName(name).addHost(alice).addParticipants(bob, carol));
		}
		return surveys;
	}
	
	/**
	 * Note: User with active transaction will automatically be added as host
	 * when calling saveSurvey. That's why we check for Alice even though we
	 * only add Bob and Carol to the survey.
	 */
	@Test
	public void saveSurveyWithInvitesPariticipantsTest() {
		final LgSurvey surveyForEvaluation = saveSurvey(new LgSurvey().addParticipants(bob, carol));
		assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).
						containsOnly(
				"Alice", "Bob", "Carol");
	}

	@Test
	public void saveSurveyWithInvitesHostAttributeTest() {
		final LgSurvey surveyForEvaluation = saveSurvey(new LgSurvey().addParticipants(bob, carol));
		assertThat(
				extractProperty("isHost")
						.from(surveyForEvaluation.getInvites()))
				.containsOnly(true, false, false);
	}
	
	@Test
	public void deleteParticipantTest() {
		final LgSurvey aSurvey = saveSurvey(new LgSurvey().addParticipants(bob, carol));
		aSurvey.removeParticipants(carol);
		final LgSurvey surveyForEvaluation = saveSurvey(aSurvey);
	    assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsOnly(
				"Alice", "Bob");
	}
	
	@Test
	public void addParticipantTest() {
		final LgSurvey aSurvey = saveSurvey(new LgSurvey().addParticipants(bob));
		aSurvey.addParticipants(carol);
		final LgSurvey surveyForEvaluation = saveSurvey(aSurvey);
	    assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsOnly(
				"Alice", "Bob", "Carol");
	}

	
	@Test
	public void deleteSurveyTest() {
		final LgSurvey surveyForEvaluation = saveSurvey(new LgSurvey()
			.addParticipants(bob, carol)
			.setPossibleTimePeriods(buildTimePeriods(20, 30, 40))
			.setDeterminedTimePeriod(new LgTimePeriod().setDurationMins(50).setStartTime(new Date())));
		new TestTransaction<Object>("Alice") {
			@Override
			public Object execute() {
				startSession().deleteSurvey(surveyForEvaluation.getOid());
				return null;
			}
		}.getResult();
		final Boolean foundDeletedObj = new TestTransaction<Boolean>(
				"Alice") {
			@Override
			public Boolean execute() {
				Boolean foundDeletedObj = null;
				try {
					startSession().findOneByKey(LgSurvey.class, "oid",
							surveyForEvaluation.getOid());
					foundDeletedObj = true;
				} catch (DaFindOneByKeyExc ex) {
					foundDeletedObj = foundDeletedObj == null ? false : foundDeletedObj;
				}
				for (final LgInvite invite : surveyForEvaluation.getInvites()) {
					try {
						startSession().findOneByKey(LgInvite.class, "oid",
								invite.getOid());
						foundDeletedObj = true;
					} catch (DaFindOneByKeyExc ex) {
						foundDeletedObj = foundDeletedObj == null ? false : foundDeletedObj;
					}
				}
				//TODO: Implement this, not really urgent.
//				for (final LgTimePeriod timePeriod : surveyForEvaluation.getPossibleTimePeriods()) {
//					try {
//						startSession().findManyByQuery("select o from " + LgTimePeriod.class);
//						foundDeletedObj = true;
//					} catch (DaFindOneByKeyExc ex) {
//						foundDeletedObj = foundDeletedObj == null ? false : foundDeletedObj;
//					}
//				}
				return false;
			}
		}.getResult();
		assertThat(foundDeletedObj).isEqualTo(false);
	}
	
	@Test
	public void saveSurveyWithTimePeriodsTest() {
		final LgSurvey freshSurvey = new LgSurvey()
				.setPossibleTimePeriods(buildTimePeriods(20, 40, 60));
		final LgSurvey surveyForEvaluation = saveSurvey(freshSurvey);
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
		final LgSurvey surveyForEvaluation = saveSurvey(freshSurvey);
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
	 * Saves a survey for Alice, which is our standard account for these
	 * operations
	 *  
	 * @param freshSurvey
	 *            Survey to be persisted
	 * @return The persisted survey with OID.
	 */
	private LgSurvey saveSurvey(final LgSurvey freshSurvey) {
		final LgSurvey persistedSurvey = new TestTransaction<LgSurvey>("Alice") {
			@Override
			public LgSurvey execute() {
				return startSession().saveSurvey(freshSurvey);
			}
		}.getResult();
		return persistedSurvey;
	}

	@Test
	public void updateSurveyByModifyingTimePeriods() {
		final LgSurvey surveyForEvaluation = updateTimePeriodsWith(20, 40, 80);
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.containsOnly(20, 40,80);
	}

	@Test
	public void updateSurveyByDeletingOneTimePeriods() {
		final LgSurvey surveyForEvaluation = updateTimePeriodsWith(20, 40);
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.containsOnly(20, 40);
	}

	@Test
	public void updateSurveyByDeletingTwoTimePeriods() {
		final LgSurvey surveyForEvaluation = updateTimePeriodsWith(20);
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.containsOnly(20);
	}

	@Test
	public void updateSurveyByDeletingAllTimePeriods() {
		final LgSurvey surveyForEvaluation = updateTimePeriodsWith();
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.isEmpty();
	}

	/**
	 * Updates the time periods [20, 40, 60] of a survey with the 
	 * given durations.
	 * 
	 * @param durationUpdates
	 *            A series of durations
	 * @return Updated survey with new durations for checking with assertions
	 */
	private LgSurvey updateTimePeriodsWith(int... durationUpdates) {
		final LgSurvey freshSurvey = new LgSurvey()
				.setPossibleTimePeriods(buildTimePeriods(20, 40, 60));
		final LgSurvey updatedSurvey = saveSurvey(freshSurvey);
		updatedSurvey.setPossibleTimePeriods(buildTimePeriods(durationUpdates));
		final LgSurvey surveyForEvaluation = new TestTransaction<LgSurvey>(
				"Alice") {
			@Override
			public LgSurvey execute() {
				return startSession().updateSurvey(updatedSurvey);
			}
		}.getResult();
		return surveyForEvaluation;
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
