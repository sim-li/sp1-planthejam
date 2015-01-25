package de.bht.comanche.persistence;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSession;
import de.bht.comanche.logic.LgStatus;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.rest.ReErrorMessage;
import de.bht.comanche.rest.ReServerException;

public class LgInviteTest {
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;
	private LgUser denise;
    private LgUser edgar;
    private List<LgInvite> invites = new LinkedList<LgInvite>();
	private TestUtils testUtils = new TestUtils();

	@BeforeClass
	public static void init() {
		TestUtils.resetJPADatabase();
	}
	
	@Before
	public void buildUp() {
		alice = testUtils.registerTestUser("Alice");
		bob = testUtils.registerTestUser("Bob");
		carol = testUtils.registerTestUser("Carol");
		denise = testUtils.registerTestUser("Denise");
		edgar = testUtils.registerTestUser("Edgar");
		saveInvitesFor(bob); 
	}

	private void saveInvitesFor(LgUser user) {
		final LgSurvey survey1 = testUtils.saveSurveyFor("Alice", new LgSurvey()
		.addParticipants(user, carol, denise, edgar)
		.setName("Sporting Event"));
		final LgSurvey survey2 = testUtils.saveSurveyFor("Carol", new LgSurvey()
		.addParticipants(user, alice, denise, edgar)
		.setName("Swim Course"));
		final LgSurvey survey3 = testUtils.saveSurveyFor("Denise", new LgSurvey()
		.addParticipants(alice, carol, edgar)
		.setName("Lou Reed Music Club"));
		final LgSurvey survey4 = testUtils.saveSurveyFor("Edgar", new LgSurvey()
		.addParticipants(user, alice, carol, denise)
		.setName("Denim Jeans Reunion"));
		invites.add(survey1.getInviteByParticipantName("Bob"));
		invites.add(survey2.getInviteByParticipantName("Bob"));
		invites.add(survey3.getInviteByParticipantName("Bob"));
		invites.add(survey4.getInviteByParticipantName("Bob"));
	}
	
	@Ignore
	@Test
	public void getInviteByOidUserNameTest() {
		final List<LgInvite> invitesForEvaluation = getAllInvitesByOid();
		assertThat(
				extractProperty("user.name").from(
						invitesForEvaluation)).containsOnly(
								"Bob", "Bob", "Bob");
	}
	@Test
	public void getInviteByOidSurveyNameTest() {
		final List<LgInvite> invitesForEvaluation = getAllInvitesByOid();
		assertThat(
			extractProperty("survey.name").from(
					invitesForEvaluation)).containsOnly(
							"Sporting Event", "Swim Course", "Denim Jeans Reunion");
	}
	
	private List<LgInvite> getAllInvitesByOid() {
		final List<LgInvite> invitesForEvaluation = new TestTransaction<List<LgInvite>>("Bob") {
			@Override
			public List<LgInvite> execute() {
				final List <LgInvite> invitesFetch = new ArrayList<LgInvite>();
				final LgUser bob = startSession();
				for (LgInvite inv : invites) {
					invitesFetch.add(bob.getInvite(inv.getOid()));
				}
				return invitesFetch;
			}
		}.getResult();
		return invitesForEvaluation;
	}
	
	/**
	 * Note: Surveys will be transmitted with invites, we are checking for invites form certain perspective
	 */
	
	@Ignore
	@Test
	public void getInvitesAsParticipantIsIgnoredFlagTest() {
		final List<LgInvite> invites = getInvitesAsParticipantFor("Bob");
		assertThat(
				extractProperty("isIgnored").from(
						invites)).containsOnly(
								LgStatus.UNDECIDED);
	}
	
	@Ignore
	@Test
	public void getInvitesAsParticipantIsHostFlagTest() {
		final List<LgInvite> invites = getInvitesAsParticipantFor("Bob");
		// Check that only surveys ment for Bob are retrieved.
		// Bob should not receive Lou Reed Music Club!
		assertThat(
				extractProperty("isHost").from(
						invites)).containsExactly(
								false, false, false);
	}
	
	@Ignore
	@Test
	public void getInvitesAsParticipantSurveyNameTest() {
		final List<LgInvite> invites = getInvitesAsParticipantFor("Bob");
		assertThat(
				extractProperty("survey.name").from(
						invites)).containsOnly(
								"Sporting Event", "Swim Course", "Denim Jeans Reunion");
	}

	private List<LgInvite> getInvitesAsParticipantFor(String username) {
		final List<LgInvite> invites = new TestTransaction<List<LgInvite>>(username) {
			@Override
			public List<LgInvite> execute() {
				return startSession().getInvitesAsParticipant();
			}
		}.getResult();
		return invites;
	}
	
	@Ignore
	@Test
	public void updateInviteSetConcreteAvailabilityTest() {
		final LgInvite inviteForEvaluation = updatePossibleTimePeriodsWith(20, 40, 60);
		assertThat(
				extractProperty("durationMins").from(inviteForEvaluation.getPossibleTimePeriods()))
					.containsOnly(
							20, 40, 60
							);
	}
	
	/**
	 * Note: The call to updateInvite is internally forwarded to saveInvite.
	 */
	@Ignore
	@Test
	public void updateInviteChangeConcreteAvailabilityTest() {
		final LgInvite inviteForEvaluation = updatePossibleTimePeriodsWith(20, 80, 60);
		assertThat(
				extractProperty("durationMins").from(inviteForEvaluation.getPossibleTimePeriods()))
					.containsOnly(
							20, 80, 60
							);
	}
	
	@Ignore
	@Test
	public void updateInviteDeleteOneConcreteAvailabilityTest() {
		final LgInvite inviteForEvaluation = updatePossibleTimePeriodsWith(20, 60);
		assertThat(
				extractProperty("durationMins").from(inviteForEvaluation.getPossibleTimePeriods()))
					.containsOnly(
							20, 60
							);
	}
	
	@Ignore
	@Test
	public void updateInviteDeleteAllConcreteAvailabilitiesTest() {
		final LgInvite inviteForEvaluation = updatePossibleTimePeriodsWith();
		assertThat(
				inviteForEvaluation.getPossibleTimePeriods())
					.isEmpty();
	}
	
	private LgInvite updatePossibleTimePeriodsWith(int ... durations) {
		final LgInvite aPersistedInvite = invites.get(0);
		aPersistedInvite.setPossibleTimePeriods(testUtils.buildTimePeriods(20, 60));
		return new TestTransaction<LgInvite>("Bob") {
			@Override
			public LgInvite execute() {
				return startSession().updateInvite(aPersistedInvite);
			}
		}.getResult();
	}
	
	@After
	public void tearDown() {
		testUtils.deleteAccountsFor("Alice", "Bob", "Carol", "Denise", "Edgar");
	}
}
