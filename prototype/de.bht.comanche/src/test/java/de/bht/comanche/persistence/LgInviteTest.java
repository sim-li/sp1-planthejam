package de.bht.comanche.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bht.comanche.logic.LgUser;

public class LgInviteTest {
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;
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
	}

	@Test
	public void getInviteTest() {}
	
	@Test
	public void getInvitesAsParticipantTest() {}
	
	@Test
	public void saveInviteTest() {}
	
	@Test
	public void updateInviteTest() {}
	
	@After
	public void tearDown() {
		testUtils.deleteAccountsFor("Alice", "Bob", "Carol");
	}
}
