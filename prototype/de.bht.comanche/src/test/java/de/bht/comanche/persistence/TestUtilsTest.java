package de.bht.comanche.persistence;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.persistence.TestUtils;

/**
 * Checks the test utils time period generator
 * 
 * @authorSimon Lischka
 *
 */
public class TestUtilsTest {
	@Test
	public void testUtilTpCorrect() {
		final TestUtils testUtils = new TestUtils();
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(1982, Calendar.JANUARY, 20, 13, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		final Date targetStartDate = cal.getTime();
		cal.set(1982, Calendar.JANUARY, 21, 14, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		final Date targetEndDate = cal.getTime();
		final LgTimePeriod gregorianTimePeriod = new LgTimePeriod()
			.setStartTime(targetStartDate)
			.setEndTime(targetEndDate);
		final LgTimePeriod testTimePeriod = testUtils.tP("20.01.1982/13:30 -> 21.01.1982/14:30");
		final LgTimePeriod testTimePeriodWrong = testUtils.tP("20.01.1982/13:31 -> 21.01.1982/14:30");
		final LgTimePeriod testTimePeriodWrongAgain = testUtils.tP("20.01.1982/13:30 -> 21.02.1982/14:30");
		assertThat(gregorianTimePeriod).isEqualTo(testTimePeriod);
		assertThat(gregorianTimePeriod).isNotEqualTo(testTimePeriodWrong);
		assertThat(gregorianTimePeriod).isNotEqualTo(testTimePeriodWrongAgain);
	}
}
