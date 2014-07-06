package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Survey")
public class LgSurvey extends DbObject {

	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private Date deadline;
	private int frequencyDist;
	private Date resultingStartTime;
	private int resultingDurationInMinutes;
	
	@Column
	@Enumerated(EnumType.STRING)
	private SurveyType type;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TimeUnit frequencyTimeUnit;
	
	@ManyToOne
	private LgInvite invite_surveys;
	
	@ManyToOne
	private LgTimePeriod timePeriod_surveys;

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

	public SurveyType getType() {
		return type;
	}

	public void setType(SurveyType type) {
		this.type = type;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getFrequencyDist() {
		return frequencyDist;
	}

	public void setFrequencyDist(int frequencyDist) {
		this.frequencyDist = frequencyDist;
	}

	public TimeUnit getFrequencyTimeUnit() {
		return frequencyTimeUnit;
	}

	public void setFrequencyTimeUnit(TimeUnit frequencyTimeUnit) {
		this.frequencyTimeUnit = frequencyTimeUnit;
	}

	public Date getResultingStartTime() {
		return resultingStartTime;
	}

	public void setResultingStartTime(Date resultingStartTime) {
		this.resultingStartTime = resultingStartTime;
	}

	public int getResultingDurationInMinutes() {
		return resultingDurationInMinutes;
	}

	public void setResultingDurationInMinutes(int resultingDurationInMinutes) {
		this.resultingDurationInMinutes = resultingDurationInMinutes;
	}

	public LgInvite getInvite_surveys() {
		return invite_surveys;
	}

	public void setInvite_surveys(LgInvite invite_surveys) {
		this.invite_surveys = invite_surveys;
	}

}
