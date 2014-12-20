package de.bht.comanche.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgUser;

public class SurveyTest {
	final LgUser alice;
	final LgUser bob;
	final LgUser pit;
	final LgSession session;
	
	@BeforeClass
	public void resetJpaDatabase() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
	}
	
	@Before
	public void startSession() {
		session = new LgSession();
	}
	
	@Before
	public void beginTransaction() {
		session.getApplication().beginTransaction();
	}
	
	
	@After
	public void cleanUpDatabase() {
		deleteUnattachedUsers(alice, bob, pit);
	}
	
	public void deleteUnattachedUsers(LgUser ...users) {
		final DaPool poolFromCurrentTransaction = session.getApplication().getPool();
		for (final LgUser user : users) {
			user.attach(poolFromCurrentTransaction);
			user.deleteAccount();
		}
	}
	
	@After
	public void endTransaction() {
		session.getApplication().endTransaction(true);
	}
	
	private class OperationsOnUsers {
		private LgUser [] usersForOperations;
		
		public OperationsOnUsers(final LgUser ... users) {
			usersForOperations = users;
		}
		
		public void withEmails(final String ... emails) {
			final int maximumLength = smallestNumberOfElements(usersForOperations, emails);
			for (int i = 0; i < maximumLength; i++) {
				usersForOperations[i].setEmail(emails[i]);
			}
		}
		
		public int smallestNumberOfElements(final Object [] firstObject, final Object [] secondObject) {
			return Math.min(firstObject.length, secondObject.length); 
		}
		
	}
	
	public void givenUsers(LgUser ... users) {
		for (final LgUser user : users) {
			
		}
	}
	
	
	/**
	 * NEW-O
	 */
	@Test
	public void surveyIsCreatedWhenInvitingUsers() {
		givenUsers(alice, bob, sven)
			.withEmails("simon@fdas.com", "fdfa@cda.com", "fdas.com")
			.withPasswords("fdaa.com", "fdas.com", "fda");
	
		andAliceInviting(bob, sven)
			.withSurveyNamed("DemoSurvey");
		
		whenAliceSavesSurvey()
		
		thenAliceShouldHaveSurveyAsHost("DemoSurvey");
	}
	
	
	
	@Test
	public void usersReceiveSurveyWhenInvited() {
		givenUsers(alice, bob, sven)
			.withEmails("simon@fdas.com", "fdfa@cda.com", "fdas.com")
			.withPasswords("fdaa.com", "fdas.com", "fda");
	
		whenAliceInvites(bob, sven)
			.withSurveyNamed("DemoSurvey");
		
		thenParticipantsShouldReceiveSurvey("Demo Survey");
	}
	
	
	@Test
	public void XusersReceiveSurveyWhenInvited() {
		givenUsers(alice, bob, sven)
			.withEmails("simon@fdas.com", "fdfa@cda.com", "fdas.com")
			.withPasswords("fdaa.com", "fdas.com", "fda");
	
		whenAliceInvites(bob, sven)
			.withSurveyNamed("DemoSurvey");
		
		andInviteIsAcceptedBy(bob);
		
		thenParticipantsShouldReceiveSurvey("Demo Survey");
	}
	
	
	@Test
	public void 
	
		assertThatAliceCorrectlyAdded("Demo Survey");
		
		final LgUser bob = new LgUser();
		bob.setName("Bob");
		bob.setEmail("bob@test.de");
		bob.setPassword("testtest");
		
		final LgUser pit = new LgUser();
		pit.setName("Pit");
		pit.setEmail("pit@test.de");
		pit.setPassword("testtest");     // --> Remove, only use precondition
	
		// Refactor to submethods
		// --> Instance vars for test objs
		// One concept per test
		LgSession sessionForCreate = createSessionAndStartTransaction(); // Move to precondition, save in instance variable
		LgUser alice = sessionForCreate.startFor(ALICE_USER_NAME); // Move to precondition, save in instance variable
		
		final LgSurvey demoSurvey = new LgSurvey(); 
		demoSurvey.setName("DemoSurvey");
		
		alice.saveSurveyAsHost(demoSurvey);
		
		// Domain specific language? addUsersToSurvey()
		demoSurvey.inviteOtherUser(bob);
		demoSurvey.inviteOtherUser(pit);

		endTransaction(sessionForCreate);
		
		LgSession sessionForValidation = createSessionAndStartTransaction();
		sessionForValidation.startFor(ALICE_USER_NAME);
		
		LgSurvey persistedSurvey = sessionForValidation
			.getUser()
				.getSurveyByName(demoSurvey.getName());
		
	    LgInvite bobsInvite = persistedSurvey.getInviteByParticipantName(bob.getName());
		LgInvite pitsInvite = persistedSurvey.getInviteByParticipantName(pit.getName());
		
		//ASSERT FALSE
		assertEquals("Bob's invite has status 'participant'", false, bobsInvite.isHost());
		assertEquals("Bob is user of his invite", bob.getName(), bobsInvite.getUser().getName());
		assertEquals("Bob's invite is not set to isIgnored", false, bobsInvite.isIgnored());
	    assertEquals("Pit's invite has status 'participant'", false, bobsInvite.isHost());
	    assertEquals("Pit is user of the invite", pit.getName(), pitsInvite.getUser().getName());
	    assertEquals("Pit's invite is not set to isIgnored", false, pitsInvite.isIgnored());
	    
	    LgInvite alicesInviteFromDemoSurvey =
    			sessionForValidation
				.getUser()
					.getInviteBySurveyName(demoSurvey.getName());
	    
	    assertEquals("Alice has an invite containing DemoSurvey", demoSurvey.getName(), alicesInviteFromDemoSurvey.getSurvey().getName());
	    assertEquals("Alice's has status 'host' of her invite", true, alicesInviteFromDemoSurvey.isHost());
	    assertEquals("Alice's invite is not ignored", false, alicesInviteFromDemoSurvey.isIgnored());
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
	public void saveInviteAsParticipant() {}
	
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
