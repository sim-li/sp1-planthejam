package de.bht.comanche.persistence;

import static org.junit.Assert.*;
import org.junit.Test;
import de.bht.comanche.logic.LgTimePeriod;

public class EvaluationTest {

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
