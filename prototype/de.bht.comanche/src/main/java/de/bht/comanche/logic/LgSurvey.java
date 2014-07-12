package de.bht.comanche.logic;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "survey")
public class LgSurvey extends DbObject {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private int frequencyDist;
	private Date deadline;

	@Column
	@Enumerated(EnumType.STRING)
	private SurveyType type;

	@Column
	@Enumerated(EnumType.STRING)
	private TimeUnit frequencyTimeUnit;

	@OneToMany(mappedBy="invite_survey", cascade = CascadeType.ALL)
	private List<LgInvite> invites;
	
	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL)
	private List<LgTimePeriod> possibleTimePeriods;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFrequencyDist() {
		return frequencyDist;
	}

	public void setFrequencyDist(int frequencyDist) {
		this.frequencyDist = frequencyDist;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public SurveyType getType() {
		return type;
	}

	public void setType(SurveyType type) {
		this.type = type;
	}

	public TimeUnit getFrequencyTimeUnit() {
		return frequencyTimeUnit;
	}

	public void setFrequencyTimeUnit(TimeUnit frequencyTimeUnit) {
		this.frequencyTimeUnit = frequencyTimeUnit;
	}

	public List<LgInvite> getInvites() {
		return invites;
	}

	public void setInvites(List<LgInvite> invites) {
		this.invites = invites;
	}

	public List<LgTimePeriod> getPossibleTimePeriods() {
		return possibleTimePeriods;
	}

	public void setPossibleTimePeriods(List<LgTimePeriod> possibleTimePeriods) {
		this.possibleTimePeriods = possibleTimePeriods;
	}
}
