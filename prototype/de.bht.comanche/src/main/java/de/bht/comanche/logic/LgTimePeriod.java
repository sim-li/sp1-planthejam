package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.bht.comanche.persistence.DaObject;

/**
 * A data type for time periods.
 * 
 * It is used to describe the possible time periods of a survey or the availability of users.
 * <p>
 * Important note: This class needs to <strong>override hashCode and equals</strong>, so that collections of 
 * LgTimePeriods are comparable. In the current version timePeriods are considered equal when ...
 * 
 * @author Duc Tung Tong
 */

//@Table(name = "TimePeriod")
@Embeddable public class LgTimePeriod {

	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	private int durationMins;
	
	public LgTimePeriod (){}
	
	public LgTimePeriod (final LgTimePeriod other){
		this.startTime = other.startTime;
		this.durationMins = other.durationMins;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public LgTimePeriod setStartTime(final Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public int getDurationMins() {
		return this.durationMins;
	}

	public LgTimePeriod setDurationMins(final int durationMins) {
		this.durationMins = durationMins;
		return this;
	}

	@Override
	public String toString() {
		return "LgTimePeriod [startTime=" + startTime + ", durationMins="
				+ durationMins + "]";
	}
}
