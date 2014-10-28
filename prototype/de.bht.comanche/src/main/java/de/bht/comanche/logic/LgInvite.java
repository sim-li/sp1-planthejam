package de.bht.comanche.logic;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.bht.comanche.persistence.DaObject;

/**
 * Describes relation of host with users.
 * 
 * @author Duc Tung Tong
 */
@Entity
@Table(name = "Lg_Invite")
public class LgInvite extends DaObject{
	
	private static final long serialVersionUID = 1L;
	
	private boolean isHost;
	private boolean isIgnored;
	
	@NotNull
	@ManyToOne
	private LgUser user;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
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

	public void removeInvite() {
		user.removeInvite(this);
	}
	
	@JsonIgnore
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((invite_survey == null) ? 0 : invite_survey.hashCode());
		result = prime * result + (isHost ? 1231 : 1237);
		result = prime * result + (isIgnored ? 1231 : 1237);
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LgInvite other = (LgInvite) obj;
		if (invite_survey == null) {
			if (other.invite_survey != null)
				return false;
		} else if (!invite_survey.equals(other.invite_survey))
			return false;
		if (isHost != other.isHost)
			return false;
		if (isIgnored != other.isIgnored)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}	
	
}
