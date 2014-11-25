package de.bht.comanche.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Sebastian Dass&eacute;
 *
 */
@Ignore
public class LgUserTest {
	
	// TODO we need some kind of mocked JPA; then add tests for save and delete etc.
	
	@Test
	public void createAnEmptyUser() {
		final LgUser user = new LgUser();
		assertTrue(user != null);
		assertNotNull(user.getInvites());
		assertNotNull(user.getGroups());
	}
	
	@Test
	public void passwordMatchWorks() {
		final String alicesPassword = "supercalifragilisticexpialigotious";
		final LgUser alice = new LgUser().setName("Alice").setPassword(alicesPassword);
		final LgUser bob = new LgUser().setName("Bob").setPassword("shhhh");
		assertFalse(alice.passwordMatchWith(bob));
		bob.setPassword(alicesPassword);
		assertTrue(alice.passwordMatchWith(bob));
	}
}
