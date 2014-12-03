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
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<LgInvite> invites;
	/**
	 * Representation of a foreign key in a LgGroup entity. Provide a list of groups. 
	 */
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<LgGroup> groups;
	/**
	 * Representation of a foreign key in a LgMember entity. Provide a member. 
	 */
	@OneToOne(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval=true)
	private LgMember member;
	
	/**
	 * Construct a new LgUser object with a list of groups and members.
	 */
	public LgUser() {
		this.invites = new ArrayList<LgInvite>();
		this.groups = new ArrayList<LgGroup>();
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
	

	public LgGroup save(final LgGroup group) {
		group.setUser(this).setForMember(group);
		return attach(group).save();
	}
	

	public void deleteGroup(final long groupOid) {
		getGroup(groupOid).delete();
	}
	
	public LgGroup getGroup(final long groupOid) {
		return search(getGroups(), groupOid);
	}

	public LgMember save(final LgMember member) {
		return attach(member).save();
	}
	
	public List<LgMember> search(final long groupId, final long userId) {
		return search(LgMember.class, "GROUP_OID", groupId, "USER_OID", userId);
	}
	
	public boolean passwordMatchWith(final LgUser user) {
		if (this.password == null) {
			return false; // TODO should it be possible/allowed to have no password? if no -> should throw exception
		}
		return this.password.equals(user.getPassword());
	}

	public LgInvite getInvite(final long inviteOid) {
		return search(this.invites, inviteOid);
	}
	
	public void remove(final LgInvite invite) {
		this.invites.remove(invite);
	}
	
	public void remove(final LgGroup group) {
		this.groups.remove(group);
	}
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	@JsonIgnore
	public List<LgInvite> getInvites() {
		return this.invites;
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
	
	@Override
	public String toString() {
		return String
				.format("LgUser [name=%s, tel=%s, email=%s, password=%s, invites=%s, groups=%s, member=%s, oid=%s, pool=%s]",
						name, tel, email, password, invites, groups, member,
						oid, pool);
	}
}
