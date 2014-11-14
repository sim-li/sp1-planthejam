package de.bht.comanche.logic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.bht.comanche.persistence.DaObject;

@Entity
@Table(name = "member")
public class LgMember extends DaObject{
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@ManyToOne
	private LgGroup group;
	
	@OneToOne
	private LgUser user;
	
	public long getMemberId() {
		return user.getOid();
	}
	
	protected LgMember(){}
	
	public LgMember(LgUser user, LgGroup group) {
		this.group = group;
		this.user = user;
	}
	
//	public LgMember getLgMemberByOid(long oid) {
//		this.search(list, oid);
//		return null;
//	}
	
//	public LgMember setLgMember(LgUser user, LgGroup group) {
//		this.group = group;
//		this.user = user;
//		return this;
//	}
	
//	public LgMember save() {
//		return pool.save(this);
//	}
	

//	
//	public void setMemberId(final long userOid) {
//		this.userOid = userOid;
//	}
	
//	public void deleteMember(final long userOid) //only one member (it's reference from table)
	
}
