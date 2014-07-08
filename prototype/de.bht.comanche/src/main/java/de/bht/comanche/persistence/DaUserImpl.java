package de.bht.comanche.persistence;

import java.util.ArrayList;
import java.util.Collection;

import de.bht.comanche.logic.LgUser;

public class DaUserImpl extends DaGenericImpl<LgUser> implements DaUser {
	public DaUserImpl(Pool pool) {
		super(LgUser.class, pool);
	}

	
	/* (non-Javadoc)
	 * @see de.bht.comanche.persistence.DaUser#findByName(java.lang.String)
	 * Attention: Returns Dummy user. Change id declaration in DbObject
	 */
	@Override
	public Collection<LgUser> findByName(String name) {
		//findbyField...
		Collection <LgUser> c = new ArrayList<LgUser>();
		LgUser user1 = new LgUser();
		user1.setName("Zakoni");
		user1.setEmail("ralf@ralf.de");
		user1.setTelephone("030333333");
		user1.setPassword("password");
		user1.id = 323132323232L;
		c.add(new LgUser());
		return c;
	}

}
