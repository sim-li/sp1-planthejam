package de.bht.comanche.persistence;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgStatus;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.logic.LgMessage;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * 
 * Tests the whole process of inviting participants and evaluating a survey.
 * 
 * @author Simon Lischka
 *
 */
public class LgSurveyEvaluationTest {
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;
	private TestUtils testUtils = new TestUtils();
	private LgSurvey surveyForEvaluation;
	private final String A_COMMON_TIMEPERIOD = "01.05.1999/21:30 -> 01.05.1999/22:30";
	
	@BeforeClass
	public static void init() {
		TestUtils.resetJPADatabase();
	}

	@Before
	public void buildUp() {
		alice = testUtils.registerTestUser("Alice");
		bob = testUtils.registerTestUser("Bob");
		carol = testUtils.registerTestUser("Carol");
	}

	private void saveSurvey() {
		final LgSurvey testSurvey = new LgSurvey()
				.setName("Go Skiing")
				.setPossibleTimePeriods(
						testUtils.buildTimePeriods(
								"01.05.1999/21:30 -> 01.05.1999/22:30",
								"30.01.1986/20:30 -> 30.01.1986/22:30",
								"08.09.2005/00:30 -> 08.09.2005/01:30"))
				.setDeadline(testUtils.buildDate("30.01.2012/22:30" // This
																	// deadline
																	// definately
																	// is over
						)).addParticipants(bob, carol);
		this.surveyForEvaluation = testUtils.saveSurveyFor("Alice", testSurvey);
		// Bob and carol seem to have time at the 01.05.99
		saveInviteFor(
				"Bob",
				getSkiInviteFor("Bob").setConcreteAvailability(
						testUtils.buildTimePeriods(
								"01.05.1999/21:30 -> 01.05.1999/22:30",
								"30.01.1986/22:30 -> 30.01.1986/23:30",
								"08.09.2005/01:30 -> 08.09.2005/02:30")
						
					)
					.setIgnored(LgStatus.NO));
		saveInviteFor(
				"Carol",
				getSkiInviteFor("Carol").setConcreteAvailability(
						testUtils.buildTimePeriods(
								"01.05.1999/21:30 -> 01.05.1999/22:30",
								"30.01.1986/00:30 -> 30.01.1986/01:30",
								"08.09.2005/02:30 -> 08.09.2005/03:30"))
								.setIgnored(LgStatus.NO));
	}

	// If no match default TP is not set correctly.
	@Test
	public void persistDefaultTimePeriodCorrectlyWhenNoMatch () {
		final LgSurvey testSurvey = new LgSurvey()
		.setName("Go Skiing")
		.setPossibleTimePeriods(
				testUtils.buildTimePeriods(
						"01.05.1999/21:30 -> 01.05.1999/22:30",
						"30.01.1986/20:30 -> 30.01.1986/22:30",
						"08.09.2005/00:30 -> 08.09.2005/01:30"))
		.setDeadline(testUtils.buildDate("30.01.2012/22:30"
				)).addParticipants(bob, carol);
			this.surveyForEvaluation = testUtils.saveSurveyFor("Alice", testSurvey);
			// Bob and carol seem to have time at the 01.05.99
			saveInviteFor(
					"Bob",
					getSkiInviteFor("Bob").setConcreteAvailability(
							testUtils.buildTimePeriods(
									"01.05.1999/0:30 -> 01.05.1999/22:30",
									"30.01.1986/22:30 -> 30.01.1986/23:30",
									"08.09.2005/01:30 -> 08.09.2005/02:30")
							
						)
						.setIgnored(LgStatus.NO));
			saveInviteFor(
					"Carol",
					getSkiInviteFor("Carol").setConcreteAvailability(
							testUtils.buildTimePeriods(
									"01.05.1999/02:30 -> 01.05.1999/22:30",
									"30.01.1986/00:30 -> 30.01.1986/01:30",
									"08.09.2005/02:30 -> 08.09.2005/03:30"))
							.setIgnored(LgStatus.NO));
		assertThat(testSurvey.getDeterminedTimePeriod())
			.isEqualTo(LgTimePeriod.EMPTY_TIMEPERIOD);
	}
	
	@Ignore
	@Test
	/**
	 * Note: We are skipping this test because we don't use messages in current
	 * implementation.
	 */
	public void messagesSentTest() {
		runDemoScenario();
		Set<LgMessage> bobsMessages = getMessagesFor("Bob");
		Set<LgMessage> carolsMessages = getMessagesFor("Carol");
		Set<LgMessage> alicesMessages = getMessagesFor("Alice");
		assertThat(bobsMessages).hasSize(1);
		assertThat(carolsMessages).hasSize(1);
		assertThat(alicesMessages).hasSize(1);
	}
	
	private void runDemoScenario() {
		saveSurvey();
		triggerEvaluation();
		// Set flag to YES and save
		surveyForEvaluation.setSuccess(LgStatus.YES);
		surveyForEvaluation.detach();
		surveyForEvaluation = testUtils.saveSurveyFor("Alice",
				surveyForEvaluation);
		notifyParticipants();
	}


	private void triggerEvaluation() {
		// Call evaluation, get Surveys
		surveyForEvaluation = new TestTransaction<LgSurvey>("Alice") {
			@Override
			public LgSurvey execute() {
				final LgUser user = startSession();
				// Triggering switch!
				user.evaluateAllSurveys();
				// Pulling single survey, not all surevys like in orignal
				// version.
				return user.getSurvey(surveyForEvaluation.getOid());
			}
		}.getResult();
	}
	
	private void notifyParticipants() {
		new TestTransaction<String>("Alice") {
			@Override
			public String execute() {
				final LgUser user = startSession();
				// Notifies
				user.notifyParticipants(surveyForEvaluation.getOid());
				// And retrieves Survey
				return "";
			}
		}.getResult();
	}

	
	@Test
	public void algoCheckedSetRightTest() {
		runDemoScenario();
		assertThat(surveyForEvaluation.getAlgoChecked()).isTrue();
	}

	@Test
	public void determinedTimePeriodAsExpectedTest() {
		runDemoScenario();
		assertThat(surveyForEvaluation.getDeterminedTimePeriod()).isEqualTo(
				testUtils.tP("01.05.1999/21:30 -> 01.05.1999/22:30"));
	}

	public Set<LgMessage> getMessagesFor(String username) {
		return new TestTransaction<Set<LgMessage>>(username) {
			@Override
			public Set<LgMessage> execute() {
				return startSession().getMessages();
			}
		}.getResult();
	}

	public LgInvite saveInviteFor(String username, final LgInvite invite) {
		return new TestTransaction<LgInvite>(username) {
			@Override
			public LgInvite execute() {
				return startSession().saveInvite(invite);
			}
		}.getResult();
	}

	public LgInvite getSkiInviteFor(String username) {
		return new TestTransaction<LgInvite>(username) {
			@Override
			public LgInvite execute() {
				// Just gets the first invite and done.
				return startSession().getInvites().get(0);
			}
		}.getResult();
	}

	/**
	 * Note: Delete has to be executed in separate transactions. Only when a
	 * transaction was executed, the contained invites and surveys are deleted.
	 */
	@After
	public void deleteTestData() {
		testUtils.deleteAccountsFor("Alice", "Bob", "Carol");
	}

}
