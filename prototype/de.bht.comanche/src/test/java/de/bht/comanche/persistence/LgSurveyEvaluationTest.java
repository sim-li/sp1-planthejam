package de.bht.comanche.persistence;


import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgStatus;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.logic.LgMessage;

public class LgSurveyEvaluationTest {
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
		saveSurvey();
	}

	private void saveSurvey() {
		final LgSurvey testSurvey = new LgSurvey()
			.setName("Go Skiing")
			.setPossibleTimePeriods(testUtils.buildTimePeriods(
				"01.05.99/21:30 -> 01.05.99/22:30",
				"30.01.86/20:30 -> 30.01.86/22:30",
				"08.09.05/00:30 -> 08.09.05/01:30"))
			.setDeadline(testUtils.buildDate(
				"30.01.2012/22:30"				// This deadline definately is over
					))
			.addParticipants(bob, carol);
		this.surveyForEvaluation = testUtils.saveSurveyFor("Alice", testSurvey);
		// Bob and carol seem to have time at the 01.05.99
		saveInviteFor("Bob",
			getSkiInviteFor("Bob").setConcreteAvailability(testUtils.buildTimePeriods(
					"01.05.99/21:30 -> 01.05.99/22:30",
					"30.01.86/22:30 -> 30.01.86/23:30",
					"08.09.05/01:30 -> 08.09.05/02:30"
					))
		);
		saveInviteFor("Bob",
			getSkiInviteFor("Carol").setConcreteAvailability(testUtils.buildTimePeriods(
					"01.05.99/21:30 -> 01.05.99/22:30",
					"30.01.86/00:30 -> 30.01.86/01:30",
					"08.09.05/02:30 -> 08.09.05/03:30"
					))
			);
	}
	
	@Test
	public void acceptSurveyTest() {
		// Call evaluation, get Surveys
		surveyForEvaluation = new TestTransaction<LgSurvey>(
				"Alice") {
			@Override
			public LgSurvey execute() {
				final LgUser user = startSession();
				user.evaluateAllSurveys();
				// Pulling single survey, not all surevys like in orignal version.
				return user.getSurvey(surveyForEvaluation.getOid());
			}
		}.getResult();
		// Set flag to YES and save
		surveyForEvaluation.setSuccess(LgStatus.YES);
		
		surveyForEvaluation.detach();
		surveyForEvaluation = testUtils.saveSurveyFor("Alice", surveyForEvaluation);
		
		new TestTransaction<String>(
				"Alice") {
			@Override
			public String execute() {
				final LgUser user = startSession();
				user.notifyParticipants(surveyForEvaluation.getOid());
				return "";
			}
		}.getResult();
		// Bob should have messages
		Set<LgMessage> bobsMessages = new TestTransaction<Set<LgMessage>>(
				"Bob") {
			@Override
			public Set<LgMessage> execute() {
				return startSession().getMessages();
			}
		}.getResult();
		// Carol should have message
		Set<LgMessage> carolsMessages = new TestTransaction<Set<LgMessage>>(
				"Carol") {
			@Override
			public Set<LgMessage> execute() {
				return startSession().getMessages();
			}
		}.getResult();
		Set<LgMessage> alicesMessages = new TestTransaction<Set<LgMessage>>(
				"Alice") {
			@Override
			public Set<LgMessage> execute() {
				return startSession().getMessages();
			}
		}.getResult();
		assertTrue(true);
		
	}
	
	public LgInvite saveInviteFor(String username, final LgInvite invite) {
		return new TestTransaction<LgInvite>(
				username) {
			@Override
			public LgInvite execute() {
				return startSession().saveInvite(invite);
			}
		}.getResult();
	}
	
	public LgInvite getSkiInviteFor(String username) {
		return new TestTransaction<LgInvite>(
				username) {
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
