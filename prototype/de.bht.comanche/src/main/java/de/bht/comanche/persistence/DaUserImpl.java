package de.bht.comanche.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javassist.NotFoundException;
import de.bht.comanche.logic.DbObject;
import de.bht.comanche.logic.LgUser;

public class DaUserImpl extends DaGenericImpl<LgUser> implements DaUser {
	public DaUserImpl(Pool<LgUser> pool) {
		super(LgUser.class, pool);
	}
	
	/** 
	 * FIXME Attention: Change id declaration in DbObject
	 * @throws ArgumentTypeExc 
	 * @throws ArgumentCountExc 
	 * @throws NoQueryClassExc 
	 * @throws NoPersistentClassExc 
	 */
	@Override
	public Collection<LgUser> findByName(String name) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc {
		return findByField("name", name);
	}
}
