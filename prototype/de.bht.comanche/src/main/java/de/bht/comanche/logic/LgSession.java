package de.bht.comanche.logic;

import static multex.MultexUtil.create;
import de.bht.comanche.persistence.DaApplication;
import de.bht.comanche.persistence.DaObject;
import de.bht.comanche.persistence.DaPool;

public class LgSession {
	private final DaApplication application;
	private final DaPool pool;

	public LgSession() {
		application = new DaApplication();
		pool = application.getPool();
	}

	public LgUser registerUser(LgUser user) {
		try {
			pool.insert(user);
			return user;
		} catch (Exception ex) {
			//			 throw create()
		}
		return null;
	}

	public boolean deleteUser(long oid) {
		try {
		    final LgUser user = pool.find(LgUser.class, oid);	
			return pool.delete(user);
		} catch (Exception ex) {
			// 	         throw create()
		}
		return false;
	}
	
	/**
	 *   No user with name "{0}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgNoUserWithThisNameExc extends multex.Exc {}
	/**
	 *  Wrong password for user with name "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class LgWrongPasswordExc extends multex.Exc {}

	public LgUser login(LgUser user) {
		// Maybe implement find by object?
		LgUser dbUser = pool.findOneByKey(LgUser.class, "NAME", user.getName());
		if (dbUser == null) {
			throw create(LgNoUserWithThisNameExc.class, user.getName());
		}
		if (!user.passwordMatchWith(dbUser)) {
			throw create(LgWrongPasswordExc.class, user.getName(), dbUser.getName());
		}
		return dbUser; 
	}

	public void Object(DaObject object) {
		object.setPool(pool);
	}
	
	protected DaPool getPool() {
		return pool;
	}

	public void beginTransaction() {
		application.beginTransaction();
	}

	public void endTransaction(boolean success) {
		application.endTransaction(success);
	}
}
