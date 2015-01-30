package de.bht.comanche.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
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
 * @author Simon Lischka
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
	 * Note: Marked for delete! User has to think of considering durations
	 * in front end.
	 * 
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
	private LgTimePeriod determinedTimePeriod; 

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
	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<LgInvite> invites;

	/**
	 * Constructor
	 */
	public LgSurvey() {
		this.possibleTimePeriods = new HashSet<LgTimePeriod>();
		this.determinedTimePeriod = LgTimePeriod.createEmptyTimePeriod();
		this.invites = new ArrayList<LgInvite>();
		this.success = LgStatus.UNDECIDED;
	}

	
	/**
	 * A very simple algorithm that just filters out the intersection of the
	 * possibleTimePeriods of the survey and all availableTimePeriods of the
	 * invites. The determinedTimePeriod is simply the first LgTimePeriod in
	 * the filtered list or null if the list was empty.
	 */
	public void evaluate() {
		this.determinedTimePeriod = LgTimePeriod.createEmptyTimePeriod();
		boolean someOneAccepted = false;
		final Set<LgTimePeriod> matchesFromUsers = new HashSet<LgTimePeriod>();
		matchesFromUsers.addAll(this.getPossibleTimePeriods());
		for (LgInvite inv : this.invites) {
			if (inv.getIsHost() == true ||inv.getConcreteAvailability().isEmpty()) {
				continue;
			}
			final Set<LgTimePeriod> cAvails = inv.getConcreteAvailability();
			if (inv.getIsIgnored() == LgStatus.NO) {
				matchesFromUsers.retainAll(cAvails);
				someOneAccepted = true;
			}
		}
		this.algoChecked = true;
		if (!someOneAccepted) {
			return;
		}
		if (matchesFromUsers.iterator().hasNext()) {
			this.determinedTimePeriod = matchesFromUsers.iterator().next();
		}
	}
	

	/** 
	 * Current system time
	 * @return a date
	 */
	private Date now() {
		return new Date();
	}

	/**
	 * Determines whether a surveys deadline is over
	 * and it is still open for evaluation.
	 * 
	 * @return true if survey should be evaluated
	 */
	@JsonIgnore
	public boolean shouldBeEvaluated() {
		return  this.deadline != null && 
				this.deadline.before(now()) &&
                !this.algoChecked &&
                this.success == LgStatus.UNDECIDED;
	}
	
	/**
	 * Returns all users that were invited in a survey.
	 * 
	 * For unit testing.
	 * 
	 * @return Participants of a survey
	 */
	public List<LgUser> getParticipants() {
		final List<LgUser> participants = new ArrayList<LgUser>();
		for (LgInvite invite : this.invites) {
			if (!invite.getIsHost()) {
				participants.add(invite.getUser());
			}
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
	 * Added for testing.
	 */
	public void detach() {
		this.pool = null;
	}
	
	public LgInvite getInviteAt(int index) {
        return this.invites.get(index);
	}

	public void setInvite(int index, LgInvite invite) {
	        this.invites.set(index, invite);
	}

	/**
	 * Returns invite of participant found with given
	 * name.
	 * 
	 * Only used for testing.
	 * 
	 * @param name Username
	 * @return User object linked to survey
	 */
	public LgInvite getInviteByUserName(String name) {
		for (LgInvite invite : this.invites) {
			final LgUser user = invite.getUser();
			if (user.getName().equals(name)) {
				return invite;
			}
		}
		return null;
	}
	/**
	 * Updates a Survey with values form another one
	 * 
	 * @param other Other survey
	 */
	public LgSurvey updateWith(final LgSurvey other) {
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
	}


	/**
	 * Returns all user that were invited in
	 * a survey.
	 * 
	 * For unit testing.
	 * 
	 */
	public LgInvite getInviteByParticipantName(final String name) {
		for (LgInvite invite: this.invites) {
			if (invite.getUser().getName().equals(name)) {
				return invite;
			}
		}
		return null;
	}
	
	
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
}
