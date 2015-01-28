// package de.bht.comanche.persistence;

// import static org.fest.assertions.api.Assertions.assertThat;
// import static org.fest.assertions.api.Assertions.extractProperty;

// import java.util.ArrayList;
// import java.util.LinkedList;
// import java.util.List;

// import org.junit.After;
// import org.junit.Before;
// import org.junit.BeforeClass;
// import org.junit.Ignore;
// import org.junit.Test;

// import de.bht.comanche.logic.LgInvite;
// import de.bht.comanche.logic.LgSession;
// import de.bht.comanche.logic.LgStatus;
// import de.bht.comanche.logic.LgSurvey;
// import de.bht.comanche.logic.LgUser;
// import de.bht.comanche.rest.ReErrorMessage;
// import de.bht.comanche.rest.ReServerException;

// public class LgInviteTest {
// 	private TestUtils testUtils;
// 	private LgUser alice;
// 	private LgUser bob;
// 	private LgUser carol;
// 	private LgUser denise;
// 	private LgUser edgar;
// 	private LgSurvey sportingSurvey;
// 	private LgSurvey swimSurvey;
// 	private LgSurvey denimSurvey;
// 	private LgInvite sportingSurveyInvite;

// 	@BeforeClass
// 	public static void init() {
// 		TestUtils.resetJPADatabase();
// 	}

// 	@Before
// 	public void buildUp() {
// 		testUtils = new TestUtils();
// 		registerUsers();
// 		saveSurveys();
// 		sportingSurveyInvite = sportingSurvey.getInviteByParticipantName("Bob");
// 	}

// 	private void registerUsers() {
// 		bob = testUtils.registerTestUser("Bob");
// 		alice = testUtils.registerTestUser("Alice");
// 		carol = testUtils.registerTestUser("Carol");
// 		denise = testUtils.registerTestUser("Denise");
// 		edgar = testUtils.registerTestUser("Edgar");
// 	}

// 	/**
// 	 * Saves various test surveys which include Bob as participant. He is not
// 	 * invited to Lou Reed Music Club. This way we can check that he really
// 	 * receives only the invites he's a participant in.
// 	 */
// 	private void saveSurveys() {
// 		sportingSurvey = testUtils.saveSurveyFor("Alice",
// 				new LgSurvey().addParticipants(bob, carol, denise, edgar)
// 						.setName("Sporting Event"));
// 		swimSurvey = testUtils.saveSurveyFor("Carol",
// 				new LgSurvey().addParticipants(bob, alice, denise, edgar)
// 						.setName("Swim Course"));
// 		denimSurvey = testUtils.saveSurveyFor("Edgar",
// 				new LgSurvey().addParticipants(bob, alice, carol, denise)
// 						.setName("Denim Jeans Reunion"));
// 		testUtils.saveSurveyFor(
// 				"Denise",
// 				new LgSurvey().addParticipants(alice, carol, edgar).setName(
// 						"Lou Reed Music Club"));
// 	}

// 	@Test
// 	public void getInviteByOidUserNameTest() {
// 		final List<LgInvite> invitesForEvaluation = getAllInvitesByOid();
// 		assertThat(extractProperty("user.name").from(invitesForEvaluation))
// 				.containsOnly("Bob", "Bob", "Bob");
// 	}

// 	@Test
// 	public void getInviteByOidSurveyNameTest() {
// 		final List<LgInvite> invitesForEvaluation = getAllInvitesByOid();
// 		assertThat(extractProperty("survey.name").from(invitesForEvaluation))
// 				.containsOnly("Sporting Event", "Swim Course",
// 						"Denim Jeans Reunion");
// 	}

// 	/**
// 	 * Executes getInvite([ oid ]) on all surveys. We don't check on the Lou
// 	 * Reed Survey (throw null pointer exception, should be corrected in other
// 	 * moment)
// 	 *
// 	 * @return Invites fetched from surveys.
// 	 */
// 	private List<LgInvite> getAllInvitesByOid() {
// 		final List<LgInvite> invitesForEvaluation = new TestTransaction<List<LgInvite>>(
// 				"Bob") {
// 			@Override
// 			public List<LgInvite> execute() {
// 				final List<LgInvite> invitesFetch = new ArrayList<LgInvite>();
// 				for (LgInvite invite : getInvitesFromSurveys(sportingSurvey,
// 						swimSurvey, denimSurvey)) {
// 					invitesFetch.add(startSession().getInvite(invite.getOid()));
// 				}
// 				return invitesFetch;
// 			}
// 		}.getResult();
// 		return invitesForEvaluation;
// 	}

// 	/**
// 	 * Extracts the invites for Bob from the given surveys.
// 	 *
// 	 * @param Surveys
// 	 *            to be queried
// 	 * @return Invites for Bob
// 	 */
// 	private List<LgInvite> getInvitesFromSurveys(LgSurvey... surveys) {
// 		final List<LgInvite> invites = new LinkedList<LgInvite>();
// 		for (LgSurvey survey : surveys) {
// 			invites.add(survey.getInviteByParticipantName("Bob"));
// 		}
// 		return invites;
// 	}

// 	@Test
// 	public void getInvitesAsParticipantIsIgnoredFlagTest() {
// 		final List<LgInvite> invites = getInvitesAsParticipantFor("Bob");
// 		assertThat(extractProperty("isIgnored").from(invites)).containsOnly(
// 				LgStatus.UNDECIDED);
// 	}

// 	@Test
// 	public void getInvitesAsParticipantIsHostFlagTest() {
// 		final List<LgInvite> invites = getInvitesAsParticipantFor("Bob");
// 		// Check that only surveys ment for Bob are retrieved.
// 		// Bob should not receive Lou Reed Music Club!
// 		assertThat(extractProperty("isHost").from(invites)).containsExactly(
// 				false, false, false);
// 	}

// 	@Test
// 	public void getInvitesAsParticipantSurveyNameTest() {
// 		final List<LgInvite> invites = getInvitesAsParticipantFor("Bob");
// 		assertThat(extractProperty("survey.name").from(invites)).containsOnly(
// 				"Sporting Event", "Swim Course", "Denim Jeans Reunion");
// 	}

// 	private List<LgInvite> getInvitesAsParticipantFor(String username) {
// 		final List<LgInvite> invites = new TestTransaction<List<LgInvite>>(
// 				username) {
// 			@Override
// 			public List<LgInvite> execute() {
// 				return startSession().getInvitesAsParticipant();
// 			}
// 		}.getResult();
// 		return invites;
// 	}

// 	@Test
// 	public void updateInviteSetConcreteAvailabilityTest() {
// 		updatePossibleTimeperiodsSportingSurvey(20, 40, 60);
// 		assertThat(
// 				extractProperty("durationMins").from(
// 						sportingSurveyInvite.getConcreteAvailability()))
// 				.containsOnly(20, 40, 60);
// 	}

// 	/**
// 	 * Note: The call to updateInvite is internally forwarded to saveInvite.
// 	 */
// 	@Test
// 	public void updateInviteChangeConcreteAvailabilityTest() {
// 		updatePossibleTimeperiodsSportingSurvey(20, 40, 60);
// 		updatePossibleTimeperiodsSportingSurvey(20, 80, 60);
// 		assertThat(
// 				extractProperty("durationMins").from(
// 						sportingSurveyInvite.getConcreteAvailability()))
// 				.containsOnly(20, 80, 60);
// 	}

// 	@Test
// 	public void updateInviteDeleteOneConcreteAvailabilityTest() {
// 		updatePossibleTimeperiodsSportingSurvey(20, 40, 60);
// 		updatePossibleTimeperiodsSportingSurvey(20, 60);
// 		assertThat(
// 				extractProperty("durationMins").from(
// 						sportingSurveyInvite.getConcreteAvailability()))
// 				.containsOnly(20, 60);
// 	}

// 	@Test
// 	public void updateInviteDeleteAllConcreteAvailabilitiesTest() {
// 		updatePossibleTimeperiodsSportingSurvey(20, 40, 60);
// 		updatePossibleTimeperiodsSportingSurvey();
// 		assertThat(sportingSurveyInvite.getConcreteAvailability()).isEmpty();
// 	}

// 	/**
// 	 * Updates Bob's sporting survey invites with given durations.
// 	 */
// 	private void updatePossibleTimeperiodsSportingSurvey(int... durations) {
// 		sportingSurveyInvite.setConcreteAvailability(testUtils
// 				.buildTimePeriods(durations));
// 		sportingSurveyInvite = new TestTransaction<LgInvite>("Bob") {
// 			@Override
// 			public LgInvite execute() {
// 				return startSession().updateInvite(sportingSurveyInvite);
// 			}
// 		}.getResult();
// 	}

// 	@After
// 	public void tearDown() {
// 		testUtils.deleteAccountsFor("Alice", "Bob", "Carol", "Denise", "Edgar");
// 	}
// }
