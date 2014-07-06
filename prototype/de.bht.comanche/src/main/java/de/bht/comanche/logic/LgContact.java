package de.bht.comanche.logic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Contacts")
public class LgContact extends DbObject{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="contact")
	private List<LgUser> contact_users;
	
	@ManyToOne
	private LgGroup group;

	public LgGroup getGroup() {
		return group;
	}
	
	public void setGroup(LgGroup group) {
		this.group = group;
	}
	
	public List<LgUser> getContact_users() {
		return contact_users;
	}
	
	public void setContact_users(List<LgUser> contact_users) {
		this.contact_users = contact_users;
	}	
}
