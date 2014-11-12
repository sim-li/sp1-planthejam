package de.bht.comanche.logic;

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
	
	@OneToOne(fetch=FetchType.EAGER)
	private LgUser user;
	
	public long getMemberId() {
		return user.getOid();
	}
	
//	public void setMemberId(final long userOid) {
//		this.userOid = userOid;
//	}
	
//	public void deleteMember(final long userOid) //only one member (it's reference from table)
	
}
