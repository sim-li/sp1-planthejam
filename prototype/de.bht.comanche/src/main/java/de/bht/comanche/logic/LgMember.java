package de.bht.comanche.logic;


import javax.persistence.Entity;
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
	
	@NotNull
	@OneToOne
	private LgUser user;
	
	public LgUser getUser() {
		return user;
	}
	
	public LgMember setUser(LgUser user) {
		this.user = user;
		return this;
	}
	
	public LgMember setGroup(LgGroup group) {
		this.group = group;
		return this;
	}
}
