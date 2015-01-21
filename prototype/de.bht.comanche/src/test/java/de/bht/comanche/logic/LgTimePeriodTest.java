package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.bht.comanche.persistence.DaEmProvider;

/**
 * @author Maxim Novichkov;
 * @author Simon Lischka
 */
@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgTimePeriodTest {
	private LgUser alice;
	private LgUser bob;
	private LgUser pit;
	private LgSession session;
	private LgSurvey survey;
	private LgInvite invite;
	private LgTimePeriod timePeriod;
	
	@BeforeClass 
	public static void resetDatabase() {
//		Map<String, String> properties = new HashMap<String, String>(1);
//		properties.put("hibernate.hbm2ddl.auto", "create");
	}
	
	@Before
	public void buildUp() {
		beginTransaction();
		intializeUsers();
		registerUsers(session);
		
		alice = session.startFor("Alice");
		
		invite = new LgInvite()
			.setUser(alice)
			.setHost(true);
		survey = new LgSurvey()
			.addInvite(invite);
		alice
			.saveInvite(invite);
		
		timePeriod = new LgTimePeriod()
			.setDurationMins(10)
			.setStartTime(new Date(8099));
	}

	private void registerUsers(final LgSession sessionForRegistration) {
		sessionForRegistration.register(alice);
		sessionForRegistration.register(bob);
		sessionForRegistration.register(pit);
	}

	private void intializeUsers() {
		alice = new LgUser().setName("Alice").setEmail("test@test.de")
				.setPassword("testtest");
		bob = new LgUser().setName("Bob").setEmail("bob@test.de")
				.setPassword("testtest");
		pit = new LgUser().setName("Pit").setEmail("pit@test.de")
				.setPassword("testtest");
	}
	
	/**
	 * This test actually includes the checking of data, thus the part before the commit 
	 * is what would be called in a Rest Service. 
	 */
	@Test
	public void addPossibleTimePeriodToSurveyTest() {
		final Set<LgTimePeriod> possibleTimePeriods = new HashSet<LgTimePeriod>();
		possibleTimePeriods.add(timePeriod);
		survey.setPossibleTimePeriods(possibleTimePeriods);
		commit();
		assertEquals("TimePeriods were persisted correctly", survey.getPossibleTimePeriods(), possibleTimePeriods);
	}


	public void addDeterminedTimePeriodToSurveyTest() {}
	
	public void addTimePeriodToInviteTest() {}
	
	public void addTimePeriodToUserTest() {}
	
	@After
	public void tearDown() {
		commit();
		alice.deleteOtherUserAccount(bob);
		alice.deleteOtherUserAccount(pit);
		alice.deleteThisAccount();
		endTransaction();
	}
				
	private void beginTransaction() {
		session = new LgSession();
		session.getApplication().beginTransaction();
	}
	
	private void endTransaction() {
		session.getApplication().endTransaction(true);
	}
	
	private void commit() {
		endTransaction();
		beginTransaction();
	}
}
