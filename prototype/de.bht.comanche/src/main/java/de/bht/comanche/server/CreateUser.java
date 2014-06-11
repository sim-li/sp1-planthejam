package de.bht.comanche.server;

public class CreateUser {

	private String email;
	private String firstName;
	private String lastName;
	private int id;

	public CreateUser() {

	}

	public CreateUser(String email, String fname, String lname, int id) {
		this.email = email;
		this.firstName = fname;
		this.lastName = lname;
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setFirstName(String fname) {
		this.firstName = fname;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setLastName(String lname) {
		this.lastName = lname;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return new StringBuffer(" Email : ").append(this.email)
				.append(" First Name : ").append(this.firstName)
				.append(" Last Name : ").append(this.lastName).append(" ID : ")
				.append(this.id).toString();
	}

}
