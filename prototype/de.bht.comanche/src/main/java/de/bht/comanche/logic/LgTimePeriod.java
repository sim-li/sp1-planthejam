package de.bht.comanche.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private final String DATE_PATTERN = "yyyy.MM.dd G HH:mm:ss z";
	private final DateFormat df = new SimpleDateFormat(DATE_PATTERN);
	
	/**
	 * start time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	
	/**
	 * duration in minutes
	 */
	private int durationMins;

	// QUESTION: Do we need to define back-refs? 
	// Can't LgUser/LgSurvey have an array list & ManyToOne
	
//	/**
//	 * Column for LgSurvey foreign key 
//	 */
//	@JsonIgnore
//	@ManyToOne
//	private LgSurvey survey;
//	
//	/**
//	 * Column for LgUser foreign key
//	 */
//	@JsonIgnore
//	@ManyToOne
//	private LgUser user;
//	
//	/**
//	 * Column for LgInvite foreign key
//	 */
//	@JsonIgnore
//	@ManyToOne
//	private LgInvite invite;

	/*
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */
	
	public LgTimePeriod (){}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + durationMins;
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LgTimePeriod other = (LgTimePeriod) obj;
		if (durationMins != other.durationMins)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!df.format(startTime).equals(df.format(other.startTime)))
			return false;
		return true;
	}

	public LgTimePeriod (final LgTimePeriod other){
		this.startTime = other.startTime;
		this.durationMins = other.durationMins;
//		this.survey = other.survey;
//		this.user = other.user;
//		this.invite = other.invite;
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

//	public LgSurvey getSurvey() {
//		return this.survey;
//	}
//	
//	public LgInvite getInvite() {
//		return this.invite;
//	}
//	
//	public LgUser getUser() {
//		return this.user;
//	}
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * # foreign key setters
	 * # 
	 * --------------------------------------------------------------------------------------------
	 */
	
	public LgTimePeriod normalized(){
		LgTimePeriod result = new LgTimePeriod();
		result.startTime = this.startTime;
		result.durationMins = this.durationMins;
//		result.survey = null;
//		result.user = null;
//		result.invite = null;
		return result;
//		return this.attachPoolFor(tp); not works
	}
	
//	public LgTimePeriod setUser(final LgUser user){
//		this.user = user;
//		return this;
//	}
//	
//	public LgTimePeriod setInvite(final LgInvite invite){
//		this.invite = invite;
//		return this;
//	}
//	
//	public LgTimePeriod setSurvey(final LgSurvey survey) {
//		this.survey = survey;
//		return this;
//	}

	public LgTimePeriod updateWith(LgTimePeriod other) {
		this.startTime = other.startTime;
		this.durationMins = other.durationMins;
		return this;
	}
	
	@Override
	public String toString() {
		return String
				.format("LgTimePeriod [startTime=%s, durationMines=%s]",
						startTime, durationMins);
	}
}
