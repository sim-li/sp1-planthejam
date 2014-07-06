package de.bht.comanche.logic;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="UserAccount")
public class LgUser extends DbObject {
    
	private static final long serialVersionUID = 1L;
	private String name;
    private String telephone;
    private String email;
    private String password;
    
    @ManyToOne
    private LgContact contact_users;
   
	@ManyToOne
    private LgInvite invite_users;
	
	@ManyToOne
	private LgTimePeriod timePeriod_users;
    
    public LgUser(String name, String telephone, String email, String password) {
		this.name = name;
		this.telephone = telephone;
		this.email = email;
		this.password = password;
	}

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
	
    public LgContact getContact_users() {
		return contact_users;
	}
    
	public void setContact_users(LgContact contact_users) {
		this.contact_users = contact_users;
	}
	
	public LgInvite getInvite_users() {
		return invite_users;
	}
	
	public void setInvite_users(LgInvite invite_users) {
		this.invite_users = invite_users;
	}
}
