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
	 *  this method updates old Survey with "other" Survey
	 * @param other The other Survey, which is needed to update old Survey
	 * @deprecated Unefficient pattern
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
	
//	getter
	public List<LgTimePeriod> getPossibleTimePeriods(){
		return this.possibleTimePeriods;
	}
	
//	setter
	public LgSurvey setPossibleTimePeriods(final List<LgTimePeriod> period){
		this.possibleTimePeriods = period;
		return this;
	}

	public LgSurvey withNormalizedTP() {
		List<LgTimePeriod> result = new ArrayList<LgTimePeriod>(this.possibleTimePeriods);
		System.out.println("withNormalizedTP==================");
		for(LgTimePeriod period: result){
			period.setInvite(null);
			period.setSurvey(null);
			period.setUser(null);
			result.add(period);
			System.out.println("===" + result);
//			this.attachPoolFor(period); // not working
//			this.attachPoolFor(period); //not working
//			if(period.getSurvey().getOid() == this.getOid()){
//			result.add(period.normalized());//not working - array is empty
//			}
		}
		return setPossibleTimePeriods(result);
		//not set to setPossibleTimePeriods, attach result
//		return this.setPossibleTimePeriods.result);
	}
	
	public List<LgTimePeriod> normilaze(List<LgTimePeriod> period) {
		List<LgTimePeriod> result = new ArrayList<LgTimePeriod>();
		System.out.println("withNormalizedTP==================");
		for(LgTimePeriod time: period){
			time.setInvite(null);
			time.setSurvey(null);
			time.setUser(null);
			result.add(time);
			System.out.println("===" + result);
//			this.attachPoolFor(period); // not working
//			this.attachPoolFor(period); //not working
//			if(period.getSurvey().getOid() == this.getOid()){
//			result.add(period.normalized());//not working - array is empty
//			}
		}
		return result;
		//not set to setPossibleTimePeriods, attach result
//		return this.setPossibleTimePeriods.result);
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
