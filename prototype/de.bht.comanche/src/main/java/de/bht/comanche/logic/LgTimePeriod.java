package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.bht.comanche.persistence.DaObject;

/**
 * Table contains Time period data
 * Is used to describe the timeperiod of a survey or the availability of
 * users.
 * 
 * @author Duc Tung Tong
 */

@Entity
@Table(name = "TimePeriod")
public class LgTimePeriod extends DaObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * start time
	 */
	private Date startTime;
	
	/**
	 * duration in minutes
	 */
	private int durationMinutes;

	/**
	 * survey 
	 */
	@NotNull
	@ManyToOne
	private LgSurvey survey;
	
	/**
	 * invite 
	 */
	@NotNull
	@ManyToOne
	private LgInvite invite;

	/*
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	public Date getStartTime() {
		return this.startTime;
	}

	public LgTimePeriod setStartTime(final Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public int getDurationMinutes() {
		return this.durationMinutes;
	}

	public LgTimePeriod setDurationMinutes(final int durationMinutes) {
		this.durationMinutes = durationMinutes;
		return this;
	}

	public LgSurvey getSurvey() {
		return this.survey;
	}

	public LgTimePeriod setSurvey(final LgSurvey survey) {
		this.survey = survey;
		return this;
	}

	@Override
	public String toString() {
		return String
				.format("LgTimePeriod [startTime=%s, durationMinutes=%s, survey=%s, oid=%s, pool=%s]",
						startTime, durationMinutes, survey, oid, pool);
	}
}
