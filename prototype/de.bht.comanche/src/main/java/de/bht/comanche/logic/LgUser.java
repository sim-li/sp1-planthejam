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

@Entity
@Table(name = "user", uniqueConstraints=@UniqueConstraint(columnNames="NAME"))
public class LgUser extends DaObject {
	
	private static final long serialVersionUID = 1L;
	
	@Column(unique=true, nullable=false)
	private String name;
	private String tel;
	private String email;
	private String password;

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<LgInvite> invites;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<LgGroup> groups;
	
	@OneToOne(mappedBy="user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval=true)
	private LgMember member;
	
	public LgUser() {
		this.invites = new ArrayList<LgInvite>();
		this.groups = new ArrayList<LgGroup>();
	}
	
	public LgInvite save(final LgInvite invite) {
        invite.setUser(this);
		return attach(invite).save();
	}
	
	public void deleteAccount() {
		delete();
	}

	public void deleteInvite(final long inviteOid) {
		getInvite(inviteOid).delete();
	}
	
<<<<<<< HEAD
	public LgGroup save(final LgGroup group) {
		group.setUser(this).setForMember(group);
		return attach(group).save();
	}
	
=======
>>>>>>> d409bc506cf5e95a750ec2af1ec8190d631820e4
	public void deleteGroup(final long groupOid) {
		getGroup(groupOid).delete();
	}
	
	public LgGroup getGroup(final long groupOid) {
		return search(getGroups(), groupOid);
	}
	
	public LgGroup save(final LgGroup group) {
		return attach(group).setUser(this).save();
	}
	
	public LgMember save(final LgMember member) {
		return attach(member).save();
	}
	
	public LgUser save(final LgUser user) {
		return attach(user).save();
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
