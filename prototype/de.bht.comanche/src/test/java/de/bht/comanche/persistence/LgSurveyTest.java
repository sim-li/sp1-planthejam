package de.bht.comanche.persistence;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

/**
 * Basic survey operations.
 * 
 * @author Simon Lischka
 *
 */
public class LgSurveyTest {
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;
	private TestUtils testUtils = new TestUtils();
	private LgSurvey surveyForEvaluation;

	@BeforeClass
	public static void init() {
		TestUtils.resetJPADatabase();
	}

	@Before
	public void buildUp() {
		alice = testUtils.registerTestUser("Alice");
		bob = testUtils.registerTestUser("Bob");
		carol = testUtils.registerTestUser("Carol");
		saveSurveyWithTimeperiods();
	}

	private void saveSurveyWithTimeperiods() {
		surveyForEvaluation = testUtils.saveSurvey(new LgSurvey()
				.setPossibleTimePeriods(testUtils.buildTimePeriods(
						"30.01.86/20:30 -> 30.01.86/22:30",
						"01.05.99/21:30 -> 01.06.99/22:30",
						"08.09.05/00:30 -> 01.06.06/22:30")));
	}

	@Test
	public void getSurveyByOidTest() {
		final LgSurvey aSurvey = new LgSurvey();
		final LgSurvey surveyWithOid = testUtils.saveSurvey(aSurvey);
		final LgSurvey surveyEval = new TestTransaction<LgSurvey>("Alice") {
			@Override
			public LgSurvey execute() {
				return startSession().getSurvey(surveyWithOid.getOid());
			}
		}.getResult();
		assertThat(surveyEval.getOid()).isEqualTo(surveyWithOid.getOid());
	}
	/**
	 * Note: The host is also returned with the getInvites method.
	 */
	@Test
	public void getInvitesForSurveyByOidTest() {
		final LgSurvey surveyWithOid = testUtils.saveSurvey(new LgSurvey()
				.addParticipants(bob, carol));
		final List<LgInvite> invites = new TestTransaction<List<LgInvite>>(
				"Alice") {
			@Override
			public List<LgInvite> execute() {
				return startSession().getInvitesForSurvey(
						surveyWithOid.getOid());
			}
		}.getResult();
		assertThat(extractProperty("user.name").from(invites)).containsOnly(
				"Alice", "Bob", "Carol");
	}

	@Test
	public void getSurveysTest() {
		testUtils.saveSurvey(new LgSurvey().setName("Survey1"));
		testUtils.saveSurvey(new LgSurvey().setName("Survey2"));
		testUtils.saveSurvey(new LgSurvey().setName("Survey3"));
		List<LgSurvey> surveysForEvaluation = new TestTransaction<List<LgSurvey>>(
				"Alice") {
			@Override
			public List<LgSurvey> execute() {
				return startSession().getSurveys();
			}
		}.getResult();
		assertThat(extractProperty("name").from(surveysForEvaluation))
				.contains("Survey1", "Survey2", "Survey3");
	}

	public List<LgSurvey> createSurveysWithNames(String... names) {
		List<LgSurvey> surveys = new LinkedList<LgSurvey>();
		for (String name : names) {
			surveys.add(new LgSurvey().setName(name).addHost(alice)
					.addParticipants(bob, carol));
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
		final LgSurvey surveyForEvaluation = testUtils
				.saveSurvey(new LgSurvey().addParticipants(bob, carol));
		assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsOnly(
				"Alice", "Bob", "Carol");
	}

	@Test
	public void saveSurveyWithInvitesHostAttributeTest() {
		final LgSurvey surveyForEvaluation = testUtils
				.saveSurvey(new LgSurvey().addParticipants(bob, carol));
		assertThat(
				extractProperty("isHost")
						.from(surveyForEvaluation.getInvites())).containsOnly(
				true, false, false);
	}

	@Test
	public void deleteParticipantTest() {
		final LgSurvey aSurvey = testUtils.saveSurvey(new LgSurvey()
				.addParticipants(bob, carol));
		aSurvey.removeParticipants(carol);
		final LgSurvey surveyForEvaluation = testUtils.saveSurvey(aSurvey);
		assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsOnly(
				"Alice", "Bob");
	}

	@Test
	public void addParticipantTest() {
		final LgSurvey aSurvey = testUtils.saveSurvey(new LgSurvey()
				.addParticipants(bob));
		aSurvey.addParticipants(carol);
		final LgSurvey surveyForEvaluation = testUtils.saveSurvey(aSurvey);
		assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsOnly(
				"Alice", "Bob", "Carol");
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
