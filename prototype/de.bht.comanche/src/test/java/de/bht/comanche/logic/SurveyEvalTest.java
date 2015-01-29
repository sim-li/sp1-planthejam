package de.bht.comanche.logic;

import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.persistence.TestUtils;

public class SurveyEvalTest {
	private LgSurvey testSurvey;
	private TestUtils testUtils;
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;
	
	@Before
	public void buildUp() {
		testUtils = new TestUtils();
		alice = new LgUser();
		bob = new LgUser();
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
		alice.saveSurvey(testSurvey);
	}
	
	
// Bob and carol seem to have time at the 01.05.99
saveInviteFor(
	"Bob",
	getSkiInviteFor("Bob").setConcreteAvailability(
			testUtils.buildTimePeriods(
					"01.05.1999/21:30 -> 01.05.1999/22:30",
					"30.01.1986/22:30 -> 30.01.1986/23:30",
					"08.09.2005/01:30 -> 08.09.2005/02:30")));
saveInviteFor(
	"Carol",
	getSkiInviteFor("Carol").setConcreteAvailability(
			testUtils.buildTimePeriods(
					"01.05.1999/21:30 -> 01.05.1999/22:30",
					"30.01.1986/00:30 -> 30.01.1986/01:30",
					"08.09.2005/02:30 -> 08.09.2005/03:30")));
	@Test
	public void fdas() {
		
	}
}
