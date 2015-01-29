package de.bht.comanche.logic;

import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.persistence.TestUtils;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

public class SurveyEvalTest {
	private LgSurvey testSurvey;
	private TestUtils testUtils;
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;
	
	@Before
	public void buildUp() {
		testUtils = new TestUtils();
		alice = new LgUser().setName("Alice");
		bob = new LgUser().setName("Bob");
		carol = new LgUser().setName("Carol");
		testSurvey = new LgSurvey()
			.setName("Go Skiing")
			.setPossibleTimePeriods(
				testUtils.buildTimePeriods(
							"01.05.1999/21:30 -> 01.05.1999/22:30",
							"30.01.1986/20:30 -> 30.01.1986/22:30",
							"08.09.2005/00:30 -> 08.09.2005/01:30"
					))
			.setDeadline(testUtils.buildDate("30.01.2012/22:30"))
			.addParticipants(carol, bob);
		testSurvey = alice.addSurveyForHost(testSurvey);
		System.out.println("HELLO");
	}
	
	@Test
	public void matchOneTimePeriodTest() {
		testSurvey.getInviteByParticipantName("Bob").setConcreteAvailability(
				testUtils.buildTimePeriods(
						"01.05.1999/21:30 -> 01.05.1999/22:30",
						"30.01.1986/22:30 -> 30.01.1986/23:30",
						"08.09.2005/01:30 -> 08.09.2005/02:30")
				);
		testSurvey.getInviteByParticipantName("Carol").setConcreteAvailability(
				testUtils.buildTimePeriods(
						"01.05.1999/21:30 -> 01.05.1999/22:30",
						"30.01.1986/00:30 -> 30.01.1986/01:30",
						"08.09.2005/02:30 -> 08.09.2005/03:30")
				);
		testSurvey.evaluate();
		assertThat(testSurvey.getDeterminedTimePeriod())
			.isNotEqualTo(LgTimePeriod.EMPTY_TIMEPERIOD)
			.isEqualTo(testUtils.tP(
					"01.05.1999/21:30 -> 01.05.1999/22:30"));
	}
}
