package de.bht.comanche.logic;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "user")
public class LgUser extends DbObject {

	private static final long serialVersionUID = 1L;

	private String name;
	private String tel;
	private String email;
	private String password;

//	@ManyToMany(cascade = { CascadeType.ALL })
	
	@JoinTable(name = "contact", joinColumns = { 
			@JoinColumn(name = "user_Id", referencedColumnName = "oid")}, inverseJoinColumns = { 
			@JoinColumn(name = "friend_Id", referencedColumnName = "oid")})
	@ManyToMany(fetch = FetchType.EAGER)
	private List<LgUser> hasContacts;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "hasContacts")
	private List<LgUser> isContacts;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="user")
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

	public LgUser setName(String name) {
		this.name = name;
		return this;
	}

	public String getTel() {
		return tel;
	}

	public LgUser setTel(String tel) {
		this.tel = tel;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public LgUser setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public LgUser setPassword(String password) {
		this.password = password;
		return this;
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

	public LgUser setIsContacts(List<LgUser> isContacts) {
		this.isContacts = isContacts;
		return this;
	}

	public void addContact(LgUser user) {
		this.getHasContacts().add(user);
		user.getIsContacts().add(this);
	}

	public void removeContact(LgUser user) {
		this.getHasContacts().remove(user);
		user.getIsContacts().remove(this);
	}
	@JsonIgnore
	public List<LgInvite> getInvites() {
		return invites;
	}

	public LgUser setInvites(List<LgInvite> invites) {
		this.invites = invites;
		return this;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((hasContacts == null) ? 0 : hasContacts.hashCode());
		result = prime * result + ((invites == null) ? 0 : invites.hashCode());
		result = prime * result
				+ ((isContacts == null) ? 0 : isContacts.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LgUser other = (LgUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (hasContacts == null) {
			if (other.hasContacts != null)
				return false;
		} else if (!hasContacts.equals(other.hasContacts))
			return false;
		if (invites == null) {
			if (other.invites != null)
				return false;
		} else if (!invites.equals(other.invites))
			return false;
		if (isContacts == null) {
			if (other.isContacts != null)
				return false;
		} else if (!isContacts.equals(other.isContacts))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LgUser [name=" + name + ", tel=" + tel + ", email=" + email
				+ ", password=" + password +
//				", hasContacts=" + hasContacts
//				+ ", isContacts=" + isContacts +
				", invites=" + invites + "]" +
				"OID>: " + getOid();
	}
	
}
