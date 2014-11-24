package de.bht.comanche.logic;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.bht.comanche.persistence.DaObject;

@Entity
@Table(name = "group")
public class LgGroup extends DaObject{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	private String name;

	@NotNull
	@ManyToOne
	private LgUser user;
		
	@OneToMany(mappedBy="group", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<LgMember> members;
	
	
	public LgMember getMember(final long oid) {
		return search(members, oid);
	}
	
	public void deleteMember(final long oid) {
		search(members, oid).delete();
	}
	
	public String getName() {
		return name;
	}

	public LgGroup setName(String name) {
		this.name = name;
		return this;
	}
	
	public LgGroup setUser(LgUser user) {
		this.user = user;
		return this;
	}
	
	public List<LgMember> getMembers() {
		return members;
	}
}
