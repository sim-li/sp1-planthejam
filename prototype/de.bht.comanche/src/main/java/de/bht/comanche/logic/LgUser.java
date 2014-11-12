package de.bht.comanche.logic;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Duc Tung Tong
 */
@Entity
@Table(name = "user", uniqueConstraints=@UniqueConstraint(columnNames="NAME"))
public class LgUser extends LgObject {

	private static final long serialVersionUID = 1L;
	@Column(unique=true, nullable=false)
	private String name;
	private String tel;
	private String email;
	private String password;

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<LgInvite> invites;

	public LgUser() {
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
		this.invites = invites == null ? new LinkedList<LgInvite>() : invites;
	}

	public LgUser(long oid) {
		super(oid);
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

	@JsonIgnore
	public List<LgInvite> getInvites() {
		return invites;
	}

	public LgUser setInvites(List<LgInvite> invites) {
		this.invites = invites;
		return this;
	}

	public boolean addInvite(LgInvite invite) {
		return this.invites.add(invite);
	}

	public boolean removeInvite(LgInvite invite) {
		return this.invites.remove(invite);
	}

	public boolean passwordMatchWith(LgUser user) {
		final String password = user.getPassword();
		if (this.password == null) {
			return false;
		}
		return this.password.equals(password);
	}
	
	public void clearInvites() {
		this.invites.clear();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((invites == null) ? 0 : invites.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LgUser other = (LgUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (invites == null) {
			if (other.invites != null)
				return false;
		} else if (!invites.equals(other.invites))
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
				", invites=" + invites + "]" +
				"OID>: " + getOid();
	}
	
}
