package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;

public class SurveyTest {
	final static String ALICE_USER_NAME = "Alice";
	final static String BOB_USER_NAME = "Bob";
	final static String PIT_USER_NAME = "Pit";
	final static String DEMO_SURVEY_NAME = "DemoSurvey";
	
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
		
		LgUser aliceLoggedIn = session.startFor(ALICE_USER_NAME);
		final LgSurvey demoSurvey = new LgSurvey();
		demoSurvey.setName(DEMO_SURVEY_NAME);
		demoSurvey.inviteOtherUser(bob);
		demoSurvey.inviteOtherUser(pit);
		aliceLoggedIn.saveSurveyAsHost(demoSurvey);
		assertEquals("Demo survey has three invites", 3, demoSurvey.getInvites().size());
		assertEquals("Demo survey has reference to host", demoSurvey.getHost().getName(), aliceLoggedIn.getName());
		session.getApplication().endTransaction(true);
	}
	
	@Before
	public void addDemoSurvey() {
		//
	}
	
	/**
	 * FOR IMPLEMENTATION: call saveSurveyAsHost in rest-service
	 */
	@Test
	public void saveSingleSurveyAsHost() {
		LgSession sessionForValidation = createSessionAndStartTransaction();
		sessionForValidation.startFor(ALICE_USER_NAME);
		assertEquals("Alice is logged in", ALICE_USER_NAME, sessionForValidation.getUser().getName());
		LgSurvey persistedSurvey = sessionForValidation
			.getUser()
				.getSurveyByName(DEMO_SURVEY_NAME);
		
	    LgInvite bobsInvite = persistedSurvey.getInviteByParticipantName(BOB_USER_NAME);
		LgInvite pitsInvite = persistedSurvey.getInviteByParticipantName(PIT_USER_NAME);
		
		assertEquals("Bob's invite has status 'participant'", false, bobsInvite.isHost());
		assertEquals("Bob is user of his invite", BOB_USER_NAME, bobsInvite.getUser().getName());
		assertEquals("Bob's invite is not set to isIgnored", false, bobsInvite.isIgnored());
	    assertEquals("Pit's invite has status 'participant'", false, bobsInvite.isHost());
	    assertEquals("Pit is user of the invite", PIT_USER_NAME, pitsInvite.getUser().getName());
	    assertEquals("Pit's invite is not set to isIgnored", false, pitsInvite.isIgnored());
	    
	    LgInvite alicesInviteFromDemoSurvey =
    			sessionForValidation
					.getUser()
						.getInviteBySurveyName(DEMO_SURVEY_NAME);
	    
	    assertEquals("Alice has an invite containing DemoSurvey", DEMO_SURVEY_NAME, alicesInviteFromDemoSurvey.getSurvey().getName());
	    assertEquals("Alice's has status 'host' of her invite", true, alicesInviteFromDemoSurvey.isHost());
	    assertEquals("Alice's invite is not ignored", false, alicesInviteFromDemoSurvey.isIgnored());
	    endTransaction(sessionForValidation);
	}
	
	@Ignore
	@Test
	public void saveExistingInviteAsParticipant() {
		final LgSession sessionForCreate = createSessionAndStartTransaction();
		sessionForCreate.startFor(BOB_USER_NAME);
		
		final LgUser bob = sessionForCreate.getUser();
		
		final LgInvite existingInviteOnServer = bob.getInviteBySurveyName(DEMO_SURVEY_NAME);

		LgInvite updatedInviteFromClient = new LgInvite();
		updatedInviteFromClient.setHost(false);
		updatedInviteFromClient.setIgnored(true);
		updatedInviteFromClient.setOid(existingInviteOnServer.getOid());
		
		bob.save(updatedInviteFromClient);
	
		endTransaction(sessionForCreate);
		
		final LgSession sessionForValidation = createSessionAndStartTransaction();
		sessionForValidation.startFor(BOB_USER_NAME);
		final LgInvite persistedInviteOnServer = bob.getInviteBySurveyName(DEMO_SURVEY_NAME);
		
		assertEquals("Invite was updated and has same properties on server", updatedInviteFromClient, persistedInviteOnServer);
		
		endTransaction(sessionForValidation);

	}
	
	@Ignore
	@Test
	public void getSingleSurveyAsHost() {
	}
	
	@Ignore
	@Test
	public void getManySurveysAsHost() {}
	
	@Ignore
	@Test
	public void getSingleInviteAsParticipant() {}
	
	@Ignore
	@Test
	public void getManyInvitesAsParticipant() {}
	
	@Ignore
	@Test
	public void deleteInviteAsParticipant() {}
	
	
	public LgSession createSessionAndStartTransaction(){
		LgSession session = new LgSession();
		session.getApplication().beginTransaction();
		return session;
	}
	
	public void endTransaction(LgSession session){
		session.getApplication().endTransaction(true);
	}
	
}
