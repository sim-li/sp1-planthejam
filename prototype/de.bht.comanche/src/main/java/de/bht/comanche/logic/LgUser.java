package de.bht.comanche.logic;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "user")
public class LgUser extends DbObject {

	private static final long serialVersionUID = 1L;

	private String name;
	private String tel;
	private String email;
	private String password;

	@JoinTable(name = "contact", joinColumns = { @JoinColumn(name = "user_Id", referencedColumnName = "oid") }, inverseJoinColumns = { @JoinColumn(name = "friend_Id", referencedColumnName = "oid") })
	@ManyToMany(cascade = CascadeType.ALL)
	private List<LgUser> hasContacts;

	@ManyToMany(mappedBy = "hasContacts", cascade = CascadeType.ALL)
	private List<LgUser> isContacts;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<LgInvite> invites;

	public LgUser() {
		this.hasContacts = new LinkedList<LgUser>();
		this.isContacts = new LinkedList<LgUser>();
		this.invites = new LinkedList<LgInvite>();
	}

	public LgUser(String name, String tel, String email, String password,
			List<LgUser> hasContacts, List<LgUser> isContacts,
			List<LgInvite> invites) {
		super();
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.password = password;
		this.hasContacts = hasContacts == null ? new LinkedList<LgUser>()
				: hasContacts;
		this.isContacts = isContacts == null ? new LinkedList<LgUser>()
				: isContacts;
		this.invites = invites == null ? new LinkedList<LgInvite>() : invites;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<LgUser> getHasContacts() {
		return hasContacts;
	}

	public void setHasContacts(List<LgUser> hasContacts) {
		this.hasContacts = hasContacts;
	}

	public List<LgUser> getIsContacts() {
		return isContacts;
	}

	public void setIsContacts(List<LgUser> isContacts) {
		this.isContacts = isContacts;
	}

	public void addContact(LgUser user) {
		this.getHasContacts().add(user);
		user.getHasContacts().add(this);
	}

	public void removeContact(LgUser user) {
		this.getHasContacts().remove(user);
		user.getHasContacts().remove(this);
	}

	public List<LgInvite> getInvites() {
		return invites;
	}

	public void setInvites(List<LgInvite> invites) {
		this.invites = invites;
	}

	public boolean addInvites(LgInvite invite) {
		return this.invites.add(invite);
	}

	public boolean removeInvites(LgInvite invite) {
		return this.invites.remove(invite);
	}

	public void updateWith(LgUser other) {
		this.name = other.name;
		this.tel = other.tel;
		this.email = other.email;
		this.password = other.password;
		this.hasContacts = other.hasContacts;
		this.isContacts = other.isContacts;
		this.invites = other.invites;
	}

	public boolean passwordMatchWith(LgUser user) {
		final String password = user.getPassword();
		if (this.password == null) {
			return false;
		}
		return this.password.equals(password);
	}
}
