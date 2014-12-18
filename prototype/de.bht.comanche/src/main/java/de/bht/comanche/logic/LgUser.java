package de.bht.comanche.logic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.bht.comanche.persistence.DaObject;
/**
 * This entity class represents a user and serve methods for working with 
 * all objects LgClasses.
 * 
 * @author Simon Lischka
 *
 */
@Entity
@Table(name = "user", uniqueConstraints=@UniqueConstraint(columnNames="NAME"))
public class LgUser extends DaObject {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Column for a user name. Must not be null.
	 */
	@Column(unique=true, nullable=false)
	private String name;
	/**
	 * Column for a user's telephone.
	 */
	private String tel;
	/**
	 * Column for a user's email.
	 */
	private String email;
	/**
	 * Column for a user's password.
	 */
	private String password;
	/**
	 * Representation of a foreign key in a LgInvite entity. Provide a list of invites. 
	 */
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<LgInvite> invites;
	/**
	 * Representation of a foreign key in a LgGroup entity. Provide a list of groups. 
	 */
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<LgGroup> groups;
	/**
	 * Representation of a foreign key in a LgMember entity. Provide a member. 
	 */
	@OneToOne(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private LgMember member;
	
	/**
	 * Representation of a foreign key in a LgTimePeriod entity. Provide a list of general user's availablity. 
	 */
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LgTimePeriod> timePeriods;
	
	/**
	 * Construct a new LgUser object with a list of ivites, groups and general availability time.
	 */
	public LgUser() {
		this.invites = new ArrayList<LgInvite>();
		this.groups = new ArrayList<LgGroup>();
		this.timePeriods = new ArrayList<LgTimePeriod>();
	}
	
	public void saveSurveyAsHost(final LgSurvey survey) {
		
		final LgInvite invite = new LgInvite();
		final LgSurvey attachedSurvey = attach(survey);
		attachedSurvey.save();
		
		invite.setHost(true);
		invite.setIgnored(false);
		invite.setSurvey(attachedSurvey);
		attach(invite).save();
	}
	
	public LgInvite getInviteBySurveyName(final String name) {
		for (LgInvite invite: invites) {
			if (invite.getSurvey().getName() == name) {
				return invite;
			}
		}
		return null; 
		//@TODO Throw Multex Exception
	}
	
	public LgSurvey getSurveyByName(final String name) {
		for (LgInvite invite: invites) {
			final LgSurvey survey = invite.getSurvey();
			if (survey.getName() == name) {
				return survey;
			}
		}
		return null; 
		//@TODO Throw Multex Exception
	}
	
	/**
	 * Save Invite for current user.
	 * @param invite The LgInvite to save.
	 * @return The saved LgInvite.
	 */
	public LgInvite save(final LgInvite invite) {
        invite.setUser(this);
		return attach(invite).save();
	}
	/**
	 * Complete deleting of a user accout.
	 */
	public void deleteAccount() {
		delete();
	}
	/**
	 * Delete LgInvite by provided oid.
	 * @param inviteOid The LgInvite oid.
	 */
	public void deleteInvite(final long inviteOid) {
		getInvite(inviteOid).delete();
	}
	
	/**
	 * Save LgGroup for current user.
	 * @param group The LgGroup to save.
	 * @return The saved LgGroup.
	 */
	public LgGroup save(final LgGroup group) {
		group.setUser(this).setForMember(group);
		return attach(group).save();
	}
	
	/**
	 * Delete LgGroup by provided oid.
	 * @param groupOid The LgGroup oid.
	 */
	public void deleteGroup(final long groupOid) {
		getGroup(groupOid).delete();
	}
	
	/**
	 * Returns LgGroup by ptovided oid.
	 * @param groupOid The LgGroup oid.
	 * @return The found LgGroup
	 */
	public LgGroup getGroup(final long groupOid) {
		return search(getGroups(), groupOid);
	}
	
	/**
	 * Save LgMember for current user.
	 * @param member The LgMember to save.
	 * @return The saved LgMember.
	 */
	public LgMember save(final LgMember member) {
		return attach(member).save();
	}
	
	/**
	 * Search LgMember object by group oid and user oid.
	 * @param groupId The LgGroup oid.
	 * @param userId The LgUser oid.
	 * @return The found list with LgMember.
	 */
	public List<LgMember> search(final long groupId, final long userId) {
		return search(LgMember.class, "GROUP_OID", groupId, "USER_OID", userId);
	}
	
	/**
	 * Proof key and value of user name and password.
	 * @param user The LgUser to proof.
	 * @return If the key and value match - true.  
	 */
	public boolean passwordMatchWith(final LgUser user) {
		if (this.password == null) {
			return false; // TODO should it be possible/allowed to have no password? if no -> should throw exception
		}
		return this.password.equals(user.getPassword());
	}
	
	
	
	//TODO improve it
	public LgTimePeriod saveTpforInvite(final LgInvite invite) {
		invite.setTimePeriod(invite);
		return attach(invite).save();
	}
	
	//for DB test only
	public <E extends DaObject> E saveObj(final E other) {
		return attach(other).save();
	}
	
	/**
	 * Set current user for incoming list of time periods
	 * @param periods The list of time periods
	 * @return The 
	 */
	public List<LgTimePeriod> setTPforUser(List<LgTimePeriod> periods){
			for (final LgTimePeriod timePeriod : this.timePeriods) {
				timePeriod.setUser(this);
			}
			return periods;
	}
	
	/**
	 * Returns LgTimePeriods list for current user. 
	 * @return The list with LgTimePeriods.
	 */
	public List<LgTimePeriod> getTimePeriods() {
		return this.timePeriods;
	}
	
	
	/**
	 * Remove invite object from the list of invites.
	 * @param invite The LgInvite to remove.
	 */
	public void remove(final LgInvite invite) {
		this.invites.remove(invite);
	}
	
	/**
	 * Remove grop object from the list of groups.
	 * @param invite The LgGroup to remove.
	 */
	public void remove(final LgGroup group) {
		this.groups.remove(group);
	}
	
	//------------------METHODS FOR REST SERVICE-------
	
	public LgSurvey getSurvey(final long oid){
		return null;
	}
	
	public List<LgSurvey> getSurveys(){
		return null;
	}
	
	public LgSurvey saveSurvey(final LgSurvey survey){
		return null;
	}
	
	public LgSurvey updateSurvey(final long oid, LgSurvey survey){
		return null;
	}
	public void deleteSurvey(final long oid){
		
	}
	
	@JsonIgnore
	public List<LgInvite> getInvites() {
		return this.invites;
	}
	
	/**
	 * Returns LgInvite by provided oid.
	 * @param inviteOid The LgInvite oid.
	 * @return The found LgInvite.
	 */
	public LgInvite getInvite(final long inviteOid) {
		return search(this.invites, inviteOid);
	}
	
	
	public LgInvite saveInvite(final LgInvite invite){
		return null;
	}
	public LgInvite updateInvite(final long oid, LgInvite invite){
		return null;
	}
	
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	
	
	@JsonIgnore
	public List<LgGroup> getGroups() {
		return this.groups;
	}
	
	public String getName() {
		return this.name;
	}

	public LgUser setName(final String name) {
		this.name = name;
		return this;
	}

	public String getTel() {
		return this.tel;
	}

	public LgUser setTel(final String tel) {
		this.tel = tel;
		return this;
	}

	public String getEmail() {
		return this.email;
	}

	public LgUser setEmail(final String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return this.password;
	}

	public LgUser setPassword(final String password) {
		this.password = password;
		return this;
	}
	
	@Override
	public String toString() {
		return String
				.format("LgUser [name=%s, tel=%s, email=%s, password=%s, invites=%s, groups=%s, member=%s, oid=%s, pool=%s]",
						name, tel, email, password, invites, groups, member,
						oid, pool);
	}
}
