package de.bht.comanche.logic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@Table(name = "Lg_Invite")
public class LgInvite extends DaObject{

	private static final long serialVersionUID = 1L;

	/**
	 *  is true if user is host from survey 
	 */
	private boolean isHost;
	
	/**
	 *  is true if user ignored the invite
	 */
	private boolean isIgnored;

	/**
	 *  user who receives  invite
	 */
	@NotNull
	@ManyToOne
	private LgUser user;

	/**
	 *  Survey 
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private LgSurvey survey;
	
	/**
	 * Representation of a foreign key in a LgTimePeriod entity. Provide a list of available periods. 
	 */
//	@OneToMany(mappedBy="survey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private List<LgTimePeriod> timePeriods;
//	
	
	public LgInvite() {
//		timePeriods = new ArrayList<LgTimePeriod>();
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
	
	/**
	 * Sets specified LgInvite for LgTimePeriod.
	 * @param invite The LgInvite to set.
	 * @return Returns The LgInvite.
	 */ 
	//not clear, how it will be used
//	public LgInvite setTimePeriod(LgInvite invite){
//			for (final LgTimePeriod period : this.timePeriods) {
//				period.setInvite(invite);
//			}
//			return this;
//	}

	@Override
	public String toString() {
		return String
				.format("LgInvite [isHost=%s, isIgnored=%s, user=%s, survey=%s, oid=%s, pool=%s]",
						isHost, isIgnored, user, survey, oid, pool);
	}
}
