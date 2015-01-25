package de.bht.comanche.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.persistence.Persistence;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgUser;

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
	 * Generates a hash set of time periods, inserts the current date stamp to
	 * fill the field with a value.
	 * 
	 * @param durations
	 *            List of durations as integer. Every duration will generate a
	 *            time period.
	 * @return A set of time periods
	 */
	public HashSet<LgTimePeriod> buildTimePeriods(int... durations) {
		final HashSet<LgTimePeriod> timePeriods = new HashSet<LgTimePeriod>();
		for (int i = 0; i < durations.length; i++) {
			timePeriods.add(new LgTimePeriod().setStartTime(new Date())
					.setDurationMins(durations[i]));
		}
		return timePeriods;
	}
	
	public LgTimePeriod buildOneTimePeriod(int duration) {
		return new LgTimePeriod().setDurationMins(duration).setStartTime(new Date());
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
