package de.bht.comanche.persistence;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgUser;

public class SurveyTest {
	final static String ALICE_USER_NAME = "Alice";
	
	@BeforeClass 
	public static void initializeDb() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);
		LgSession session = new LgSession();
		session.getApplication().beginTransaction();
		final LgUser alice = new LgUser();
		alice.setName(ALICE_USER_NAME);
		alice.setEmail("test@test.de");
		alice.setPassword("testtest");
		session.register(alice);
		final LgUser bob = new LgUser();
		bob.setName("Bob");
		bob.setEmail("bob@test.de");
		bob.setPassword("testtest");
		session.register(bob);
		final LgUser pit = new LgUser();
		pit.setName("Pit");
		pit.setEmail("pit@test.de");
		pit.setPassword("testtest");
		session.register(pit);
		session.getApplication().endTransaction(true);	
	}
	
	@Test
	public void getSingleSurveyAsHost() {}
	
	@Test
	public void getManySurveysAsHost() {}
	
	@Test
	public void saveSingleSurveyAsHost() {}
	
	@Test
	public void getSingleInviteAsParticipant() {}
	
	@Test
	public void getManyInvitesAsParticipant() {}
	
	@Test
	public void saveInviteAsParticipant() {}
	
	@Test
	public void deleteInviteAsParticipant() {}
	
	
	public LgSession startSession(){
		LgSession session = new LgSession();
		session.getApplication().beginTransaction();
		return session;
	}
	
	public LgUser startSessionFor(String username){
		return new LgSession().startFor(username);
	}
	
	public void end(LgSession session){
		session.getApplication().endTransaction(true);
	}
}
