package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.bht.comanche.logic.LgTimePeriod;

public class TimePeriodTest {
	
	@Test
	public void nulledTimePeriodImplCorrectly() {
		LgTimePeriod nulledTimePeriod = LgTimePeriod.EMPTY_TIMEPERIOD;
		assertEquals(-1, differenceInSeconds(nulledTimePeriod));
	}
	
	public long differenceInSeconds(LgTimePeriod timePeriod ) {
		final long startTime = timePeriod.getStartTime().getTime();
		final long endTime = timePeriod.getEndTime().getTime();
		final long res = (endTime - startTime) / 60000;
		return res;
	}
	
	
}
