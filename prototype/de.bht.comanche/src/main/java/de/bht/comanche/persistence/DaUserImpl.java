package de.bht.comanche.persistence;

import java.util.ArrayList;
import java.util.Collection;

import de.bht.comanche.logic.LgUser;

public class DaUserImpl extends DaGenericImpl<LgUser> implements DaUser {
	public DaUserImpl(Pool<LgUser> pool) {
		super(LgUser.class, pool);
	}
	
	/** 
	 * FIXME Attention: Returns Dummy user. Change id declaration in DbObject
	 */
	@Override
	public Collection<LgUser> findByName(String name) {
		//findbyField...
		Collection <LgUser> c = new ArrayList<LgUser>();
		LgUser dummy = new LgUser();
		dummy.setName("Zakoni");
		dummy.setEmail("ralf@ralf.de");
		dummy.setTel("030333333");
		dummy.setPassword("password");
		dummy.oid = 323132323232L;
		c.add(dummy);
		return c;
	}

}
