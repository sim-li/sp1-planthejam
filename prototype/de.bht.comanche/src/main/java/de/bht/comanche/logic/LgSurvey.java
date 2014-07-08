package webTest.entity;

import java.util.Date;
import java.util.List;

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
	
	@Column(length=125)
	private String name;
	
	@Column(length=255)
	private String description;
	
	private int frequencyDist;
	private Date deadline;

	@Column
	@Enumerated(EnumType.STRING)
	private SurveyType type;

	@Column
	@Enumerated(EnumType.STRING)
	private TimeUnit frequencyTimeUnit;

	@OneToMany(mappedBy = "usi.survey")
	private List<LgInvite> invites;

	@OneToMany(mappedBy="survey")
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
