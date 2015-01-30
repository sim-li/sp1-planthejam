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

/**
 * 
 * Tests operations on invite class
 * 
 * @author Simon Lischka
 *
 */
public class LgInviteTest {
	private TestUtils testUtils;
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;
	private LgUser denise;
	private LgUser edgar;
	private LgSurvey sportingSurvey;
	private LgSurvey swimSurvey;
	private LgSurvey denimSurvey;
	private LgInvite sportingSurveyInvite;

	@BeforeClass
	public static void init() {
		TestUtils.resetJPADatabase();
	}

	@Before
	public void buildUp() {
		testUtils = new TestUtils();
		registerUsers();
		saveSurveys();
		sportingSurveyInvite = sportingSurvey.getInviteByParticipantName("Bob");
	}

	private void registerUsers() {
		bob = testUtils.registerTestUser("Bob");
		alice = testUtils.registerTestUser("Alice");
		carol = testUtils.registerTestUser("Carol");
		denise = testUtils.registerTestUser("Denise");
		edgar = testUtils.registerTestUser("Edgar");
	}

	/**
	 * Saves various test surveys which include Bob as participant. He is not
	 * invited to Lou Reed Music Club. This way we can check that he really
	 * receives only the invites he's a participant in.
	 */
	private void saveSurveys() {
		sportingSurvey = testUtils.saveSurveyFor("Alice",
				new LgSurvey().addParticipants(bob, carol, denise, edgar)
						.setName("Sporting Event"));
		swimSurvey = testUtils.saveSurveyFor("Carol",
				new LgSurvey().addParticipants(bob, alice, denise, edgar)
						.setName("Swim Course"));
		denimSurvey = testUtils.saveSurveyFor("Edgar",
				new LgSurvey().addParticipants(bob, alice, carol, denise)
						.setName("Denim Jeans Reunion"));
		testUtils.saveSurveyFor(
				"Denise",
				new LgSurvey().addParticipants(alice, carol, edgar).setName(
						"Lou Reed Music Club"));
	}

	@Test
	public void getInviteByOidUserNameTest() {
		final List<LgInvite> invitesForEvaluation = getAllInvitesByOid();
		assertThat(extractProperty("user.name").from(invitesForEvaluation))
				.containsOnly("Bob", "Bob", "Bob");
	}

	@Test
	public void getInviteByOidSurveyNameTest() {
		final List<LgInvite> invitesForEvaluation = getAllInvitesByOid();
		assertThat(extractProperty("survey.name").from(invitesForEvaluation))
				.containsOnly("Sporting Event", "Swim Course",
						"Denim Jeans Reunion");
	}

	/**
	 * Executes getInvite([ oid ]) on all surveys. We don't check on the Lou
	 * Reed Survey (throw null pointer exception, should be corrected in other
	 * moment)
	 *
	 * @return Invites fetched from surveys.
	 */
	private List<LgInvite> getAllInvitesByOid() {
		final List<LgInvite> invitesForEvaluation = new TestTransaction<List<LgInvite>>(
				"Bob") {
			@Override
			public List<LgInvite> execute() {
				final List<LgInvite> invitesFetch = new ArrayList<LgInvite>();
				for (LgInvite invite : getInvitesFromSurveys(sportingSurvey,
						swimSurvey, denimSurvey)) {
					invitesFetch.add(startSession().getInvite(invite.getOid()));
				}
				return invitesFetch;
			}
		}.getResult();
		return invitesForEvaluation;
	}

	/**
	 * Extracts the invites for Bob from the given surveys.
	 *
	 * @param Surveys
	 *            to be queried
	 * @return Invites for Bob
	 */
	private List<LgInvite> getInvitesFromSurveys(LgSurvey... surveys) {
		final List<LgInvite> invites = new LinkedList<LgInvite>();
		for (LgSurvey survey : surveys) {
			invites.add(survey.getInviteByParticipantName("Bob"));
		}
		return invites;
	}

	@Test
	public void getInvitesAsParticipantIsIgnoredFlagTest() {
		final List<LgInvite> invites = getInvitesAsParticipantFor("Bob");
		assertThat(extractProperty("isIgnored").from(invites)).containsOnly(
				LgStatus.UNDECIDED);
	}

	@Test
	public void getInvitesAsParticipantIsHostFlagTest() {
		final List<LgInvite> invites = getInvitesAsParticipantFor("Bob");
		// Check that only surveys ment for Bob are retrieved.
		// Bob should not receive Lou Reed Music Club!
		assertThat(extractProperty("isHost").from(invites)).containsExactly(
				false, false, false);
	}

	@Test
	public void getInvitesAsParticipantSurveyNameTest() {
		final List<LgInvite> invites = getInvitesAsParticipantFor("Bob");
		assertThat(extractProperty("survey.name").from(invites)).containsOnly(
				"Sporting Event", "Swim Course", "Denim Jeans Reunion");
	}

	private List<LgInvite> getInvitesAsParticipantFor(String username) {
		final List<LgInvite> invites = new TestTransaction<List<LgInvite>>(
				username) {
			@Override
			public List<LgInvite> execute() {
				return startSession().getInvitesAsParticipant();
			}
		}.getResult();
		return invites;
	}

	@Test
	public void updateInviteSetConcreteAvailabilityTest() {
		updateConcreteAvailabilitySportingInvite(
				"31.01.1986/21:30 -> 30.01.1986/22:30",
				"01.05.1999/21:30 -> 01.07.2005/21:30",
				"08.09.2005/00:30 -> 01.06.2006/20:30"
				);
		assertThat(
				sportingSurveyInvite.getConcreteAvailability())
				.containsOnly(
						testUtils.tP("31.01.1986/21:30 -> 30.01.1986/22:30"),
						testUtils.tP("01.05.1999/21:30 -> 01.07.2005/21:30"),
						testUtils.tP("08.09.2005/00:30 -> 01.06.2006/20:30")
						);
	}

	/**
	 * Note: The call to updateInvite is internally forwarded to saveInvite.
	 */
	@Test
	public void updateInviteChangeConcreteAvailabilityTest() {
		saveConcreteAvailabilites();
		updateConcreteAvailabilitySportingInvite(
				"31.01.1986/21:30 -> 30.01.1986/23:30",
				"01.05.1999/22:30 -> 01.07.2005/21:30",
				"08.10.2005/00:30 -> 01.06.2009/20:30");
		assertThat(
				sportingSurveyInvite.getConcreteAvailability())
				.containsOnly(
						testUtils.tP("31.01.1986/21:30 -> 30.01.1986/23:30"),
						testUtils.tP("01.05.1999/22:30 -> 01.07.2005/21:30"),
						testUtils.tP("08.10.2005/00:30 -> 01.06.2009/20:30")
						);
	}

	@Test
	public void updateInviteDeleteOneConcreteAvailabilityTest() {
		saveConcreteAvailabilites();
		updateConcreteAvailabilitySportingInvite(
				"31.01.1986/21:30 -> 30.01.1986/22:30",
				"08.09.2005/00:30 -> 01.06.2006/20:30");
		assertThat(
				sportingSurveyInvite.getConcreteAvailability())
				.containsOnly(
						testUtils.tP("31.01.1986/21:30 -> 30.01.1986/22:30"),
						testUtils.tP("08.09.2005/00:30 -> 01.06.2006/20:30"))
			    .hasSize(2);
	}

	@Test
	public void updateInviteDeleteAllConcreteAvailabilitiesTest() {
		saveConcreteAvailabilites();
		updateConcreteAvailabilitySportingInvite();
		assertThat(sportingSurveyInvite.getConcreteAvailability()).isEmpty();
	}

	public void saveConcreteAvailabilites() {
		updateConcreteAvailabilitySportingInvite(
		"31.01.1986/21:30 -> 30.01.1986/22:30",
		"01.05.1999/21:30 -> 01.07.2005/21:30",
		"08.09.2005/00:30 -> 01.06.2006/20:30");
	}

	/**
	 * Updates Bob's sporting survey invites with given durations.
	 */
	private void updateConcreteAvailabilitySportingInvite(String... dateStrings) {
		sportingSurveyInvite.setConcreteAvailability(testUtils
				.buildTimePeriods(dateStrings));
		sportingSurveyInvite = new TestTransaction<LgInvite>("Bob") {
			@Override
			public LgInvite execute() {
				return startSession().updateInvite(sportingSurveyInvite);
			}
		}.getResult();
	}

	@After
	public void tearDown() {
		testUtils.deleteAccountsFor("Alice", "Bob", "Carol", "Denise", "Edgar");
	}
}
