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
		return this.user;
	}
	
	public LgMember setUser(final LgUser user) {
		this.user = user;
		return this;
	}
	
	public LgMember setGroup(final LgGroup group) {
		this.group = group;
		return this;
	}

	@Override
	public String toString() {
		return String.format("LgMember [group=%s, user=%s, oid=%s, pool=%s]",
				group, user, oid, pool);
	}
}
