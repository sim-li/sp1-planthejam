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

import org.codehaus.jackson.annotate.JsonIgnore;

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
		
	@OneToMany(mappedBy="group", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<LgMember> members;
	
	public String getName() {
		return name;
	}

	public LgGroup setName(String name) {
		this.name = name;
		return this;
	}
	
	@JsonIgnore
	public LgUser getUser() {
		return user;
	}

	public LgGroup setUser(LgUser user) {
		this.user = user;
		return this;
	}
	
	public LgGroup save() {
		return pool.save(this);
	}
	
	public void delete() {
		user.remove(this);
		pool.delete(this); //throw exc when delete errror
	}
	
//	TODO How to find group by id - pool.find-getOid??
//	public LgGroup getLgGroup(long oid){
//		return null;
//	}
	

}
