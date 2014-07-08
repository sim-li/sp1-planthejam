package de.bht.comanche.logic;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="UserAccount")
public class LgUser extends DbObject {
    
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	private static final long serialVersionUID = 1L;
	private String name;
    private String telephone;
    private String email;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public List<LgGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<LgGroup> groups) {
		this.groups = groups;
	}
	
	@Override
	public String toString() {
		return "LgUser [name=" + name + ", telephone=" + telephone + ", email="
				+ email + ", password=" + password + ", hatFriends="
				+ hatFriends + ", beFriendFromUsers=" + beFriendFromUsers
				+ ", groups=" + groups + "]";
	}
}
