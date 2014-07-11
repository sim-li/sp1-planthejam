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

//	@ManyToMany(cascade = { CascadeType.ALL })
//	@JoinTable(name = "contact", joinColumns = { 
//			@JoinColumn(name = "user_Id") }, inverseJoinColumns = { 
//			@JoinColumn(name = "friend_Id") })
//	private List<LgUser> hasContacts;

//	@ManyToMany(mappedBy = "hasContacts")
	
//	@ManyToMany(mappedBy = "user_id")
//	private List<LgUser> contacts;

	@OneToMany(mappedBy="user")
	private List<LgInvite> invites;

	public LgUser() {
//		this.hasContacts = new LinkedList<LgUser>();
//		this.contacts = new LinkedList<LgUser>();
		this.invites = new LinkedList<LgInvite>();
	}
	
	public LgUser(String name, String tel, String email, String password,
			/*List<LgUser> hasContacts,*/ List<LgUser> contacts,
			List<LgInvite> invites) {
		super();
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.password = password;
//		this.hasContacts = hasContacts == null ? new LinkedList<LgUser>() : hasContacts;
//		this.contacts = contacts;
		this.invites = invites;
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

//	public List<LgUser> getHasContacts() {
//		return hasContacts;
//	}
//
//	public void setHasContacts(List<LgUser> hasContacts) {
//		this.hasContacts = hasContacts;
//	}
//	
//	public boolean addHasContact(LgUser hasContact) {
//		return this.hasContacts.add(hasContact);
//	}
//	
//	public boolean removeHasContact(LgUser hasContact) {
//		return this.hasContacts.remove(hasContact);
//	}

//	public List<LgUser> getContacts() {
//		return contacts;
//	}
//
//	public void setContacts(List<LgUser> contacts) {
//		this.contacts = contacts;
//	}
//	
//	public boolean addContact(LgUser contact) {
//		return this.contacts.add(contact);
//	}
//	
//	public boolean removeContact(LgUser contact) {
//		return this.contacts.remove(contact);
//	}

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
//		this.hasContacts = other.hasContacts;
//		this.contacts = other.contacts;
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
