package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaPoolImpl.DaNotFoundExc;

public class DaUserImpl extends DaGenericImpl<LgUser> implements DaUser {
	public DaUserImpl(DaPool<LgUser> pool) {
		super(LgUser.class, pool);
	}
	
	@Override
	public List<LgUser> findByName(String name) throws DaNotFoundExc {
		return findByField("name", name);
	}
}
