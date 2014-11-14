package de.bht.comanche.logic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private LgUser user;
		
	@OneToMany(mappedBy="group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<LgMember> member ;
	
	public String getGroupName() {
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
	
	public long getGroupOid() {
		return this.getOid();
	}
	
//	public LgGroup save() {
//		return pool.save(this);
//	}
	
	public void delete() {
		user.removeGroup(this);
		pool.delete(this); //throw exc when delete errror
	}
	
//---------LgMember operations----------------	
	
	public List<LgMember> getLgMembers(){
		return member;
	}
	
	
	//not used
//	public LgMember lgUserToLgMember(LgUser user){
//		return new LgMember(user);
//	}
	
	//not used
//	public List<LgMember> addUserMember(LgUser user){
//		List<LgMember> result = new ArrayList<LgMember>();
//		if(!(user == null)){
//			 result.set(0, lgUserToLgMember(user));
//			 return result;
//		}
//		return result;
//	}
	
	//not used
//	public List<LgMember> addLgMember(LgMember lgMember){
//		List<LgMember> result = new ArrayList<LgMember>();
//		if(!(user == null)){
//			 result.set(0, lgMember);
//			 return result;
//		}
//		return result;
//	}
	
	//not used
//	public LgGroup setMembers(List<LgMember> member) {
//		this.member = member;
//		return this;
//	}
	
	
	
//	TODO How to find group by id - pool.find-getOid??
//	public LgGroup getLgGroup(User , long group_oid){
//		return null;
//	}
	

}
