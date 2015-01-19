package de.bht.comanche.logic;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgUserWithCollectionsTest {
	private LgUser alice;
	private LgSession session;
	private LgTimePeriod timePeriod;
	
	@BeforeClass 
	public static void resetDatabase() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
	}
	
	@Before
	public void buildUp() {
		beginTransaction();
		intializeUsers();
		registerUsers(session);
		alice = session.startFor("Alice");
		timePeriod = new LgTimePeriod()
			.setDurationMins(10)
			.setStartTime(new Date(8099));
	}

	private void registerUsers(final LgSession sessionForRegistration) {
		sessionForRegistration.register(alice);
	}

	private void intializeUsers() {
		alice = new LgUser().setName("Alice").setEmail("test@test.de")
				.setPassword("testtest");
	}
	
	/**
	 * This test actually includes the checking of data, thus the part before the commit 
	 * is what would be called in a Rest Service. 
	 */
	@Test
	public void saveMessagesTest() {
		List<String> messages = new ArrayList<String>();
		messages.add("Hello");
		messages.add("Kitty");
		alice.setMessages(messages);
		alice.save();
		commit();
		final LgUser alicePersisted = session.startFor("Alice");
		assertTrue(alicePersisted.getMessages().contains("Hello"));
		assertTrue(alicePersisted.getMessages().contains("Kitty"));
	}
	
	@Ignore
	@Test
	public void saveTimePeriodTest() {
	}
	
	@After
	public void tearDown() {
		alice.deleteThisAccount();
//		endTransaction();
	}
				
	private void beginTransaction() {
		session = new LgSession();
		session.getApplication().beginTransaction();
	}
	
	private void endTransaction() {
		session.getApplication().endTransaction(true);
	}
	
	private void commit() {
		beginTransaction();
		endTransaction();
	}
}
