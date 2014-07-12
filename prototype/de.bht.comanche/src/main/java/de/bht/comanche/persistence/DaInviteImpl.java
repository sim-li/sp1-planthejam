package de.bht.comanche.persistence;

import de.bht.comanche.logic.LgInvite;

public class DaInviteImpl extends DaGenericImpl<LgInvite> implements DaInvite {
	public DaInviteImpl(Pool<LgInvite> pool) {
		super(LgInvite.class, pool);
	}
}
