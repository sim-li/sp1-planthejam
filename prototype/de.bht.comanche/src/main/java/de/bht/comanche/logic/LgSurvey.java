package de.bht.comanche.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
	private int surveyDurationMins;

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
    @ElementCollection(targetClass=LgTimePeriod.class, fetch = FetchType.EAGER) 
    @Column(name="possibleTimePeriods")
	private Set<LgTimePeriod> possibleTimePeriods;

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
//		this.possibleTimePeriods = new HashSet<LgTimePeriod>();
		this.determinedTimePeriod = new LgTimePeriod();
		this.invites = new ArrayList<LgInvite>();
	}

	public LgInvite getInviteByParticipantName(final String name) {
		for (LgInvite invite: this.invites) {
			if (invite.getUser().getName().equals(name)) {
				return invite;
			}
		}
		return null;
		//@TODO Throw multex exception
	}
	
	/**
	 * Returns all user that were invited in
	 * a survey.
	 * 
	 * For unit testing.
	 * 
	 */
	
	/**
	 * Returns all users that were invited in a survey.
	 * 
	 * For unit testing.
	 * 
	 * @return Participants of a survey
	 */
	public List<LgUser> getParticipants() {
		List<LgUser> participants = new ArrayList<LgUser>();
		for (LgInvite invite : this.invites) {
			participants.add(invite.getUser());
		}
		return participants;
	}
	/**
	 * Adds an invite with user in participant role.
	 * (isHost = false)
	 * 
	 * For Unit testing
	 * 
	 * @param user
	 * @return
	 */
	public LgSurvey addParticipants(final LgUser ... users) {
		for (LgUser user : users) {
			this.addInvite(new LgInvite().setUser(user).setHost(false));
		}
		return this;
	}
	
	/**
	 * Removes participants from Survey
	 * 
	 * For Unit testing
	 * 
	 * @param Users that should be removed from survey.
	 * @return This survey, updated.
	 */
	public LgSurvey removeParticipants(LgUser ... users) {
		for (LgUser user : users) {
			final LgInvite invite = findInviteForParticipant(user);
			if (this.invites.contains(invite)) {
				this.removeInvite(invite);
			}
		}
		return this;
	}
	
	/**
	 * Returns invite by user name.
	 * 
	 * @param user User object to be used as search criteria.
	 * @return Invite or empty invite when not found
	 */
	public LgInvite findInviteForParticipant(LgUser user) {
		for (LgInvite invite : this.invites) {
			if (invite.getUser().equals(user)) {
				return invite;
			}
		}
		return new LgInvite();
	}
	
	/**
	 * Adds an invite with user in host role.
	 * (isHost = true)
	 * 
	 * Originally written for tests.
	 * @param user
	 * @return
	 */
	public LgSurvey addHost(final LgUser user) {
		this.addInvite(new LgInvite().setUser(user).setHost(true));
		return this;
	}
	
	public LgUser getHost() {
		for (final LgInvite invite : this.invites) {
			if (invite.getIsHost() == true) {
				return invite.getUser();
			}
		}
		return null;
	}
	
	/**
	 * Updates a Survey with values form another one
	 * @param other Other survey
	 */
	public LgSurvey updateWith(final LgSurvey other) {
		System.out.println(" ----------------------- update with: num of possTPs " + other.possibleTimePeriods.size());
		this.name = other.name;
		this.description = other.description;
		this.type = other.type;
		this.surveyDurationMins = other.surveyDurationMins;
		this.deadline = other.deadline;
		this.frequencyDist = other.frequencyDist;
		this.frequencyUnit = other.frequencyUnit;
		this.possibleTimePeriods = other.possibleTimePeriods;
		this.determinedTimePeriod = other.determinedTimePeriod;
		this.success = other.success;
		this.algoChecked = other.algoChecked;
		for (LgInvite invite : other.invites) {
			invite.setSurvey(other);
		}
		this.invites = other.invites;
		return this;
		// Check this, implement Equals method for other classes
//		updateList(this.invites, other.invites);
	}

	/**
	 * Elements not present in the freshList are removed from the one persisted on server.
	 * All elements that already exist on server are removed from freshList,
	 * to save the rest one by one and add to pers. list.
	 *
	 * @param persistedList
	 * @param freshList
	 */
	// TODO implement
//	public LgSurvey updateList(LgSurvey persisted) {
//		List<LgSurvey> listFromDB = new ArrayList<LgSurvey>();
//		listFromDB.add(persisted);
//		
//		List<LgSurvey> listfresh = new ArrayList<LgSurvey>();
//		listfresh.add(this);
//		
//		System.out.println("listfresh=======" + listfresh.size());
//		System.out.println("listFromDB=======" + listFromDB.size());
//		
//		listFromDB.retainAll(listfresh); // PL & its objs must be tracked for
//		listfresh.removeAll(listFromDB); // ELs already saved
//		
//		for (LgSurvey el : listfresh) {
//		el.save(); // Fresh list are never tracked
//		}
//		listFromDB.addAll(listfresh); 
//		
//		return listFromDB.get(0);
//	}
	
	//-- METHODS FOR SURVEY EVALUATION ----------------------------------------

	private Date now() {
		return new Date();
	}

	@JsonIgnore
	public boolean isReadyForEvaluation() {
		return this.deadline != null &&
                this.deadline.before(now()) &&
                !this.algoChecked &&
                this.success == LgStatus.UNDECIDED;
	}

	/**
	 * A very simple algorithm that just filters out the intersection of the
	 * possibleTimePeriods of the survey and all availableTimePeriods of the
	 * invites. The determinedTimePeriod is simply the first LgTimePeriod in
	 * the filtered list or null if the list was empty.
	 */
	public void determine() {
        System.out.println("+#-+#-+#-+#-+#-+#-+#-+#-+#-+#-");
        System.out.println("determination started for survey " + this.oid + " " + this.name);
        System.out.println("+#-+#-+#-+#-+#-+#-+#-+#-+#-+#-");

		final List<LgTimePeriod> filtered = new ArrayList<LgTimePeriod>(this.possibleTimePeriods);
		for (final LgInvite invite : this.invites) {
			/* TODO
			 * - LgTimePeriod needs hashCode and equals
			 * - implementation missing of: Invite.getAvailableTimePeriods()
			 */
			// then comment back in: -->
//			filtered.retainAll(invite.getAvailableTimePeriods());
			// <--
		}

		if (filtered.size() > 0) {
			this.determinedTimePeriod = filtered.get(0);
		} else {

            /****** HACK ******/
            this.determinedTimePeriod = new LgTimePeriod().setStartTime(new Date()).setDurationMins(12345);
            // this.determinedTimePeriod = null	;
            /****** HACK ******/
        }
		this.algoChecked = true;
	}

	//-------------------------------------------------------------------------

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
		result = prime * result + surveyDurationMins;
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
		if (this.oid != other.oid)
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
		if (surveyDurationMins != other.surveyDurationMins)
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

	public int getSurveyDurationMins() {
		return this.surveyDurationMins;
	}

	public LgSurvey setSurveyDurationMins(final int surveyDurationMins) {
		this.surveyDurationMins = surveyDurationMins;
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

	public Set<LgTimePeriod> getPossibleTimePeriods(){
		return this.possibleTimePeriods;
	}

	public LgSurvey setPossibleTimePeriods(final Set<LgTimePeriod> possibleTimePeriods){
		this.possibleTimePeriods = possibleTimePeriods;
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

	/**
	 * Removed invites property - leads to stack overflow error.
	 */
	@Override
	public String toString() {
		return String
				.format("LgSurvey [name=%s, description=%s, type=%s, durationMins=%s, deadline=%s, frequencyDist=%s, frequencyUnit=%s, possibleTimePeriods=%s, determinedTimePeriod=%s, success=%s, algoChecked=%s, oid=%s, pool=%s]",
						name, description, type, surveyDurationMins, deadline,
						frequencyDist, frequencyUnit, possibleTimePeriods,
						determinedTimePeriod, success, algoChecked, oid,
						pool);
	}

	public LgSurvey removeInvite(LgInvite invite) {
		invites.remove(invite);
		return this;
	}
	
	public LgSurvey addInvite(LgInvite invite) {
		invites.add(invite);
		return this;
	}

	public boolean getAlgoChecked() {
		return this.algoChecked;
	}
}
