package de.bht.comanche.logic;

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

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "contact", joinColumns = { 
			@JoinColumn(name = "user_Id") }, inverseJoinColumns = { 
			@JoinColumn(name = "friend_Id") })
	private List<LgUser> hasContacts;

	@ManyToMany(mappedBy = "hasContacts")
	private List<LgUser> isContacts;

	@OneToMany(mappedBy="user")
	private List<LgInvite> invites;

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

	public List<LgInvite> getInvites() {
		return invites;
	}

	public void setInvites(List<LgInvite> invites) {
		this.invites = invites;
	}
	
	public boolean passwordMatchWith(LgUser user) {
		return true;
	}
}
