package de.bht.comanche.persistence;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTimePeriod;

/**
 * 
 * Time Period tests.
 * 
 * @author Simon Lischka
 *
 */
public class LgSurveyTimePeriodsTest {
	private TestUtils testUtils = new TestUtils();
	private LgSurvey surveyForEvaluation;

	@BeforeClass
	public static void init() {
		TestUtils.resetJPADatabase();
	}

	@Before
	public void buildUp() {
		testUtils.registerTestUser("Alice");
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
	public void testUtilTpCorrect() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(1982, Calendar.JANUARY, 20, 13, 30);
		final Date targetStartDate = cal.getTime();
		cal.set(1982, Calendar.JANUARY, 21, 14, 30);
		final Date targetEndDate = cal.getTime();
		final LgTimePeriod gregorianTimePeriod = new LgTimePeriod()
				.setStartTime(targetStartDate).setEndTime(targetEndDate);
		final LgTimePeriod testTimePeriod = testUtils
				.tP("20.01.1982/13:30 -> 21.01.1982/14:30");
		final LgTimePeriod testTimePeriodWrong = testUtils
				.tP("20.01.1982/13:31 -> 21.01.1982/14:30");
		final LgTimePeriod testTimePeriodWrongAgain = testUtils
				.tP("20.01.1982/13:30 -> 21.02.1982/14:30");
		assertThat(gregorianTimePeriod).isEqualTo(testTimePeriod);
		assertThat(gregorianTimePeriod).isNotEqualTo(testTimePeriodWrong);
		assertThat(gregorianTimePeriod).isNotEqualTo(testTimePeriodWrongAgain);
	}

	@Test
	public void saveSurveyWithTimePeriodsTest() {
		final LgSurvey surveyWithVariousTimePeriods = testUtils
				.saveSurvey(new LgSurvey().setPossibleTimePeriods(testUtils
						.buildTimePeriods(
								"02.12.1982/13:40 -> 02.12.1982/15:40",
								"03.12.1984/13:40 -> 04.12.1986/15:40",
								"05.12.1986/13:45 -> 06.12.1986/15:40")));
		assertThat(surveyWithVariousTimePeriods.getPossibleTimePeriods())
				.contains(testUtils.tP("02.12.1982/13:40 -> 02.12.1982/15:40"),
						testUtils.tP("03.12.1984/13:40 -> 04.12.1986/15:40"),
						testUtils.tP("05.12.1986/13:45 -> 06.12.1986/15:40"))
				.hasSize(3);
	}

	@Test
	public void saveSurveyWithDeterminedTimePeriodTest() {
		final LgSurvey surveyWithOneTimePeriod = testUtils
				.saveSurvey(new LgSurvey().setDeterminedTimePeriod(testUtils
						.tP("02.12.2012/13:40 -> 02.12.2012/15:40")));
		assertThat(surveyWithOneTimePeriod.getDeterminedTimePeriod())
				.isEqualTo(testUtils.tP("02.12.2012/13:40 -> 02.12.2012/15:40"));
	}

	@Test
	public void updateSurveyByModifyingTimePeriods() {
		updateSurveyTimePeriods("31.01.1986/21:30 -> 30.01.1986/22:30",
				"01.05.1999/21:30 -> 01.07.2005/21:30",
				"08.09.2005/00:30 -> 01.06.2006/20:30");
		assertThat(surveyForEvaluation.getPossibleTimePeriods()).containsOnly(
				testUtils.tP("31.01.1986/21:30 -> 30.01.1986/22:30"),
				testUtils.tP("01.05.1999/21:30 -> 01.07.2005/21:30"),
				testUtils.tP("08.09.2005/00:30 -> 01.06.2006/20:30"))
				.hasSize(3);
	}

	@Test
	/**
	 * Note: Equal time periods will be ignored and saved as one time period.
	 */
	public void updateSurveyByAddingEqualTimePeriods() {
		updateSurveyTimePeriods("31.01.1986/21:30 -> 30.01.1986/22:30",
				"31.01.1986/21:30 -> 30.01.1986/22:30",
				"31.01.1986/21:30 -> 30.01.1986/22:30");
		assertThat(surveyForEvaluation.getPossibleTimePeriods()).containsOnly(
				testUtils.tP("31.01.1986/21:30 -> 30.01.1986/22:30"))
				.hasSize(1);
	}

	@Test
	public void updateSurveyByDeletingOneTimePeriods() {
		updateSurveyTimePeriods("30.01.1986/20:30 -> 30.01.1986/22:30",
				"08.09.2005/00:30 -> 01.06.2006/22:30");
		assertThat(surveyForEvaluation.getPossibleTimePeriods()).containsOnly(
				testUtils.tP("30.01.1986/20:30 -> 30.01.1986/22:30"),
				testUtils.tP("08.09.2005/00:30 -> 01.06.2006/22:30"))
				.hasSize(2);
	}

	@Test
	public void updateSurveyByDeletingTwoTimePeriods() {
		updateSurveyTimePeriods("30.01.1986/20:30 -> 30.01.1986/22:30");
		assertThat(surveyForEvaluation.getPossibleTimePeriods()).containsOnly(
				testUtils.tP("30.01.1986/20:30 -> 30.01.1986/22:30"))
				.hasSize(1);
	}

	@Test
	public void updateSurveyByDeletingAllTimePeriods() {
		updateSurveyTimePeriods();
		assertThat(surveyForEvaluation.getPossibleTimePeriods()).isEmpty();
	}

	/**
	 * Updates the time periods of the test survey with the given timeString
	 * dd.MM.yyyy/mm:HH->dd.MM.yyyy/mm:HH
	 * 
	 */
	private void updateSurveyTimePeriods(String... dateStrings) {
		surveyForEvaluation.setPossibleTimePeriods(testUtils
				.buildTimePeriods(dateStrings));
		surveyForEvaluation = new TestTransaction<LgSurvey>("Alice") {
			@Override
			public LgSurvey execute() {
				return startSession().updateSurvey(surveyForEvaluation);
			}
		}.getResult();
	}
	
	@After
	public void deleteTestData() {
		testUtils.deleteAccountsFor("Alice");
	}

}
