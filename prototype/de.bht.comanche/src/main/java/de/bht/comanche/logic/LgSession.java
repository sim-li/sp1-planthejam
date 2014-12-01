package de.bht.comanche.logic;

import static multex.MultexUtil.create;

import java.util.List;

import de.bht.comanche.persistence.DaApplication;
import de.bht.comanche.persistence.DaPool;

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
	
	public LgUser findByName(final String userName) {
		// throw exc when user not found
		return this.pool.findOneByKey(LgUser.class, "NAME", userName);
	}
	
	public LgUser save(final LgUser user) {
		final LgUser o_user = user.attach(getPool()).save(); //can throw exception
		this.setUser(o_user);
		return o_user; 
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

	public void beginTransaction() {
		this.application.beginTransaction();
	}

	public void endTransaction(final boolean success) {
		this.application.endTransaction(success);
	}


	/*
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */

	protected DaPool getPool() {
		return this.pool;
	}
	
	public LgUser getUser() {
		return this.user;
	}
	
	public void setUser(final LgUser user) {
		this.user = user;
	}
	
	public List<LgUser> getAllUsers() {
//		return pool.findManyByQuery(LgUser.class, null, "SELECT * FROM " + LgUser.class.getSimpleName(), new Object[]{  });
		return this.pool.findAll(LgUser.class);
	}
}
