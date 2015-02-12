package de.bht.comanche.persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Persistence;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgUser;
/**
 * 
 * Common methods for testing.
 * 
 * @author Simon Lischka
 *
 */
public class TestUtils {
	
	public static void resetJPADatabase() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(
				DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
	}

	public LgUser registerTestUser(final String name) {
		final LgUser testAccount = new LgUser().setName(name)
				.setEmail(name + "@integrationTesting.com").setPassword(name + "isTesting");
		return new TestTransaction<LgUser>(name) {
			@Override
			public LgUser execute() {
				return getSession().register(testAccount);
			}
		}.getResult();
	}
	
	/**
	 * Generates a hash set of time periods by parsing the time strings.
	 * 
	 * @param timeString
	 *            List of time strings that define start and end time of
	 *            time period.
	 * @return A set of time periods
	 */
	public HashSet<LgTimePeriod> buildTimePeriods(String ... timeStrings) {
		final HashSet<LgTimePeriod> timePeriods = new HashSet<LgTimePeriod>();
		try {
			for (String tS: timeStrings) {
				timePeriods.add(buildTimePeriodWithExc(tS));
			}
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return timePeriods;
	}
	
	/**
	 * Parses a single time string. 
	 * 
	 * Format (start/end) is dd.MM.yyyy/mm:HH->dd.MM.yyyy/mm:HH
	 * 
	 * @param timeString String representation of start and end time
	 * @return Time Period with start and end time
	 * @throws ParseException
	 */
	public LgTimePeriod buildTimePeriodWithExc(String timeString) throws ParseException {
		final String[] times = timeString.split("->");
		for (int i = 0; i <times.length; i++) {
			times[i] = times[i].trim();
		}
		final Date startTime = parseDate(times[0]);
		final Date endTime = parseDate(times[1]);
		return new LgTimePeriod()
			.setStartTime(startTime)
			.setEndTime(endTime);
	}
	
	public Date buildDate(String dateString) {
		return parseDate(dateString);
	}
	
	public Date parseDate(String date) {
		// Format: dd.MM.yyyy/mm:HH
		// Note: Using "dumb" regEx for the moment.
		final String re = "([0-9]{2}).([0-9]{2}).([0-9]{4})/([0-9]{2}):([0-9]{2})";
		final Pattern p = Pattern.compile(re);
		final Matcher m = p.matcher(date);
		Calendar cal = GregorianCalendar.getInstance();
		if (m.matches()) {
			cal.set(Integer.parseInt(m.group(3)),     	//YEAR
					Integer.parseInt(m.group(2)) - 1,	//MONTH
					Integer.parseInt(m.group(1)),		//DAY
					Integer.parseInt(m.group(4)),		//HOUR
					Integer.parseInt(m.group(5))		//MINUTE
					);
		}
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * Parses a single time string. 
	 * 
	 * Format (start/end) is dd.MM.yyyy/mm:HH->dd.MM.yyyy/mm:HH
	 * 
	 * @param timeString String representation of start and end time
	 * @return Time Period with start and end time
	 * 
	 */
	public LgTimePeriod tP(String timeString) {
		try {
			return buildTimePeriodWithExc(timeString);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Runs a transaction with a delete command for every given user.
	 * 
	 * @param users
	 *            Users to be deleted
	 */
	public void deleteAccountsFor(String... users) {
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
	

	/**
	 * Saves a survey for Alice, which is our standard account for these
	 * operations
	 *  
	 * @param freshSurvey
	 *            Survey to be persisted
	 * @return The persisted survey with OID.
	 */
	public LgSurvey saveSurvey(final LgSurvey freshSurvey) {
		return saveSurveyFor("Alice", freshSurvey);
	}
	
	public LgSurvey saveSurveyFor(String username, final LgSurvey freshSurvey) {
		final LgSurvey persistedSurvey = new TestTransaction<LgSurvey>(username) {
			@Override
			public LgSurvey execute() {
				return startSession().saveSurvey(freshSurvey);
			}
		}.getResult();
		return persistedSurvey;
	}

}
