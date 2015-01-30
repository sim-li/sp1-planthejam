package de.bht.comanche.logic;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.bht.comanche.persistence.DaObject;

/**
 * Table contains Invite data
 * 
 * @author Duc Tung Tong
 * @author Simon Lischka
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
	private LgStatus isIgnored = LgStatus.UNDECIDED;

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
	private Set<LgTimePeriod> concreteAvailability;
	
	public LgInvite() {}
	
	public LgInvite(LgInvite other) {
		this.oid = other.oid;
		this.isHost = other.isHost;
		this.isIgnored = other.isIgnored;
		this.user = other.user;
		this.survey = other.survey;
		this.concreteAvailability = new HashSet<LgTimePeriod>();
		for (final LgTimePeriod timePeriod : other.concreteAvailability) {
			this.concreteAvailability.add(timePeriod);
		}
	}
	
	public Set<LgTimePeriod> getConcreteAvailability(){
		return this.concreteAvailability;
	}
	
	public LgInvite setConcreteAvailability(final Set<LgTimePeriod> concreteAvailability){
		this.concreteAvailability = concreteAvailability;
		return this;
	}

	public boolean getIsHost() {
		return this.isHost;
	}
	
	public LgStatus getIsIgnored() {
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
	 * Persisted invite without user field
	 */
	@SuppressWarnings("serial")
	public static final class EmptyUserInInviteFailure extends multex.Failure {}
	
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
	
	@Override
	public void delete() {
		this.user = null;
		super.delete();
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
