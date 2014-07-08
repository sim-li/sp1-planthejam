package webTest.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TimePeriod")
public class LgTimePeriod extends DbObject {

	private static final long serialVersionUID = 1L;
	private Date startTime;
	private int durationMinutes;

	@ManyToOne
	private LgSurvey survey;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public LgSurvey getSurvey() {
		return survey;
	}

	public void setSurvey(LgSurvey survey) {
		this.survey = survey;
	}

}
