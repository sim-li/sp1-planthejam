package de.bht.comanche.logic;

import static multex.MultexUtil.create;

import java.util.List;

import de.bht.comanche.persistence.DaApplication;
import de.bht.comanche.persistence.DaObject;
import de.bht.comanche.persistence.DaPool;

/**
 * Provides basic account functionality and additional operations
 * for <code>LgUser</code> objects. Couples REST-authentication logic with 
 * the DB layer and provides the entry point to the object hierarchy.
 * 
 * <p>The class wraps a single <code>LgUser</code> instance and binds it
 * to the pool object of the application. 
 *  
 * <p>The user instance can be assigned by simple user name search or
 * by search with password check. The class does not prevent 
 * unauthorized access to a <code>LgUser</code> instance with simple search.
 * Existing authentication must be validated externally.
 * 
 * <p>Operations accessing data available only after login should not
 * be implemented here but directly in the <code>LgUser</code> class
 * and its children. 
 *  
 * @author Simon Lischka
 *
 */
public class LgSession extends DaObject {
	
	/**
	 * Application instance for access to pool.
	 * 
	 * <p>For performance reasons, only one <code>DaPool</code> instance is initialized for the entire
	 * application.
	 */
	private final DaApplication application;
	
	/**
	 * Shortcut reference to <pool>pool</pool> extracted from <code>application</code>
	 */
	private final DaPool pool;
	
	/**
	 * <code>User</code> initialized for data access 
	 */
	private LgUser user;

	/**
	 * Name column identifier in user table
	 */
	private final String NAME_FIELD = "NAME";
	
	/**
	 * Constructor, initializes application. The <code>pool</code> is
	 * guaranteed to be initialized at that point 
	 */
	public LgSession() {
		// @TODO Throw exception when failure initializing pool
		this.application = new DaApplication();
		this.pool = this.application.getPool();
		this.user = null;
	}
	
	/**
	 * Starts session for <code>user</code> by user name search.
	 * Does not validate password. 
	 * 
	 * @param userName Name of a logged in<code>user</code>. 
	 * @return Persisted <code>LgUser</code> instance with id.
	 */
	public LgUser startFor(final String userName) {
		// @TODO Throw exception when user not found
		this.user = this.pool.findOneByKey(LgUser.class, NAME_FIELD, userName); 
		return this.user;
	}
	
	/**
	 * Saves a new <code>LgUser</code> instance. 
	 * 
	 * <p>Expects user name not to be previously registered in the 
	 * system.
	 * 
	 * @param user <code>User</code> to be registered.
	 * @return Persisted <code>LgUser</code> instance with id.
	 */
	public LgUser register(final LgUser user) { 
		// @TODO Throw exception when user name present in DB
		// @TODO Throw exception when user already set in class
		this.pool.insert(user);
		this.user = user;
		return this.user;
	}
	
	/**
	 * Validates name and password of the <code>user</code> instance. 
	 * 
	 * If a matching instance is found in the system, it is set in the <code>session</code>. 
	 * 
	 * @param user <code>User</code> to be logged in.
	 * @return Persisted <code>LgUser</code> instance with id.
	 */
	public LgUser login(final LgUser user) {
		// @TODO Throw failure when user already set in class
		// @TODO Throw exception when user doesn't contain name / password
		this.user = this.pool.findOneByKey(LgUser.class, NAME_FIELD, user.getName());
		if (this.user == null) {
			throw create(LgNoUserWithThisNameExc.class, user.getName());
		}
		if (!user.passwordMatchWith(this.user)) {
			throw create(LgWrongPasswordExc.class, user.getName());
		}
		return this.user;
	}
	/**
	 * No user with name "{0}" found in the database.
	 */
	@SuppressWarnings("serial")
	public static final class LgNoUserWithThisNameExc extends multex.Exc {
	}
	/**
	 * The email or password you entered is incorrect. 
	 */
	@SuppressWarnings("serial")
	public static final class LgWrongPasswordExc extends multex.Exc {
	}

	/**
	 * Saves or updates the <code>user</code> instance. An update
	 * is executed when no id is present.
	 * 
	 * @param user <code>User</code> to be saved / updated.
	 * @return Persisted <code>LgUser</code> instance with id.
	 */
	public LgUser save(final LgUser user) {
		return user.attach(this.pool).save();
	}	
	
	/**
	 * Returns all users registered in the system.
	 * 
	 * @return List of users.
	 */
	public List<LgUser> getAllUsers() {
		List<LgUser> allUsers = this.pool.findAll(LgUser.class);
		if (this.user!= null) {
			allUsers.remove(this.user);
		}
		return allUsers;
	}

	public DaApplication getApplication() {
		return this.application;
	}

	protected DaPool getPool() {
		return this.pool;
	}

	public LgUser getUser() {
		return this.user;
	}
	
}
