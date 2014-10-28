package de.bht.comanche.logic;

import de.bht.comanche.persistence.DaApplication;
import de.bht.comanche.persistence.DaPool;

public class LgSession {
	 private final DaApplication application;
	 private final DaPool pool;
	 
	 public LgSession() throws multex.Failure {
		 try {
			 this.application = new DaApplication();
			 this.pool = this.application.getPool();
		 } catch (Exception ex) {
			 throw new multex.Failure("Failure initializing Session", ex);
		 }
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
