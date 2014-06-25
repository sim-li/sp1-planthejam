package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.bht.comanche.persistence.DbObject;
@Entity
public class LgUser extends DbObject {
    private String firstName;
    private String lastName;
    private String password;
    private Date birthdate;

    public LgUser() {}
    
    public LgUser(String firstName, String lastName, String password, Date birthdate) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.password = password;
    	this.birthdate = birthdate;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
    	return super.getId();
    }
    
    public void setId(Long id) {
    	super.setId(id);
    }
   
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
