package de.bht.comanche.persistence;

import java.util.ArrayList;
import java.util.Collection;

import javassist.NotFoundException;
import de.bht.comanche.logic.LgUser;

public class DaUserImpl extends DaGenericImpl<LgUser> implements DaUser {
	public DaUserImpl(Pool<LgUser> pool) {
		super(LgUser.class, pool);
	}
	
	public LgUser find(long id) throws NotFoundException, NoPersistentClassExc, OidNotFoundExc {
		return null;
	}
	/** 
	 * FIXME Attention: Returns Dummy user. Change id declaration in DbObject
	 */
	@Override
	public Collection<LgUser> findByName(String name) {
		return new ArrayList<LgUser>();
	}

}
