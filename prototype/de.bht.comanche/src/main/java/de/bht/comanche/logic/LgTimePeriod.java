package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TimePeriod")
public class LgTimePeriod extends DbObject {

	private static final long serialVersionUID = 1L;
	private Date startTime;
	private int durationMinutes;

	@NotNull
	@ManyToOne
	private LgSurvey survey;

	public Date getStartTime() {
		return startTime;
	}

	public LgTimePeriod setStartTime(Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public LgTimePeriod setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
		return this;
	}

	public LgSurvey getSurvey() {
		return survey;
	}

	public LgTimePeriod setSurvey(LgSurvey survey) {
		this.survey = survey;
		return this;
	}

}
