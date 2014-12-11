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
	 * Survey's frequency distance
	 */
	private int frequencyDist;

	/**
	 * Survey's deadline. Type: Date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date deadline;

	/**
	 * Survey's type
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private LgSurveyType type;

	/**
	 * Frequency Time Unit
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private LgTimeUnit frequencyTimeUnit;

	/**
	 * Invites which are sent to members of Survey
	 */
	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<LgInvite> invites;

	/**
	 * Representation of foreign key in LgTimePeriod entity. Provide all possible time periods for this survey.
	 */
	@JsonIgnore
	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LgTimePeriod> possibleTimePeriods = new ArrayList<LgTimePeriod>();
	
	/**
	 * Constructor 
	 */
	public LgSurvey() {
		this.invites = new ArrayList<LgInvite>();
	}

	/**
	 *  this method updates old Survey with "other" Survey
	 * @param other The other Survey, which is needed to update old Survey
	 */
	public void updateWith(final LgSurvey other) {
		this.name = other.name;
		this.description = other.description;
		this.frequencyDist = other.frequencyDist;
		this.deadline = other.deadline;
		this.type = other.type;
		this.frequencyTimeUnit = other.frequencyTimeUnit;
		this.invites = other.invites;
		this.possibleTimePeriods = other.possibleTimePeriods;
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

	public int getFrequencyDist() {
		return this.frequencyDist;
	}

	public LgSurvey setFrequencyDist(final int frequencyDist) {
		this.frequencyDist = frequencyDist;
		return this;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public LgSurvey setDeadline(final Date deadline) {
		this.deadline = deadline;
		return this;
	}

	public LgSurveyType getType() {
		return this.type;
	}

	public LgSurvey setType(final LgSurveyType type) {
		this.type = type;
		return this;
	}

	public LgTimeUnit getFrequencyTimeUnit() {
		return this.frequencyTimeUnit;
	}

	public LgSurvey setFrequencyTimeUnit(final LgTimeUnit frequencyTimeUnit) {
		this.frequencyTimeUnit = frequencyTimeUnit;
		return this;
	}

	@JsonIgnore
	public List<LgInvite> getInvites() {
		return this.invites;
	}

	public LgSurvey setInvites(final List<LgInvite> invites) {
		this.invites = invites;
		return this;
	}

	public List<LgTimePeriod> getPossibleTimePeriods() {
		return this.possibleTimePeriods;
	}

	public LgSurvey setPossibleTimePeriods(final List<LgTimePeriod> possibleTimePeriods) {
		this.possibleTimePeriods = possibleTimePeriods;
		return this;
	}

	@Override
	public String toString() {
		return String
				.format("LgSurvey [name=%s, description=%s, frequencyDist=%s, deadline=%s, type=%s, frequencyTimeUnit=%s, invites=%s, possibleTimePeriods=%s, oid=%s, pool=%s]",
						name, description, frequencyDist, deadline, type,
						frequencyTimeUnit, invites, possibleTimePeriods, oid,
						pool);
	}
}
