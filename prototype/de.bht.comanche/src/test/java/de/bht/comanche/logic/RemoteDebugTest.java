package de.bht.comanche.logic;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoteDebugTest {
	@Test
	public void mytest() {
		System.out.println("Hello");
		assertEquals(true, true);
		try {
			throw new NullPointerException();
		} catch (Exception e) {
			
		}
	}
}
