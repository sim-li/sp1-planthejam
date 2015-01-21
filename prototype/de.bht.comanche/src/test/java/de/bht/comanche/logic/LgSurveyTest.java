package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class LgSurveyTest {
	private Set<LgTimePeriod> someTimePeriods;
	private LgSurvey aSurvey;
	private LgSurvey aTwinSurvey;
	
	@Before
	public void buildUp() {
		someTimePeriods = new HashSet<LgTimePeriod>();
		aSurvey = new LgSurvey();
		aTwinSurvey = new LgSurvey();
		someTimePeriods.add(new LgTimePeriod().setDurationMins(39).setStartTime(new Date()));
		aSurvey.setName("Alice").setPossibleTimePeriods(someTimePeriods);
		aTwinSurvey.setName("Alice").setPossibleTimePeriods(someTimePeriods);
	}
	
	@Test
	public void equalsIgnoresEmptyOidTest() {
		aSurvey.setOid(1);
		assertEquals("Two surveys with same data and one empty oid are equal", aSurvey, aTwinSurvey);
	}
	
	@Test
	public void equalsIgnoresOidTest() {
		aSurvey.setOid(1);
		aTwinSurvey.setName("Bob");
		aTwinSurvey.setOid(1);
		assertNotEquals("Two Surveys with different attributes and same oid are different", aSurvey, aTwinSurvey);
	}
	
	@Test 
	public void equalsConsidersTwoDifferentOidsTest() {	
		aSurvey.setOid(1);
		aSurvey.setName("Alice");
	    aTwinSurvey.setOid(2);
	    aTwinSurvey.setName("Alice");
	    assertNotEquals("Two Surveys with different oids and same attributes are different", aSurvey, aTwinSurvey);
	}
}
