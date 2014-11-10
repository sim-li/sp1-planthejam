package de.bht.comanche.logic;

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
 * @author Duc Tung Tong
 */
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
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<LgGroup> groups;
	
	@OneToOne(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private LgMember lgMember;
	
	public LgInvite save(final LgInvite invite) {
		return attach(invite).save();
	}
	
	public LgGroup save(final LgGroup group) {
		return attach(group).save();
	}

	public void deleteAccount() {
		delete();
	}

	public void deleteInvite(final long oid) {
		getInvite(oid).delete();
	}
	
	public void deleteGroup(final long oid) {
		getGroup(oid).delete();
	}
	
	public boolean passwordMatchWith(LgUser user) {
		final String password = user.getPassword();
		if (this.password == null) {
			return false;
		}
		return this.password.equals(password);
	}

	public LgInvite getInvite(long oid) {
		return search(getInvites(), oid);
	}
	
	private LgGroup getGroup(long oid) {
		return search(getGroups(), oid);
	}
   
	public void removeInvite(final LgInvite invite) {
		invites.remove(invite);
	}
	
	public void removeGroup(final LgGroup group) {
		groups.remove(group);
	}

	/**
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	@JsonIgnore
	public List<LgInvite> getInvites() {
		return invites;
	}
	
	@JsonIgnore
	public List<LgGroup> getGroups() {
		return groups;
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
