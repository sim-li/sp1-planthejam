package de.bht.comanche.logic;

import static multex.MultexUtil.create;

import java.util.List;

import de.bht.comanche.persistence.DaApplication;
import de.bht.comanche.persistence.DaPool;
/**
 * Represents the operations available to a user account before 
 * login.
 * 
 * 
 * @author Simon Lischka
 *
 */
public class LgSession {
	
	private final DaApplication application;
	private final DaPool pool;
	private LgUser user;

	public LgSession() {
		this.application = new DaApplication();
		this.pool = this.application.getPool();
		this.user = null;
	}
	
	public LgUser startFor(final String userName) {
		// throw exc when user not found
		this.user = this.pool.findOneByKey(LgUser.class, "NAME", userName); 
		return this.user;
	}
	
	public LgUser register(final LgUser i_user) { // Throw exception when DB error
		// or failure when user already set in class
		this.pool.insert(i_user);
		this.user = i_user;
		return this.user;
	}
	
	public LgUser login(final LgUser i_user) {
		//throw failure when user already set in class
		this.user = this.pool.findOneByKey(LgUser.class, "NAME", i_user.getName());
		if (this.user == null) {
			throw create(LgNoUserWithThisNameExc.class, i_user.getName());
		}
		if (!i_user.passwordMatchWith(this.user)) {
			throw create(LgWrongPasswordExc.class, i_user.getName());
		}
		return this.user;
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
	
	
	/*
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	public DaApplication getApplication() {
		return application;
	}

	protected DaPool getPool() {
		return this.pool;
	}
	
	public LgUser getUser() {
		return this.user;
	}
	
	public void setUser(final LgUser user) {
		this.user = user;
	}
	
}
