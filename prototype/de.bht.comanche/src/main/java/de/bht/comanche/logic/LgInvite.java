package de.bht.comanche.logic;

import javax.persistence.CascadeType;
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
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private LgSurvey survey;

	public LgInvite() {}
	
	public LgInvite(final long oid) {
		this.oid = oid;
	}
	
	public LgInvite save() {
		return getPool().save(this);
	}
	
	public void delete() {
		user.remove(this);
		this.getPool().delete(this); //throw exc when delete errror
	}
	
	
	public LgSurvey getSurvey(long oid) { //secure
		if (this.survey.getOid() == oid) {
			return this.survey;
		}
		return null; //throw exception (not same)
	}

	public LgSurvey get(LgSurvey survey) { //secure
		return getSurvey(survey.getOid());
	}
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	public boolean isHost() {
		return isHost;
	}


	public boolean isIgnored() {
		return isIgnored;
	}

	public LgInvite setIgnored(boolean isIgnored) {
		this.isIgnored = isIgnored;
		return this;
	}

	public LgInvite setHost(boolean isHost) {
		this.isHost = isHost;
		return this;
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
		return survey;
	}

	public LgInvite setSurvey(LgSurvey survey) {
		this.survey = survey;
		return this;
	}
	
	public LgInvite updateWith(LgInvite other) {
		this.survey = other.survey;
		this.isHost = other.isHost;
		this.isIgnored = other.isIgnored;
		this.user = other.user;
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((survey == null) ? 0 : survey.hashCode());
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
		if (survey == null) {
			if (other.survey != null)
				return false;
		} else if (!survey.equals(other.survey))
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
