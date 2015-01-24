package de.bht.comanche.persistence;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

import java.util.Collection;
import java.util.LinkedList;

import de.bht.comanche.logic.LgUser;

import org.junit.Test;


public class IterablesTest {
	@Test
	public void tPass() {
		Collection<LgUser> col = new LinkedList<LgUser>();
		col.add(new LgUser().setName("Hello"));
		col.add(new LgUser().setName("Kitty"));
		assertThat(
				extractProperty("user.name").from(
						col))
				.contains("Hello", "Kitty");
	}
	
	public void tFail() {
		Collection<LgUser> col = new LinkedList<LgUser>();
		col.add(new LgUser().setName("Hello"));
		col.add(new LgUser().setName("Kitty"));
		assertThat(
				extractProperty("name").from(
						col))
				.containsExactly("Hello");
	}

}
