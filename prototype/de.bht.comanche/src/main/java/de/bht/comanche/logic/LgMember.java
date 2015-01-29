package de.bht.comanche.logic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import de.bht.comanche.persistence.DaObject;

/**
 * This entity class represents a member in a group and serve methods for working with 
 * LgMember object.
 * @author Maxim Novichkov
 *
 */
@Entity
@Table(name = "member")
public class LgMember extends DaObject{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Column for a LgGroup representation as a foreign key in this table. Must not be null.
	 */
	@NotNull
	@ManyToOne
	private LgGroup group;
	
	/**
	 * Column for a LgUser representation as a foreign key in this table. Must not be null.
	 */
	@NotNull
	@OneToOne
	private LgUser user;
	
	/**
	 * Gets LgUser fot this LgMember object.
	 * @return The LgUser object.
	 */
	public LgUser getUser() {
		return this.user;
	}
	
	/**
	 * Sets LgUser for this LgMember.
	 * @param user The LgUser to set.
	 * @return This LgMember.
	 */
	public LgMember setUser(final LgUser user) {
		this.user = user;
		return this;
	}
	
	/**
	 * Sets LgGroup for this LgMember.
	 * @param group The LgGroup to set.
	 * @return This LgMember.
	 */
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
