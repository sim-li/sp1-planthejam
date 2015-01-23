package de.bht.comanche.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.bht.comanche.persistence.DaObject;

/**
 * This entity class represents a user and serve methods for working with all
 * objects LgClasses.
 *
 * @author Simon Lischka
 *
 */
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class LgUser extends DaObject {

	private static final long serialVersionUID = 1L;
	/**
	 * Column for a user name. Must not be null.
	 */
	@Column(unique = true, nullable = false)
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
	 * URL to gravatar icon
	 */
	private String iconurl;
	/**
	 * Representation of a foreign key in a LgInvite entity. Provide a list of
	 * invites.
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<LgInvite> invites;
	/**
	 * Representation of a foreign key in a LgGroup entity. Provide a list of
	 * groups.
	 */
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<LgGroup> groups;
	/**
	 * Representation of a foreign key in a LgMember entity. Provide a member.
	 */
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private LgMember member;
	
	@ElementCollection(targetClass = LgTimePeriod.class, fetch = FetchType.EAGER)
	@Column(name = "general_availability")
	private Set<LgTimePeriod> generalAvailability;

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@Column(name = "messages")
	private Set<LgMessage> messages = new HashSet<LgMessage>();

	public LgUser() {
		this.invites = new ArrayList<LgInvite>();
		this.groups = new ArrayList<LgGroup>();
	}

	public LgInvite getInviteBySurveyName(final String name) {
		for (LgInvite invite : invites) {
			if (invite.getSurvey().getName() == name) {
				return invite;
			}
		}
		return null;
		// @TODO Throw Multex Exception
	}

	public LgSurvey getSurveyByName(final String name) {
		for (LgInvite invite : invites) {
			final LgSurvey survey = invite.getSurvey();
			if (survey.getName() == name) {
				return survey;
			}
		}
		return null;
		// @TODO Throw Multex Exception
	}

	/**
	 * Complete deleting of a user accout.
	 */
	public void deleteThisAccount() {
		for (LgInvite invite : this.getInvites()) {
			attachPoolFor(invite).delete();
		}
		delete();
	}

	public void deleteOtherUserAccount(final LgUser user) {
		this.findOneByKey(LgUser.class, "oid", user.getOid()).delete();
	}

	/**
	 * Delete LgInvite by provided oid.
	 * 
	 * @param inviteOid
	 *            The LgInvite oid.
	 */
	public void deleteInvite(final long inviteOid) {
		getInvite(inviteOid).delete();
	}

	/**
	 * Save LgGroup for current user.
	 * 
	 * @param group
	 *            The LgGroup to save.
	 * @return The saved LgGroup.
	 */
	public LgGroup save(final LgGroup group) {
		group.setUser(this).setForMember(group);
		return saveUnattached(group);
	}

	/**
	 * Generates icon url from classes internal email Gravatar will deliver a
	 * default icon if no email given.
	 */
	public String getIconurl() {
		final LgGravatarUtils utils = new LgGravatarUtils();
		if (email != null) {
			iconurl = utils.getUserUrl(email);
		} else {
			iconurl = utils.getUserUrl(name);
		}
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	/**
	 * Delete LgGroup by provided oid.
	 * 
	 * @param groupOid
	 *            The LgGroup oid.
	 */
	public void deleteGroup(final long groupOid) {
		getGroup(groupOid).delete();
	}

	/**
	 * Returns LgGroup by ptovided oid.
	 * 
	 * @param groupOid
	 *            The LgGroup oid.
	 * @return The found LgGroup
	 */
	public LgGroup getGroup(final long groupOid) {
		return search(getGroups(), groupOid);
	}

	/**
	 * Search LgMember object by group oid and user oid.
	 * 
	 * @param groupId
	 *            The LgGroup oid.
	 * @param userId
	 *            The LgUser oid.
	 * @return The found list with LgMember.
	 */
	public List<LgMember> search(final long groupId, final long userId) {
		return search(LgMember.class, "GROUP_OID", groupId, "USER_OID", userId);
	}

	/**
	 * Proof key and value of user name and password.
	 * 
	 * @param user
	 *            The LgUser to proof.
	 * @return If the key and value match - true.
	 */
	public boolean passwordMatchWith(final LgUser user) {
		if (this.password == null) {
			return false; // TODO should it be possible/allowed to have no
							// password? if no -> should throw exception
		}
		return this.password.equals(user.getPassword());
	}

	/**
	 * Returns LgTimePeriods list for current user.
	 * 
	 * @return The list with LgTimePeriods.
	 */
	
	public Set<LgTimePeriod> getGeneralAvailability() {
		return this.generalAvailability;
	}

	/**
	 * Returns LgTimePeriods list for current user.
	 * 
	 * @return The list with LgTimePeriods.
	 */
	public LgUser setGeneralAvailability(Set<LgTimePeriod> generalAvailability) {
		this.generalAvailability = generalAvailability;
		return this;
	}

	/**
	 * Remove invite object from the list of invites.
	 * 
	 * @param invite
	 *            The LgInvite to remove.
	 */
	public void remove(final LgInvite invite) {
		this.invites.remove(invite);
	}

	/**
	 * Remove grop object from the list of groups.
	 * 
	 * @param invite
	 *            The LgGroup to remove.
	 */
	public void remove(final LgGroup group) {
		this.groups.remove(group);
	}

	// -- HOST ROLES --
	public LgSurvey getSurvey(final long oid) {
		for (LgInvite invite : this.invites) {
			if (invite.isHost() && invite.getSurvey().getOid() == oid) {
				return invite.getSurvey();
			}
		}
		return null; // TODO: Throw MULTEX exception
	}
	
	@JsonIgnore
	public List<LgSurvey> getSurveys() {
		List<LgSurvey> surveys = new ArrayList<LgSurvey>();
		for (LgInvite invite : this.invites) {
			if (invite.isHost()) {
				surveys.add(invite.getSurvey());
			} 
		}
		return surveys;
	}
	
	@JsonIgnore
	public List<LgInvite> getInvites() {
		return this.invites;
	}

	public List<LgInvite> getInvitesForSurvey(final long oid) {
		List<LgInvite> filteredInvites = new ArrayList<LgInvite>();
		for (LgInvite invite : this.getSurvey(oid).getInvites()) {
			filteredInvites.add(new LgInvite(invite).setSurvey(null));
		}
		return filteredInvites;
	}

	public LgSurvey saveSurvey(final LgSurvey survey) {
		List<LgInvite> dirtyInvites = survey.getInvites();
		addHostInvite(dirtyInvites);
		survey.setInvites(new ArrayList<LgInvite>());
		final LgSurvey persistedSurvey = saveUnattached(survey);
		persistInvitesAndAddToSurvey(persistedSurvey, dirtyInvites);
		return persistedSurvey;
	}

	public LgSurvey updateSurvey(final LgSurvey surveyFromClient) {
		
		List<LgSurvey> listFromDB = new ArrayList<LgSurvey>();
		listFromDB.add(findOneByKey(LgSurvey.class, "OID", this.getOid()));
		List<LgSurvey> listfresh = new ArrayList<LgSurvey>();
		listfresh.add(surveyFromClient);
		
		listFromDB.retainAll(listfresh); // PL & its objs must be tracked for
		listfresh.removeAll(listFromDB); // ELs already saved
		
		for (LgSurvey el : listfresh) {
		System.out.println(el);
		saveUnattached(el); // Fresh list are never tracked
		}
		
		listFromDB.addAll(listfresh); // Requires PL tracking too
		return listFromDB.get(0);
	}

	private void persistInvitesAndAddToSurvey(LgSurvey persistedSurvey,
			List<LgInvite> dirtyInvites) {
		for (int i = 0; i < dirtyInvites.size(); i++) {
			final LgInvite dirtyInvite = dirtyInvites.get(i);
			dirtyInvite.setSurvey(persistedSurvey);
			persistedSurvey.addInvite(saveUnattached(dirtyInvite));
		}
	}

	private void addHostInvite(List<LgInvite> dirtyInvites) {
		final LgInvite invite = new LgInvite();
		invite.setHost(true).setIgnored(LgStatus.UNDECIDED).setUser(this);
		dirtyInvites.add(invite);
	}

	public void deleteSurvey(final long oid) {
//		this.getSurvey(oid).delete();
		
		System.out.println("this.getSurvey(oid).getInvites().get(0).getOid()" + this.getSurvey(oid).getInvites().get(0).getOid());
		LgSurvey survey = this.getSurvey(oid);
		List<LgInvite> invites = survey.getInvites();
//		survey.delete();
		List<LgInvite> persistedInvites = findManyByKey(LgInvite.class, "SURVEY_OID", survey.getOid());
//		for (LgInvite invite : invites){
//			attachPoolFor(invite).delete();
////			remove(invite);
//			invite.delete();
//		}
		
		for (LgInvite invite : survey.getInvites()) {
			attachPoolFor(invite).delete();
		}
		attachPoolFor(survey).delete();
	}

	// -- PARTICIPANT ROLES --
	@JsonIgnore
	public List<LgInvite> getInvitesAsParticipant() {
		List<LgInvite> filteredInvites = new ArrayList<LgInvite>();
		for (LgInvite invite : this.invites) {
			if (!invite.isHost()) {
				filteredInvites.add(invite);
			}
		}
		return filteredInvites;
	}

	public LgInvite saveInvite(final LgInvite invite) {
		return saveUnattached(invite);
	}

	public LgInvite updateInvite(final long oid, LgInvite invite) {
		// REDUNDANT
		return saveInvite(invite);
	}

	//------------------ TODO: METHODS FOR SURVEY EVALUATION ------------------

    public void evaluateAllSurveys() {
    	final List<LgSurvey> surveysOfThisUser = getSurveys();
        System.out.println("+#+#+#+#+#+#+#+#+#+#");
        System.out.println("evaluating all survey (" + surveysOfThisUser.size() + ")");
    	for (final LgSurvey survey : surveysOfThisUser) {
            System.out.println("survey " + survey.getOid() + " " + survey.getName() + " " + survey.isReadyForEvaluation());
    		if (survey.isReadyForEvaluation()) {
                System.out.println("READY survey " + survey.getOid() + " " + survey.getName() + " " + survey.isReadyForEvaluation());
    			survey.determine();
    			// sendMessageToHost(survey);
    		}
    	}
    }

    private void sendMessageToHost(final LgSurvey survey) {
        System.out.println(survey);
        System.out.println(survey.getDeterminedTimePeriod());
        System.out.println(survey.getDeterminedTimePeriod().getStartTime());
    	final Date determinedDate = survey.getDeterminedTimePeriod().getStartTime(); // needs formatting
    	final String message = "es konnte folgender / kein Termin ermittelt werden " + determinedDate;
    	// IMPORTANT TODO implementation missing of LgUser.messages
    	// this.messages.add(message);
    }

	// -------------------------------------------------------------------------

    public LgUser updateWith(LgUser other) {
		this.email = other.email;
		this.generalAvailability =  other.generalAvailability;
		this.groups = other.groups;
//		this.iconurl = other.iconurl;
		this.invites = other.invites;
		this.member = other.member;
		this.messages = other.messages;
		this.name = other.name;
		this.oid = other.oid;
		this.password = other.password;
		this.tel = other.tel;	
		return this;
	} 
    //need json split
	public LgInvite getInvite(final long oid) {
		return search(this.invites, oid);
	}

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

	 public Set<LgMessage> getMessages() {
		 return this.messages;
	 }
	
	 public void setMessages(Set<LgMessage> messages) {
		 this.messages = messages;
	 }
	 
	@Override
	public String toString() {
		return String
				.format("LgUser [name=%s, tel=%s, email=%s, password=%s, invites=%s, groups=%s, member=%s, oid=%s, pool=%s]",
						name, tel, email, password, invites, groups, member,
						oid, pool);
	}
}