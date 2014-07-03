package webTest.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="gruppe")
public class Group extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="groups")
	private List<User> users;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="group_poll")		
	private List<Poll> group_polls;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
