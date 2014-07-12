package de.bht.comanche.persistence;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.TransactionWithStackTrace;
import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

public class PersistenceTest {
	
	private DaFactory daFactory;
	
	@Before public void setUp(){
		daFactory = new JpaDaFactory();
	}
	
	@Test public void saveInviteTest() throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException, EntityExistsException, TransactionRequiredException, IllegalArgumentException, OidNotFoundException, NotFoundException {
		final DaUser daUser = daFactory.getDaUser();
		final DaInvite daInvite = daFactory.getDaInvite();
		final Pool pool = daUser.getPool();
		daInvite.setPool(pool);
		/*
		 * SAVE DEMOUSER (JUST IN CASE), SO THAT JPA PROVIDES ID.
		 */
		DbObject result = new TransactionWithStackTrace<LgUser>(pool) {
			public LgUser executeWithThrows() throws Exception {
				System.out.println(">>> SAVE DEMOUSER (JUST IN CASE), SO THAT JPA PROVIDES ID.");
				LgUser lgUser = new LgUser();
				lgUser.setName("Ralf");
				lgUser.setEmail("simon@a-studios.org");
				lgUser.setPassword("myPwIsEasy");
				lgUser.setTel("030-3223939");
				daUser.save(lgUser);
				/*
				 * RETRIEVE FIRST USER IN DB.
				 */
				System.out.println(">>> RETRIEVE FIRST USER IN DB.");
				LgUser lgUserWithId = daUser.find(0);
				/*
				 * CREATE & SAVE INVITES 
				 */
				System.out.println("> CREATE & SAVE INVITES");
				LgInvite lgInvite = new LgInvite();
				LgInvite lgInvite2 = new LgInvite();
				lgInvite.setUser(lgUser);
				lgInvite2.setUser(lgUser);
				lgInvite2.setHost(true);
				daInvite.save(lgInvite);
				daInvite.save(lgInvite2);
				/*
				 * RETRIEVE INVITES FROM DB
				 */
				System.out.println(">>> RETRIEVE INVITES FROM DB");
				lgInvite = daInvite.find(0);
				lgInvite2 = daInvite.find(1);
				/*
				 * SET INVITES IN USER & SAVE
				 */
				System.out.println(">>> SET INVITES IN USER & SAVE");
				lgUser.addInvites(lgInvite);
				lgUser.addInvites(lgInvite2);
				daUser.save(lgUser);
				return lgUser;
			}
		}.execute();
	}
	
	@After public void tearDown() {
		// TODO clean up the database when the tests are done 
	}
	
}
