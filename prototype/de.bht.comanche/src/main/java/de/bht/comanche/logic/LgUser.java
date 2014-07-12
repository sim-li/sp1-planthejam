package de.bht.comanche.logic;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class LgUser extends DbObject {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String tel;
	private String email;
	private String password;

	@Id
	@Column(name="USER_ID")
	private long id;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private List<LgInvite> invites;

	public LgUser() {
		this.invites = new LinkedList<LgInvite>();
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

	public List<LgInvite> getInvites() {
		return invites;
	}

	public void setInvites(List<LgInvite> invites) {
		this.invites = invites;
	}
	
	public boolean addInvite(LgInvite invite) {
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
