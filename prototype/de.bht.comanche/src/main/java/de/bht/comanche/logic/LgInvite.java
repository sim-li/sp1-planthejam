package de.bht.comanche.logic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Invite")
public class LgInvite extends DbObject{

	private static final long serialVersionUID = 1L;
	private boolean isHost;
	private boolean isIgnored;
	
	@OneToMany(mappedBy="invite_users")
	private List<LgUser> users;
	
	@OneToMany(mappedBy="invite_surveys")
	private List<LgSurvey> surveys;
	
	@ManyToOne
	private LgTimePeriod timePeriod_invites;

	public boolean isHost() {
		return isHost;
	}

	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}

	public boolean isIgnored() {
		return isIgnored;
	}

	public void setIgnored(boolean isIgnored) {
		this.isIgnored = isIgnored;
	}

	public List<LgUser> getUsers() {
		return users;
	}

	public void setUsers(List<LgUser> users) {
		this.users = users;
	}

	public List<LgSurvey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<LgSurvey> surveys) {
		this.surveys = surveys;
	}	
}
