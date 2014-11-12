package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;

public class DaUserImpl extends DaGenericImpl<LgUser> implements DaUser {
	public DaUserImpl(DaPool<LgUser> pool) {
		super(LgUser.class, pool);
	}
	
	@Override
	public List<LgUser> findByName(String name) throws DaNoPersistentClassExc {
		return findByField("name", name);
	}
}
