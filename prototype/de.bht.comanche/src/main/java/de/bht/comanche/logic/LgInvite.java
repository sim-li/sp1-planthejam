package de.bht.comanche.logic;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.bht.comanche.persistence.DaObject;

/**
 * Table contains Invite data
 * 
 * @author Duc Tung Tong
 */
@Entity
@Table(name = "invite")
public class LgInvite extends DaObject{

	private static final long serialVersionUID = 1L;

	/**
	 *  is true if user is host from survey 
	 */
	@Column
	private boolean isHost;
	
	/**
	 * A tribool flag indicating whether the participant has marked the invite as ignored or not or if he is still undecided.
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private LgStatus isIgnored;

	/**
	 * The user who receives this invite.
	 */
	@NotNull
	@ManyToOne
	private LgUser user;

	/**
	 * The survey to which this invite belongs.
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private LgSurvey survey;
	
	/**
	 * Representation of a foreign key in a LgTimePeriod entity. Provide a list of available periods. 
	 */
	
	@ElementCollection(targetClass=LgTimePeriod.class, fetch = FetchType.EAGER) 
	@Column(name="timeperiods") 
	private List<LgTimePeriod> timePeriods;
	
	
	public LgInvite() {
//		this.timePeriods = new ArrayList<LgTimePeriod>();
	}
	
	public LgInvite(LgInvite other) {
		this.oid = other.oid;
		this.isHost = other.isHost;
		this.isIgnored = other.isIgnored;
		this.user = other.user;
		this.survey = other.survey;
		this.timePeriods = new ArrayList<LgTimePeriod>();
		for (final LgTimePeriod timePeriod : other.timePeriods) {
			this.timePeriods.add(timePeriod);
		}
	}
	
	/*
	 * -------------------------------------
	 * -------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	public boolean isHost() {
		return this.isHost;
	}

	public LgStatus getIgnored() {
		return this.isIgnored;
	}

	public LgInvite setIgnored(final LgStatus isIgnored) {
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
	/**
	 * Returns PossibleTimePeriod with nulled db-flags
	 * @return
	 */
	@JsonIgnore
	public LgSurvey getSurvey() {
		return this.survey;
	}

	public LgInvite setSurvey(final LgSurvey survey) {
		this.survey = survey;
		return this;
	}
	
	/**
	 * Sets specified LgInvite for LgTimePeriod.
	 * @param invite The LgInvite to set.
	 * @return Returns The LgInvite.
	 */ 

	@Override
	public String toString() {
		return String
				.format("LgInvite [isHost=%s, isIgnored=%s, user=%s, survey=%s, oid=%s, pool=%s]",
						isHost, isIgnored, user, survey, oid, pool);
	}
}
