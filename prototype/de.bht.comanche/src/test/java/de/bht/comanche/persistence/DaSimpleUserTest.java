//package de.bht.comanche.persistence;
//
//import org.junit.Test;
//
//import de.bht.comanche.logic.LgSession;
//import de.bht.comanche.logic.LgTransaction;
//import de.bht.comanche.logic.LgUser;
//
//public class DaSimpleUserTest {
//
//	@Test
//	public void deleteBob() {
//		final LgUser user = new LgUser();
//		user.setName("Bob");
//		user.setEmail("bobby@surfersclub.de");
//		
//		
//		final LgSession session = new LgSession();
//		new LgTransaction<LgUser>(session) {
//			@Override
//			public LgUser execute() throws multex.Exc {
//				try {
//					session.deleteUser(user);
//				} catch (Exception ex) {
////					throw create(DeleteFailure.class, ex,
////							createTimeStamp(),
////							userFromClient.getOid(), 
////							userFromClient.getName());
//				}
//				return null;
//			}
//		}.getResult();
//	}
//	
//}
