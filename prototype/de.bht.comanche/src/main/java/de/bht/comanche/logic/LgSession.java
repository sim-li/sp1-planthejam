package de.bht.comanche.logic;

import de.bht.comanche.persistence.DaApplication;
import de.bht.comanche.persistence.DaPool;

public class LgSession {
	 private final DaApplication application;
	 private final DaPool pool;
	 
	 public LgSession() {
		 this.application = new DaApplication();
		 this.pool = this.application.getPool();
	 }
	
	 public LgUser registerUser(LgUser user) {
		 try {
			 this.pool.insert(user);
			 return user;
		 } catch (Exception ex) {
//			 throw create()
		 }
		 return null;
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
      
	 /*
	  * Generic business transaction to follow.
	  */
}
