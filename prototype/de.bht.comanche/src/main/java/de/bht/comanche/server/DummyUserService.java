//package de.bht.comanche.server;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.NotAuthorizedException;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import de.bht.comanche.logic.LgUser;
//import de.bht.comanche.persistence.DaFactory;
//import de.bht.comanche.persistence.DaUser;
//import de.bht.comanche.persistence.JpaDaFactory;
//
//@Path("/service")
//public class DummyUserService { 
//	//Begin/End Transaction
//    @GET
//    @Path("/single-user")
//	@Produces(MediaType.APPLICATION_JSON)
//	public ResponseObject produceJSON() {
////    	LgUser lgUser1 = new LgUser();
////    	lgUser1.setName("asdasdcas");
////		lgUser1.setEmail("not a valid email");
//		
////    	ResponseObject result = new Transaction() {
////			@Override
////			public LgUser executeWithThrows() throws Exception {
////				LgUser lgUser1 = new LgUser();
////				lgUser1.setEmail("not a valid email"); // will throw NoValidEmailExc 
////				DaFactory jpaDaFactory = new JpaDaFactory();
////				DaUser daUser = jpaDaFactory.getDaUser();
////				daUser.save(lgUser1);
////				return lgUser1;
////			}
////		}.execute();
//    	
//		DemoFactory dm = new DemoFactory();
//		return dm.getTransactionObject();
//	}
//    
//    
//    
//    // 
//    //  > Template-Method-Pattern
//    //  
//    //  Abstract class: Transaktionsbegleitung in execute Methode. TRY, FINALLY.
//    //  Abstract class: public E execute() calls executeWithThrows, 
//    // 					-> Durchreichung von Resultat: executeWithThrows -> execute
//    //  Concrete class: Implements executeWithThrows
//    //  
//    //	LgUser user = new Transaction<E>(..) {
//    //			
//    // 		public E executeWithThrows() throws Exception {
//    //			final DaUser daUser = JpaDaFactory.getDaUser();
//    //			final LgUser lgUser1 = daUser.create();
//    // 			final LgUser lgUser2 = daUser.findById(2);
//    // 			daUser.save(dmUser);
//    //			...
//    //			return user;
//    //  }.execute();
//}
//
