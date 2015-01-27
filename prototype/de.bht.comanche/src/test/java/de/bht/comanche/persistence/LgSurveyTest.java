package de.bht.comanche.persistence;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bht.comanche.logic.LgStatus;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgSurveyType;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgTimeUnit;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.persistence.DaHibernateJpaPool.DaFindOneByKeyExc;

public class LgSurveyTest extends LgTestWithUsers {
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
		.setPossibleTimePeriods(testUtils.buildTimePeriods(20, 40, 60)));
	}
	
	@Test
	public void getSurveyByOidTest() {
		final Date aDate = new Date();
		final LgTimePeriod aTimePeriod = new LgTimePeriod().setDurationMins(30).setStartTime(aDate);
		final Set<LgTimePeriod> severalTimePeriods = testUtils.buildTimePeriods(20,30,40); 
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
		final LgSurvey surveyWithOid = testUtils.saveSurvey(aSurvey);
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
		final LgSurvey surveyWithOid = testUtils.saveSurvey(new LgSurvey().addParticipants(bob, carol));
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
		testUtils.saveSurvey(new LgSurvey().setName("Survey1"));
		testUtils.saveSurvey(new LgSurvey().setName("Survey2"));
		testUtils.saveSurvey(new LgSurvey().setName("Survey3"));
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
		final LgSurvey surveyForEvaluation = testUtils.saveSurvey(new LgSurvey().addParticipants(bob, carol));
		assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).
						containsOnly(
				"Alice", "Bob", "Carol");
	}

	@Test
	public void saveSurveyWithInvitesHostAttributeTest() {
		final LgSurvey surveyForEvaluation = testUtils.saveSurvey(new LgSurvey().addParticipants(bob, carol));
		assertThat(
				extractProperty("isHost")
						.from(surveyForEvaluation.getInvites()))
				.containsOnly(true, false, false);
	}
	
	@Test
	public void deleteParticipantTest() {
		final LgSurvey aSurvey = testUtils.saveSurvey(new LgSurvey().addParticipants(bob, carol));
		aSurvey.removeParticipants(carol);
		final LgSurvey surveyForEvaluation = testUtils.saveSurvey(aSurvey);
	    assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsOnly(
				"Alice", "Bob");
	}
	
	@Test
	public void addParticipantTest() {
		final LgSurvey aSurvey = testUtils.saveSurvey(new LgSurvey().addParticipants(bob));
		aSurvey.addParticipants(carol);
		final LgSurvey surveyForEvaluation = testUtils.saveSurvey(aSurvey);
	    assertThat(
				extractProperty("user.name").from(
						surveyForEvaluation.getInvites())).containsOnly(
				"Alice", "Bob", "Carol");
	}

	
	@Test
	public void deleteSurveyTest() {
		final LgSurvey surveyForEvaluation = testUtils.saveSurvey(new LgSurvey()
			.addParticipants(bob, carol)
			.setPossibleTimePeriods(testUtils.buildTimePeriods(20, 30, 40))
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
		final LgSurvey surveyWithVariousTimePeriods = testUtils.saveSurvey(new LgSurvey()
		.setPossibleTimePeriods(
				testUtils.buildTimePeriods(20, 40, 60)));
		assertThat(
				extractProperty("durationMins").from(
						surveyWithVariousTimePeriods.getPossibleTimePeriods()))
				.contains(20, 40, 60);
	}

	@Test
	public void saveSurveyWithDeterminedTimePeriodTest() {
		final LgSurvey surveyWithOneTimePeriod = testUtils.saveSurvey(new LgSurvey()
		.setDeterminedTimePeriod(
				testUtils.buildOneTimePeriod(20)));
		assertThat(
				surveyWithOneTimePeriod.getDeterminedTimePeriod().getDurationMins())
				.isEqualTo(20);
	}

	@Test
	public void updateSurveyByModifyingTimePeriods() {
		updateSurveyTimePeriods(20, 40, 80);
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.containsOnly(20, 40,80);
	}
	
	@Test
	public void updateSurveyByAddingEquallyLastingTimePeriods() {
		Date firstDate;
		Date secondDate;
		Date thirdDate;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			firstDate = sdf.parse("21/12/2012");
			secondDate = sdf.parse("21/12/2014");
			thirdDate = sdf.parse("21/12/2016");
			final HashSet<LgTimePeriod> pTimePeriods = new HashSet<LgTimePeriod>();
			pTimePeriods.add(
					new LgTimePeriod().setDurationMins(20).setStartTime(firstDate)
					);
			pTimePeriods.add(
					new LgTimePeriod().setDurationMins(20).setStartTime(secondDate)
					);
			pTimePeriods.add(
					new LgTimePeriod().setDurationMins(20).setStartTime(thirdDate)
					);
			surveyForEvaluation.setPossibleTimePeriods(pTimePeriods);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		surveyForEvaluation = new TestTransaction<LgSurvey>(
				"Alice") {
			@Override
			public LgSurvey execute() {
				return startSession().updateSurvey(surveyForEvaluation);
			}
		}.getResult();
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.containsOnly(20, 20, 20)
				.hasSize(3);
	}

	@Test
	public void updateSurveyByDeletingOneTimePeriods() {
		updateSurveyTimePeriods(20, 40);
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.containsOnly(20, 40);
	}

	@Test
	public void updateSurveyByDeletingTwoTimePeriods() {
		updateSurveyTimePeriods(20);
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.containsOnly(20);
	}

	@Test
	public void updateSurveyByDeletingAllTimePeriods() {
		updateSurveyTimePeriods();
		assertThat(
				extractProperty("durationMins").from(
						surveyForEvaluation.getPossibleTimePeriods()))
				.isEmpty();
	}

	/**
	 * Updates the time periods [20, 40, 60] of the test survey with the 
	 * given durations.
	 */
	private void updateSurveyTimePeriods(int... durationUpdates) {
		surveyForEvaluation.setPossibleTimePeriods(testUtils.buildTimePeriods(durationUpdates));
		surveyForEvaluation = new TestTransaction<LgSurvey>(
				"Alice") {
			@Override
			public LgSurvey execute() {
				return startSession().updateSurvey(surveyForEvaluation);
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
