package de.bht.comanche.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.bht.comanche.persistence.DaObject;

@Entity
@Table(name = "group")
public class LgGroup extends DaObject{

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	private String name;

	@NotNull
	@ManyToOne
	private LgUser user;

	@OneToMany(mappedBy="group", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<LgMember> members;

	public LgGroup() {
		this.members = new ArrayList<LgMember>();
	}

	public LgMember getMember(final long oid) {
		return search(this.members, oid);
	}

	public void deleteMember(final long oid) {
		search(this.members, oid).delete();
	}

	public void deleteUser(final long userOid) {
		for (final LgMember member : this.members) {
			final LgUser user = member.getUser();
			if (user.getOid() == userOid) {
				user.remove(this);
				this.members.remove(member);
			}
		}
	}
	
	public LgGroup setForMember(LgGroup group){
			for (final LgMember member : group.getMembers()) {
				member.setGroup(group);
			}
			return this;
	}
	
	public List<LgUser> getUsers() {
		final List<LgUser> users = new LinkedList<LgUser>();
		for (final LgMember member : this.members) {
			users.add(member.getUser());
		}
		return users;
	}

	public String getName() {
		return this.name;
	}

	public LgGroup setName(final String name) {
		this.name = name;
		return this;
	}

	public LgGroup setUser(final LgUser user) {
		this.user = user;
		return this;
	}

	public List<LgMember> getMembers() {
		return this.members;
	}

	@Override
	public String toString() {
		return String.format(
				"LgGroup [name=%s, user=%s, members=%s, oid=%s, pool=%s]",
				name, user, members, oid, pool);
	}
}
