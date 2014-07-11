package de.bht.comanche.logic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
//@Table(name = "Lg_Invite")
public class LgInvite extends DbObject{
	
	private static final long serialVersionUID = 1L;
	private boolean isHost;
	private boolean isIgnored;
	
	@ManyToOne
	private LgUser user;
	@Id
	private long oid;

	public boolean isHost() {
		return isHost;
	}

	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}

	public boolean isIgnored() {
		return isIgnored;
	}

	public void setIgnored(boolean isIgnored) {
		this.isIgnored = isIgnored;
	}

	public LgUser getUser() {
		return user;
	}

	public void setUser(LgUser user) {
		this.user = user;
	}
}
