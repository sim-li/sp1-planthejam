package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class SurveyEqualsTest {
	private Set<LgTimePeriod> someTimePeriods;
	private LgSurvey aSurvey;
	private LgSurvey aTwinSurvey;
	
	@Before
	public void buildUp() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			final Date startTime = sdf.parse("21/12/2012");
			final Date endTime = sdf.parse("21/12/2014");
			someTimePeriods = new HashSet<LgTimePeriod>();
			aSurvey = new LgSurvey();
			aTwinSurvey = new LgSurvey();
			someTimePeriods.add(new LgTimePeriod().setStartTime(startTime).setEndTime(endTime));
			aSurvey.setName("Alice").setPossibleTimePeriods(someTimePeriods);
			aTwinSurvey.setName("Alice").setPossibleTimePeriods(someTimePeriods);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Note: This rule was once implemented in the opposite matter.
	 */
	@Test
	public void equalsIgnoresEmptyOidTest() {
		aSurvey.setOid(1);
		assertNotEquals("Two surveys with same data and one empty oid are not equal", aSurvey, aTwinSurvey);
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
