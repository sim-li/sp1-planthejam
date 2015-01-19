package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	// QUESTION: Do we need to define back-refs? 
	// Can't LgUser/LgSurvey have an array list & ManyToOne
	
	/**
	 * Column for LgSurvey foreign key 
	 */
	@JsonIgnore
	@ManyToOne
	private LgSurvey survey;
	
	/**
	 * Column for LgUser foreign key
	 */
	@JsonIgnore
	@ManyToOne
	private LgUser user;
	
	/**
	 * Column for LgInvite foreign key
	 */
	@JsonIgnore
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
		LgTimePeriod result = new LgTimePeriod();
		result.oid = this.oid;
		result.startTime = this.startTime;
		result.durationMins = this.durationMins;
		result.survey = null;
		result.user = null;
		result.invite = null;
		return result;
//		return this.attachPoolFor(tp); not works
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
	public String toString() {
		return String
				.format("LgTimePeriod [startTime=%s, durationMines=%s, survey=%s, oid=%s, pool=%s]",
						startTime, durationMins, survey, oid, pool);
	}
}
