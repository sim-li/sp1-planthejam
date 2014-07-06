package de.bht.comanche.logic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="gruppe")
public class LgGroup extends DbObject {

	private static final long serialVersionUID = 1L;
	private String name;
	
	@OneToMany(mappedBy="group")
	private List<LgContact> contacts;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<LgContact> getContacts() {
		return contacts;
	}
	public void setContacts(List<LgContact> contacts) {
		this.contacts = contacts;
	}
}
