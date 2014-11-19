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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private List<LgMember> members ;
	
	public String getGroupName() {
		return name;
	}

	public LgGroup setGroupName(String name) {
		this.name = name;
		return this;
	}
	
	public LgGroup setUser(LgUser user) {
		this.user = user;
		return this;
	}
	
//	public List<LgMember> getMember(){
//		return members;
//	}
		
	//it doesn't work on this place -> only in LgMember
//	public List<LgMember> findMemberByTwoId(long groupId, long userId) {
//		// throw exc when user not found
//		List<LgMember> lg = null;
//		try{
//			lg = pool.findManyByTwoKeys(LgMember.class, "GROUP_OID", groupId, "USER_OID", userId);
//		} catch (Exception e) {
//			multex.Msg.printReport(System.err, e);
//		}
//		return lg;
//	}
	
//	public long getGroupOid() {
//		return this.getOid();
//	}
	
//	public LgGroup save() {
//		return pool.save(this);
//	}

//---------LgMember operations----------------	
	
	
//-------------delete user operations------------
//	private LgMember getLgMember(long oid) {
//		System.out.println("------------getLgMember: " + search(getLgMembers(), oid) + " -----------------");
//		return search(getLgMembers(), oid);
//	}
//	
//	//not work
//	public void deleteLgMember(final long oid) {
//		getLgMember(oid).delete();
//	}
//	
//	public List<LgMember> getLgMembers(){
//		System.out.println("------------members.size(): " + members.size() + " -----------------");
//		System.out.println("------------members.oid(): " + members.get(0).getMemberId() + " -----------------");
//		return members;
//	}
//------------------------------------------------------
	
	//is it works on group? - no, on this way, no -> only user.save(member)
//	public LgMember save(final LgMember lgMember) {
//		return attach(lgMember).save();
//	}
	
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
//	public LgGroup setMembers(List<LgMember> members) {
//		this.member = members;
//		return this;
//	}

}
