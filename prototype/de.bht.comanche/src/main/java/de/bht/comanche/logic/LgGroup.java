package webTest.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//@Entity
//@Table(name="LgGroup")
public class LgGroup extends DbObject {

	private static final long serialVersionUID = 1L;
	private String name;
	
	@ManyToMany(mappedBy="groups")
	private List<LgUser> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LgUser> getUsers() {
		return users;
	}

	public void setUsers(List<LgUser> users) {
		this.users = users;
	}
}
