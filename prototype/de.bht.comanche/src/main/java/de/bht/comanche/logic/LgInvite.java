package de.bht.comanche.logic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Lg_Invite")
public class LgInvite extends DbObject{
	
	private static final long serialVersionUID = 1L;
	
	private boolean isHost;
	private boolean isIgnored;
	
	@NotNull
	@ManyToOne
	private LgUser user;
	
	@NotNull
	@ManyToOne
	private LgSurvey invite_survey;

	public boolean isHost() {
		return isHost;
	}

	public LgInvite setHost(boolean isHost) {
		this.isHost = isHost;
		return this;
	}

	public boolean isIgnored() {
		return isIgnored;
	}

	public LgInvite setIgnored(boolean isIgnored) {
		this.isIgnored = isIgnored;
		return this;
	}

	public LgUser getUser() {
		return user;
	}

	public LgInvite setUser(LgUser user) {
		this.user = user;
		return this;
	}

	public LgSurvey getSurvey() {
		return invite_survey;
	}

	public LgInvite setSurvey(LgSurvey survey) {
		this.invite_survey = survey;
		return this;
	}	
}
