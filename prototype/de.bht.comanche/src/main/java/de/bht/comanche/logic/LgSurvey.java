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
 * A survey connects the invite to the time period.
 * 
 * @author Duc Tung Tong
 */
@Entity
@Table(name = "survey")
public class LgSurvey extends DaObject {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private int frequencyDist;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deadline;

	@Column
	@Enumerated(EnumType.STRING)
	private LgSurveyType type;

	@Column
	@Enumerated(EnumType.STRING)
	private LgTimeUnit frequencyTimeUnit;

	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<LgInvite> invites;

	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<LgTimePeriod> possibleTimePeriods;
	
	public LgSurvey() {
		this.invites = new ArrayList<LgInvite>();
		this.possibleTimePeriods = new ArrayList<LgTimePeriod>();
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

	@Override
	public <E extends DaObject> E save() {
		throw new UnsupportedOperationException("LgSurvey.save() is not implemented");
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
