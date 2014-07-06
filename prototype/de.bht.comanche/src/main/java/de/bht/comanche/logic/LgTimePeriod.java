package de.bht.comanche.logic;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TimePeriod")
public class LgTimePeriod extends DbObject{

	private static final long serialVersionUID = 1L;
	private Date startTime;
	private int durationMinutes;
	
	@OneToMany(mappedBy="timePeriod_users")
	private List<LgUser> users;
	
	@OneToMany(mappedBy="timePeriod_invites")
	private List<LgInvite> invites;
	
	@OneToMany(mappedBy="timePeriod_surveys")
	private List<LgSurvey> surveys;

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

	public List<LgUser> getUsers() {
		return users;
	}

	public void setUsers(List<LgUser> users) {
		this.users = users;
	}

	public List<LgInvite> getInvites() {
		return invites;
	}

	public void setInvites(List<LgInvite> invites) {
		this.invites = invites;
	}

	public List<LgSurvey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<LgSurvey> surveys) {
		this.surveys = surveys;
	}
}
