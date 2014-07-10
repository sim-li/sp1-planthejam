package de.bht.comanche.logic;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	
	@Column(length = 125)
	private String name;
	@Column(length = 25)
    private String tel;
    private String email;
	@Column(length = 125)
    private String password;
   
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="contact", joinColumns = {
    		@JoinColumn(name="user_Id", referencedColumnName="oid")}, inverseJoinColumns = {
    		@JoinColumn(name="friend_Id", referencedColumnName="oid")})
    private List<LgUser> hatFriends;

    @ManyToMany(mappedBy="hatFriends")
    private List<LgUser> beFriendFromUsers;
    
    @ManyToMany
	@JoinTable(name="user_group")			  
	private List<LgGroup> groups;
//
//	@OneToMany(mappedBy = "usi.user")
//	private List<LgInvite> invites;
	
	
	public LgUser() {}
	
	public LgUser(String name, String tel, String email, String password,
			List<LgUser> hatFriends, List<LgUser> beFriendFromUsers,
			List<LgGroup> groups, List<LgInvite> invites) {
		super();
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.password = password;
		this.hatFriends = hatFriends;
		this.beFriendFromUsers = beFriendFromUsers;
		this.groups = groups;
//		this.invites = invites;
	}
//	
//	public LgUser(LgUser other) {
//		this(other.name, other.tel, other.email, other.password, other.hatFriends, 
//				other.beFriendFromUsers, other.groups, other.invites);
//	}
//	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String telephone) {
		this.tel = telephone;
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

	public List<LgUser> getHatFriends() {
		return hatFriends;
	}

	public void setHatFriends(List<LgUser> hatFriends) {
		this.hatFriends = hatFriends;
	}

	public List<LgUser> getBeFriendFromUsers() {
		return beFriendFromUsers;
	}

	public void setBeFriendFromUsers(List<LgUser> beFriendFromUsers) {
		this.beFriendFromUsers = beFriendFromUsers;
	}

//	public List<LgInvite> getInvites() {
//		return invites;
//	}
//
//	public void setInvites(List<LgInvite> invites) {
//		this.invites = invites;
//	}
	
	public boolean passwordMatchWith(String password) {
		if (this.password == null) {
			return false;
		}
		return this.password.equals(password);
	}
	
	@Override
	public String toString() {
		return "LgUser [oid=" + getOid() + ", name=" + name + ", telephone=" + tel + ", email="
				+ email + ", password=" + password + ", hatFriends="
				+ hatFriends + ", beFriendFromUsers=" + beFriendFromUsers
				+ ", groups=" + groups + "]";
	}

	/**
	 * Copies all attributes from the other user except(!) the oid.
	 * @param other	the other user
	 */
	public void updateWith(LgUser other) {
		this.name = other.name;
		this.tel = other.tel;
		this.email = other.email;
		this.password = other.password;
		this.hatFriends = other.hatFriends;
		this.beFriendFromUsers = other.beFriendFromUsers;
		this.groups = other.groups;
//		this.invites = other.invites;
	}
}
