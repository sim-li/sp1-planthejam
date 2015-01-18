package de.bht.comanche.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.bht.comanche.persistence.DaObject;

/**
 * Table contains Survey data
 * A survey connects the invite to the time period.
 * 
 * @author Duc Tung Tong
 */
@Entity
@Table(name = "survey")
public class LgSurvey extends DaObject {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Survey's name
	 */
	private String name;
	
	/**
	 * Survey's description
	 */
	private String description;
	
	/**
	 * The survey type, which can either be ONE_TIME or RECURRING.
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private LgSurveyType type;
	
	/**
	 * The intended duration of the survey in minutes.
	 */
	private int durationMins;
	
	/**
	 * The deadline of the survey.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date deadline;
	
	/**
	 * The distance of the frequency of the survey, e.g. the number of time units in between two runs of the survey.
	 */
	private int frequencyDist;

	/**
	 * The time unit for the frequency of the survey, which may be DAY, WEEK or MONTH.
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private LgTimeUnit frequencyUnit;
	
	/**
	 * Representation of foreign key in LgTimePeriod entity. Provide all possible time periods for this survey.
	 */
	//if tests are not working, set to lazy 
//	@JsonIgnore
	@OneToMany(mappedBy="survey", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private List<LgTimePeriod> possibleTimePeriods;
	
	/**
	 * The time period that will be determined after the deadline is reached.
	 * At this point the date determination algorithm will check for such a time period, which then has to be confirmed by the host of the survey.
	 */
	private LgTimePeriod determinedTimePeriod; // TODO check if it works ok with the database
	
	/**
	 * A tribool flag indicating whether the host has marked the survey as successful or not or if it is still undecided.
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private LgStatus success;
	
	/**
	 * A flag indicating whether the survey was already checked by the date determination algorithm.
	 */
	@Column
	private boolean algoChecked;

	/**
	 * Invites which are sent to participants of the survey
	 */
	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // SEB: changed to fetch eager to get ReInviteService.getSurveyInvites working
	private List<LgInvite> invites;
	
	/**
	 * Constructor 
	 */
	public LgSurvey() {
		this.possibleTimePeriods = new ArrayList<LgTimePeriod>();
		this.invites = new ArrayList<LgInvite>();
	}
	
	public LgInvite getInviteByParticipantName(final String name) {
		for (LgInvite invite: this.invites) {
			if (invite.getUser().getName() == name) {
				return invite;
			}
		}
		return null; 
		//@TODO Throw multex exception
	}
	
	public void inviteOtherUser(final LgUser user) {
		final LgInvite invite = new LgInvite();
		invite.setHost(false);
		invite.setIgnored(LgStatus.UNDECIDED);
		invite.setSurvey(this);
		invite.setUser(user);
		saveUnattached(invite);
	}
	
	/**
	 * Updates a Survey with values form another one and saves
	 * @param other Other survey
	 */
	public void updateWith(final LgSurvey other) {
		this.name = other.name;
		this.description = other.description;
		this.type = other.type;
		this.durationMins = other.durationMins;
		this.deadline = other.deadline;
		this.frequencyDist = other.frequencyDist;
		this.frequencyUnit = other.frequencyUnit;
		this.possibleTimePeriods = other.possibleTimePeriods;
		this.determinedTimePeriod = other.determinedTimePeriod;
		this.success = other.success;
		this.algoChecked = other.algoChecked;
		this.invites = other.invites;
	}
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (algoChecked ? 1231 : 1237);
		result = prime * result
				+ ((deadline == null) ? 0 : deadline.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((determinedTimePeriod == null) ? 0 : determinedTimePeriod
						.hashCode());
		result = prime * result + durationMins;
		result = prime * result + frequencyDist;
		result = prime * result
				+ ((frequencyUnit == null) ? 0 : frequencyUnit.hashCode());
		result = prime * result + ((invites == null) ? 0 : invites.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((possibleTimePeriods == null) ? 0 : possibleTimePeriods
						.hashCode());
		result = prime * result + ((success == null) ? 0 : success.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		LgSurvey other = (LgSurvey) obj;
		// This is the trick!
		if (this.oid != 0L && other.oid != 0L && this.oid != other.oid)
			return false;
		if (algoChecked != other.algoChecked)
			return false;
		if (deadline == null) {
			if (other.deadline != null)
				return false;
		} else if (!deadline.equals(other.deadline))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (determinedTimePeriod == null) {
			if (other.determinedTimePeriod != null)
				return false;
		} else if (!determinedTimePeriod.equals(other.determinedTimePeriod))
			return false;
		if (durationMins != other.durationMins)
			return false;
		if (frequencyDist != other.frequencyDist)
			return false;
		if (frequencyUnit != other.frequencyUnit)
			return false;
		if (invites == null) {
			if (other.invites != null)
				return false;
		} else if (!invites.equals(other.invites))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (possibleTimePeriods == null) {
			if (other.possibleTimePeriods != null)
				return false;
		} else if (!possibleTimePeriods.equals(other.possibleTimePeriods))
			return false;
		if (success != other.success)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public String getName() {
		return this.name;
	}

	public LgSurvey setName(final String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public LgSurvey setDescription(final String description) {
		this.description = description;
		return this;
	}
	
	public LgSurveyType getType() {
		return this.type;
	}
	
	public LgSurvey setType(final LgSurveyType type) {
		this.type = type;
		return this;
	}
	
	public int getDurationMins() {
		return this.durationMins;
	}
	
	public LgSurvey setDurationMins(final int durationMins) {
		this.durationMins = durationMins;
		return this;
	}
	
	public Date getDeadline() {
		return this.deadline;
	}
	
	public LgSurvey setDeadline(final Date deadline) {
		this.deadline = deadline;
		return this;
	}

	public int getFrequencyDist() {
		return this.frequencyDist;
	}

	public LgSurvey setFrequencyDist(final int frequencyDist) {
		this.frequencyDist = frequencyDist;
		return this;
	}

	public LgTimeUnit getFrequencyUnit() {
		return this.frequencyUnit;
	}

	public LgSurvey setFrequencyUnit(final LgTimeUnit frequencyUnit) {
		this.frequencyUnit = frequencyUnit;
		return this;
	}
	
	public List<LgTimePeriod> getPossibleTimePeriods(){
		return this.possibleTimePeriods;
	}
	
	public LgSurvey setPossibleTimePeriods(final List<LgTimePeriod> period){
		this.possibleTimePeriods = period;
		return this;
	}

	// # This would become unnecessary if reference to INV / SURV / USER removed.
	// ( see comment in TP )
	public LgSurvey normalizePossibleTPs() {
		List<LgTimePeriod> result = new ArrayList<LgTimePeriod>(this.possibleTimePeriods);
		for(LgTimePeriod period: result){
			period.setInvite(null);
			period.setSurvey(null);
			period.setUser(null);
			result.add(period);
		}
		return setPossibleTimePeriods(result);
	}
	
	public List<LgTimePeriod> normalizeTimePeriod(List<LgTimePeriod> period) {
		List<LgTimePeriod> result = new ArrayList<LgTimePeriod>();
		// Rename necessary, totally confusing [time / period]
		for(LgTimePeriod time: period){
			time.setInvite(null);
			time.setSurvey(null);
			time.setUser(null);
			result.add(time);
		}
		return result;
	}
	
	public LgSurvey flagPossibleTimePeriod() {
		for(LgTimePeriod period : this.possibleTimePeriods) {
			period.setSurvey(this);
		}
		return this;
	}
	
	public LgTimePeriod getDeterminedTimePeriod() {
		return this.determinedTimePeriod;
	}
	
	public LgSurvey setDeterminedTimePeriod(final LgTimePeriod determinedTimePeriod) {
		this.determinedTimePeriod = determinedTimePeriod;
		return this;
	}
	
	public LgStatus getSuccess() {
		return this.success;
	}
	
	public LgSurvey setSuccess(final LgStatus success) {
		this.success = success;
		return this;
	}
	
	public boolean isAlgoChecked() {
		return this.algoChecked;
	}
	
	public LgSurvey setAlgoChecked(final boolean algoChecked) {
		this.algoChecked = algoChecked;
		return this;
	}

	@JsonIgnore
	public List<LgInvite> getInvites() {
		return this.invites;
	}

	@JsonProperty
	public LgSurvey setInvites(final List<LgInvite> invites) {
		this.invites = invites;
		return this;
	}

	@Override
	public String toString() {
		return String
				.format("LgSurvey [name=%s, description=%s, type=%s, durationMins=%s, deadline=%s, frequencyDist=%s, frequencyUnit=%s, possibleTimePeriods=%s, determinedTimePeriod=%s, success=%s, algoChecked=%s, invites=%s, oid=%s, pool=%s]",
						name, description, type, durationMins, deadline,
						frequencyDist, frequencyUnit, possibleTimePeriods,
						determinedTimePeriod, success, algoChecked, invites, oid,
						pool);
	}

	public void addInvite(LgInvite invite) {
		invites.add(invite);
	}
}
