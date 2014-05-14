package de.bht.comanche;

import java.util.Date;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private Date birthdate;

    public User() {}

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
         this.id = id;
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
