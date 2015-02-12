package de.bht.comanche.logic;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.persistence.TestUtils;
import static org.fest.assertions.api.Assertions.assertThat;

public class SurveyEvalTest {
	private LgSurvey testSurvey;
	private TestUtils testUtils;
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;
	private LgUser denise;
	private final String A_COMMON_TIMEPERIOD = "01.05.1999/21:30 -> 01.05.1999/22:30";
	
	@Before
	public void buildUp() {
		testUtils = new TestUtils();
		alice = new LgUser().setName("Alice");
		bob = new LgUser().setName("Bob");
		carol = new LgUser().setName("Carol");
		denise = new LgUser().setName("Denise");
		testSurvey = new LgSurvey()
			.setName("Go Skiing")
			.setPossibleTimePeriods(
				testUtils.buildTimePeriods(
							A_COMMON_TIMEPERIOD,
							"30.01.1986/20:30 -> 30.01.1986/22:30",
							"08.09.2005/00:30 -> 08.09.2005/01:30"
					))
			.setDeadline(testUtils.buildDate("30.01.2012/22:30"));
		testSurvey = alice.prepareUnpersistedSurvey(testSurvey);
	}
	
	@Test
	public void emptyEqualsTest() {
		assertThat(new LgTimePeriod())
			.isEqualTo(new LgTimePeriod());
	}
	
	@Test
	public void equalEqualTest() {
		final LgTimePeriod tp1 = testUtils.tP(
				A_COMMON_TIMEPERIOD);
		final LgTimePeriod tp2 = testUtils.tP(
				A_COMMON_TIMEPERIOD); 
		assertThat(tp1
					.equals(
					tp2)
				  ).isTrue();
	}
	
	@Test
	public void retainAllCollectionTest() {
		final Set<LgTimePeriod> setOne = new HashSet<LgTimePeriod>();
		final Set<LgTimePeriod> setTwo = new HashSet<LgTimePeriod>();
		setOne.add(testUtils.tP(
				A_COMMON_TIMEPERIOD));
		
		setTwo.add(testUtils.tP(
				A_COMMON_TIMEPERIOD));
		setOne.retainAll(setTwo);
		assertThat(setOne).hasSize(1);
		assertThat(setOne).contains(
				testUtils.tP(
						A_COMMON_TIMEPERIOD));
	}
	
	@Test
	public void matchOneTimePeriodTest() {
		testSurvey.addParticipants(bob, carol);
		testSurvey.getInviteByParticipantName("Bob").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Carol").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Bob").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/22:30 -> 30.01.1986/23:30",
						"08.09.2005/01:30 -> 08.09.2005/02:30")
				);
		testSurvey.getInviteByParticipantName("Carol").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/00:30 -> 30.01.1986/01:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		testSurvey.evaluate();
		assertThat(testSurvey.getDeterminedTimePeriod())
			.isNotEqualTo(LgTimePeriod.EMPTY_TIMEPERIOD)
			.isEqualTo(testUtils.tP(
					"01.05.1999/21:30 -> 01.05.1999/22:30"));
	}
	
	@Test
	public void matchNoTimePeriod3UsersTest() {
		testSurvey.addParticipants(bob, carol, denise);
		testSurvey.getInviteByParticipantName("Bob").setConcreteAvailability(
				testUtils.buildTimePeriods(
						"01.05.1999/0:30 -> 01.05.1999/22:30",
						"30.01.1986/22:30 -> 30.01.1986/23:30",
						"08.09.2005/01:30 -> 08.09.2005/02:30")
				);
		testSurvey.getInviteByParticipantName("Carol").setConcreteAvailability(
				testUtils.buildTimePeriods(
						"01.05.1999/0:30 -> 01.05.1999/22:30",
						"30.01.1986/00:30 -> 30.01.1986/01:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		testSurvey.getInviteByParticipantName("Denise").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/00:30 -> 30.01.1986/01:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		testSurvey.evaluate();
		assertThat(testSurvey.getDeterminedTimePeriod())
			.isEqualTo(LgTimePeriod.EMPTY_TIMEPERIOD);
	}
	
	@Test
	public void matchOneTimePeriod3UsersTest() {
		testSurvey.addParticipants(bob, carol, denise);
		testSurvey.getInviteByParticipantName("Bob").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Carol").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Denise").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Bob").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/20:30 -> 30.01.1986/22:30",
						"08.09.2005/01:30 -> 08.09.2005/02:30")
				);
		testSurvey.getInviteByParticipantName("Carol").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/20:30 -> 30.01.1986/22:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		testSurvey.getInviteByParticipantName("Denise").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/00:30 -> 30.01.1986/01:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		testSurvey.evaluate();
		assertThat(testSurvey.getDeterminedTimePeriod())
			.isEqualTo(testUtils.tP(
					A_COMMON_TIMEPERIOD));
	}
	
	@Test
	public void matchOneTimePeriod3UsersOneRejectsTest() {
		testSurvey.addParticipants(bob, carol, denise);
		testSurvey.getInviteByParticipantName("Bob").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Denise").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Bob").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/20:30 -> 30.01.1986/22:30",
						"08.09.2005/01:30 -> 08.09.2005/02:30")
				);
		//Carol's not part of the game
		testSurvey.getInviteByParticipantName("Carol").setConcreteAvailability(
				testUtils.buildTimePeriods(
						"01.05.1998/21:30 -> 01.05.1999/0:30",
						"30.01.1986/20:30 -> 30.01.1986/22:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		//But she is ignoring
		testSurvey.getInviteByParticipantName("Carol").setIgnored(LgStatus.YES);
		testSurvey.getInviteByParticipantName("Denise").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/00:30 -> 30.01.1986/01:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		testSurvey.evaluate();
		assertThat(testSurvey.getDeterminedTimePeriod())
			.isEqualTo(testUtils.tP(
					"01.05.1999/21:30 -> 01.05.1999/22:30"));
	}
	
	@Test
	public void matchVariousTimePeriod3UsersTest() {
		testSurvey.addParticipants(bob, carol, denise);
		testSurvey.getInviteByParticipantName("Bob").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Carol").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Denise").setIgnored(LgStatus.NO);
		testSurvey.getInviteByParticipantName("Bob").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/20:30 -> 30.01.1986/22:30",
						"08.09.2005/01:30 -> 08.09.2005/02:30")
				);
		testSurvey.getInviteByParticipantName("Carol").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/20:30 -> 30.01.1986/22:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		testSurvey.getInviteByParticipantName("Denise").setConcreteAvailability(
				testUtils.buildTimePeriods(
						A_COMMON_TIMEPERIOD,
						"30.01.1986/20:30 -> 30.01.1986/22:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		testSurvey.evaluate();
		assertThat(testSurvey.getDeterminedTimePeriod())
			.isEqualTo(testUtils.tP(
					"01.05.1999/21:30 -> 01.05.1999/22:30"));
	}
	
	@Test
	public void dontMatchIfAllUsersUndecided() {
		testSurvey.addParticipants(bob, carol, denise);
		defaultAvailabilityFor("Bob", "Carol", "Denise");
		testSurvey.evaluate();
		assertThat(testSurvey.getDeterminedTimePeriod())
			.isEqualTo(LgTimePeriod.EMPTY_TIMEPERIOD);
	}
	
	@Test
	public void dontMatchIfAllUsersIgnore() {
		testSurvey.addParticipants(bob, carol, denise);
		testSurvey.getInviteByParticipantName("Bob").setIgnored(LgStatus.YES);
		testSurvey.getInviteByParticipantName("Carol").setIgnored(LgStatus.YES);
		testSurvey.getInviteByParticipantName("Denise").setIgnored(LgStatus.YES);
		defaultAvailabilityFor("Bob", "Carol", "Denise");
		testSurvey.evaluate();
		assertThat(testSurvey.getDeterminedTimePeriod())
			.isEqualTo(LgTimePeriod.EMPTY_TIMEPERIOD);
	}
	
	public void defaultAvailabilityFor(String ... users) {
		for (String user : users) {
			testSurvey.getInviteByParticipantName(user).setConcreteAvailability(
					testUtils.buildTimePeriods(
							A_COMMON_TIMEPERIOD,
							"30.01.1986/20:30 -> 30.01.1986/22:30",
							"08.09.2005/01:30 -> 08.09.2005/02:30")
					);
		}
	}
	
}
