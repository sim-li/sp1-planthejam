package de.bht.comanche.logic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.bht.comanche.persistence.DaObject;

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
	
	// save() is already implemented in superclass DaObject
//	public LgInvite save() {
//		return this.pool.save(this);
//	}
	
	public void delete() {
		this.user.remove(this);
		this.pool.delete(this); //throw exc when delete errror
	}
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	public boolean isHost() {
		return this.isHost;
	}

	public boolean isIgnored() {
		return this.isIgnored;
	}

	public LgInvite setIgnored(final boolean isIgnored) {
		this.isIgnored = isIgnored;
		return this;
	}

	public LgInvite setHost(final boolean isHost) {
		this.isHost = isHost;
		return this;
	}

	@JsonIgnore
	public LgUser getUser() {
		return this.user;
	}

	public LgInvite setUser(final LgUser user) {
		this.user = user;
		return this;
	}

	public LgSurvey getSurvey() {
		return this.survey;
	}

	public LgInvite setSurvey(final LgSurvey survey) {
		this.survey = survey;
		return this;
	}

	@Override
	public String toString() {
		return String
				.format("LgInvite [isHost=%s, isIgnored=%s, user=%s, survey=%s, oid=%s, pool=%s]",
						isHost, isIgnored, user, survey, oid, pool);
	}
}
