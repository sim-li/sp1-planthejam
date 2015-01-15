package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.bht.comanche.persistence.DaObject;

/**
 * A data type for time periods.
 * 
 * It is used to describe the possible time periods of a survey or the availability of users.
 * <p>
 * Important note: This class needs to <strong>override hashCode and equals</strong>, so that collections of 
 * LgTimePeriods are comparable. In the current version timePeriods are considered equal if their startTimes 
 * are equal.
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
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	
	/**
	 * duration in minutes
	 */
	private int durationMins;

	/**
	 * Column for LgSurvey foreign key 
	 */
	@ManyToOne
	private LgSurvey survey;
	
	/**
	 * Column for LgUser foreign key
	 */
	@ManyToOne
	private LgUser user;
	
	/**
	 * Column for LgInvite foreign key
	 */
	@ManyToOne
	private LgInvite invite;

	/*
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */
	
	public LgTimePeriod (){}
	
	public LgTimePeriod (final LgTimePeriod other){
		this.oid = other.oid;
		this.startTime = other.startTime;
		this.durationMins = other.durationMins;
		this.survey = other.survey;
		this.user = other.user;
		this.invite = other.invite;
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

	public LgSurvey getSurvey() {
		return this.survey;
	}
	
	public LgInvite getInvite() {
		return this.invite;
	}
	
	public LgUser getUser() {
		return this.user;
	}
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * # foreign key setters
	 * # 
	 * --------------------------------------------------------------------------------------------
	 */
	
	public LgTimePeriod normalized(){
		LgTimePeriod tp = new LgTimePeriod();
		tp.oid = this.oid;
		tp.startTime = this.startTime;
		tp.durationMins = this.durationMins;
		return tp;
	}
	
	public LgTimePeriod setUser(final LgUser user){
		this.user = user;
		return this;
	}
	
	public LgTimePeriod setInvite(final LgInvite invite){
		this.invite = invite;
		return this;
	}
	
	public LgTimePeriod setSurvey(final LgSurvey survey) {
		this.survey = survey;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LgTimePeriod other = (LgTimePeriod) obj;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("LgTimePeriod [startTime=%s, durationMines=%s, survey=%s, oid=%s, pool=%s]",
						startTime, durationMins, survey, oid, pool);
	}
}
