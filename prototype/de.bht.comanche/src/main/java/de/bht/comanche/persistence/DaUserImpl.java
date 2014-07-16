package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;

public class DaUserImpl extends DaGenericImpl<LgUser> implements DaUser {
	public DaUserImpl(Pool<LgUser> pool) {
		super(LgUser.class, pool);
	}
	
	/** 
	 * FIXME Attention: Change id declaration in DbObject
	 * @throws ArgumentTypeException 
	 * @throws ArgumentCountException 
	 * @throws NoQueryClassException 
	 * @throws NoPersistentClassException 
	 */
	@Override
	public List<LgUser> findByName(String name) throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException {
		return findByField("name", name);

	}
	public LgUser getDummy() {
		LgUser user = new LgUser();
		user.setOid(123456789);
		user.setName("Tom Sawyer");
		user.setPassword("letmeinplease");
		user.setEmail("tom@sawyer.com");
		user.setTel("00123456");
		return user;
	}

}
