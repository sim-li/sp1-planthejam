package de.bht.comanche.logic;

import java.util.Date;
import java.util.LinkedList;
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

	//Is survey from invite class add comment
	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<LgInvite> invites;

	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<LgTimePeriod> possibleTimePeriods;

	/**
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	public String getName() {
		return name;
	}

	public LgSurvey setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public LgSurvey setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getFrequencyDist() {
		return frequencyDist;
	}

	public LgSurvey setFrequencyDist(int frequencyDist) {
		this.frequencyDist = frequencyDist;
		return this;
	}

	public Date getDeadline() {
		return deadline;
	}

	public LgSurvey setDeadline(Date deadline) {
		this.deadline = deadline;
		return this;
	}

	public LgSurveyType getType() {
		return type;
	}

	public LgSurvey setType(LgSurveyType type) {
		this.type = type;
		return this;
	}

	public LgTimeUnit getFrequencyTimeUnit() {
		return frequencyTimeUnit;
	}

	public LgSurvey setFrequencyTimeUnit(LgTimeUnit frequencyTimeUnit) {
		this.frequencyTimeUnit = frequencyTimeUnit;
		return this;
	}

	@JsonIgnore
	public List<LgInvite> getInvites() {
		return invites;
	}

	public LgSurvey setInvites(List<LgInvite> invites) {
		this.invites = invites;
		return this;
	}

	public List<LgTimePeriod> getPossibleTimePeriods() {
		return possibleTimePeriods;
	}

	public LgSurvey setPossibleTimePeriods(List<LgTimePeriod> possibleTimePeriods) {
		this.possibleTimePeriods = possibleTimePeriods;
		return this;
	}

	public void updateWith(LgSurvey other) {
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
		// TODO Auto-generated method stub
		return null;
	}
}
