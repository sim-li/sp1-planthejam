package de.bht.comanche.User;

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

    private String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String getPassword(){
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private Date getBirthdate() {
        return birthdate;
    }

    private void getBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
