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
	 * FIXME Attention: Returns Dummy user. Change id declaration in DbObject
	 */
	@Override
	public Collection<LgUser> findByName(String name) {
//		return new ArrayList<LgUser>();
		return createDummyList();
	}
	
	public Collection<LgUser> createDummyList() {
		List<LgUser> list = new ArrayList<LgUser>();
		LgUser user1 = new LgUser();
		user1.setOid(123456789);
		user1.setName("Tom Sawyer");
		user1.setPassword("letmeinplease");
		user1.setEmail("tom@sawyer.com");
		user1.setTel("00123456");
//		LgUser user2 = new LgUser();
//		user2.setOid(987654321);
//		user2.setName("Huckleberry Finn");
//		user2.setPassword("opensesame");
//		user2.setEmail("huck@finn.net");
//		user2.setTel("0309876543");
		list.add(user1);
//		list.add(user2);
		return list;
	}

}
