package de.bht.comanche.logic;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.bht.comanche.persistence.DbObject;
@Entity
public class LgUser extends DbObject {
    private String name;
    private String telephone;
    private String email;
    private String password;
    
    public LgUser() {}
    
    public LgUser(String name, String telephone, String email, String password) {
		super();
		this.name = name;
		this.telephone = telephone;
		this.email = email;
		this.password = password;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
    	return super.getId();
    }
    
    public void setId(Long id) {
    	super.setId(id);
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
}
