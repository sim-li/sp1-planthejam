package de.bht.comanche.logic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
//@Table(name = "Lg_Invite")
public class LgInvite extends DbObject{
	
	private static final long serialVersionUID = 1L;
	
	private boolean isHost;
	private boolean isIgnored;
	
	@ManyToOne
	private LgUser user;
	
	@ManyToOne
	private LgSurvey invite_survey;

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

	public LgUser getUser() {
		return user;
	}

	public void setUser(LgUser user) {
		this.user = user;
	}

	public LgSurvey getSurvey() {
		return invite_survey;
	}

	public void setSurvey(LgSurvey survey) {
		this.invite_survey = survey;
	}	
}
