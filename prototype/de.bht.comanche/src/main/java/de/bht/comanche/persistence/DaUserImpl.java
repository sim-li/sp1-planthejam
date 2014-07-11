package de.bht.comanche.persistence;

import java.util.Collection;

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
	
	public LgUser getDummy() {
		LgUser user = new LgUser();
		user.setOid(123456789);
		user.setName("Tom Sawyer");
		user.setPassword("letmeinplease");
		user.setEmail("tom@sawyer.com");
		user.setTelephone("00123456");
		return user;
	}
}
