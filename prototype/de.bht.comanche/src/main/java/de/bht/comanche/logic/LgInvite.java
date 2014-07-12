package de.bht.comanche.logic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
//@Table(name = "Lg_Invite")
public class LgInvite extends DbObject{
	
	private static final long serialVersionUID = 1L;
	private boolean isHost;
	private boolean isIgnored;
	
	@ManyToOne
//	@JoinColumn(name="USER_ID")
	private LgUser user;
	@Id
//	@SequenceGenerator(name = "idGeneratorSeq", sequenceName = "idSequence")
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "idGeneratorSeq")
	private long invite_id;

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
