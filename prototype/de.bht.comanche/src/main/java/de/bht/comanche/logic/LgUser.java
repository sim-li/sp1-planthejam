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
    		@JoinColumn(name="user_Id", referencedColumnName="id")}, inverseJoinColumns = {
    		@JoinColumn(name="friend_Id", referencedColumnName="id")})
    private List<LgUser> hatFriends;

    @ManyToMany(mappedBy="hatFriends")
    private List<LgUser> beFriendFromUsers;
    
    @ManyToMany
	@JoinTable(name="user_group")			  
	private List<LgGroup> groups;

	@OneToMany(mappedBy = "usi.user")
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

	public List<LgInvite> getInvites() {
		return invites;
	}

	public void setInvites(List<LgInvite> invites) {
		this.invites = invites;
	}
	
	public boolean validatePassword(String password) {
		if (this.password == null) {
			return false;
		}
		return this.password.equals(password);
	}
	@Override
	public String toString() {
		return "LgUser [name=" + name + ", telephone=" + tel + ", email="
				+ email + ", password=" + password + ", hatFriends="
				+ hatFriends + ", beFriendFromUsers=" + beFriendFromUsers
				+ ", groups=" + groups + "]";
	}
}
