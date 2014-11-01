package de.bht.comanche.logic;

import static multex.MultexUtil.create;
import java.util.List;
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

	public LgSession register(LgUser i_user) { // Throw exception when DB error
		// or failure when user already set in class
		pool.insert(i_user);
		this.user = i_user;
		return this;
	}
	
	//Parents can save their children through delegation
	public LgUser save(final LgUser user) {
		user.attach(getPool());
		final LgUser o_user = user.save(); //can throw exception
		this.user = o_user;
		return o_user; 
	}

	public LgSession login(LgUser i_user) {
		//throw failure when user already set in class
		this.user = pool.findOneByKey(LgUser.class, "NAME", i_user.getName());
		if (this.user == null) {
			throw create(LgNoUserWithThisNameExc.class, i_user.getName());
		}
		if (!i_user.passwordMatchWith(this.user)) {
			throw create(LgWrongPasswordExc.class, i_user.getName());
		}
		return this;
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
		object.attach(pool);
	}

	public void beginTransaction() {
		application.beginTransaction();
	}

	public void endTransaction(boolean success) {
		application.endTransaction(success);
	}

	public LgSession startFor(String userName) {
		// throw exc when user not found
		user = pool.findOneByKey(LgUser.class, "NAME", userName); 
		return this;
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

	public LgUser getUser() {
		return user;
	}
}
