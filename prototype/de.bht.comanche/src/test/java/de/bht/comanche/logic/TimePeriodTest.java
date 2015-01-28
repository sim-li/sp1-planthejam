package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.bht.comanche.logic.LgTimePeriod;

public class TimePeriodTest {
	@Test
	@SuppressWarnings("unused")
	public void nulledTimePeriodImplCorrectly() {
		LgTimePeriod nulledTimePeriod = LgTimePeriod.EMPTY_TIMEPERIOD;
		assertEquals(-1, differenceInSeconds(nulledTimePeriod));
	}
	
	public long differenceInSeconds(LgTimePeriod timePeriod ) {
		return (timePeriod.getEndTime().getTime() - timePeriod.getStartTime().getTime()) / 60000;
	}
	
	
}
