package de.bht.comanche.logic;

import static multex.MultexUtil.create;
import de.bht.comanche.persistence.DaApplication;
import de.bht.comanche.persistence.DaObject;
import de.bht.comanche.persistence.DaPool;

public class LgSession {
	private final DaApplication application;
	private final DaPool pool;
	private LgUser user;

	public LgSession() {
		application = new DaApplication();
		pool = application.getPool();
		user = null;
	}

	public void registerUser(LgUser i_user) { // Throw exception when DB error
		// or failure when user already set in class
		pool.insert(i_user);
		this.user = i_user;
	}

	public void login(LgUser i_user) {
		//throw failure when user already set in class
		this.user = pool.findOneByKey(LgUser.class, "NAME", i_user.getName());
		if (this.user == null) {
			throw create(LgNoUserWithThisNameExc.class, i_user.getName());
		}
		if (!i_user.passwordMatchWith(this.user)) {
			throw create(LgWrongPasswordExc.class, i_user.getName());
		}
	}
	/**
	 * No user with name "{0}" found in the database
	 */
	@SuppressWarnings("serial")
	public static final class LgNoUserWithThisNameExc extends multex.Exc {
	}
	/**
	 * Wrong password for user with name "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class LgWrongPasswordExc extends multex.Exc {
	}

	public void Object(DaObject object) {
		object.setPool(pool);
	}

	public void beginTransaction() {
		application.beginTransaction();
	}

	public void endTransaction(boolean success) {
		application.endTransaction(success);
	}

	public void startFor(String userName) {
		// throw exc when user not found
		user = pool.findOneByKey(LgUser.class, "NAME", userName); 
		System.out.println("Started Session for " + user.getName());
	}

	/**
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	protected DaPool getPool() {
		return pool;
	}


	public LgUser getUser() { // throw exc when user not present
		return user;
	}
}
