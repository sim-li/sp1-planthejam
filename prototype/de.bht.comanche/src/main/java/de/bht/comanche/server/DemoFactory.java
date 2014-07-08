package de.bht.comanche.server;

import de.bht.comanche.logic.LgUser;

public class DemoFactory {
	public ResponseObject getTransactionObject() {
		ResponseObject to = new ResponseObject();
		to.setSuccess(true);
		to.addServerMessage("Server msg 1: ERROR CODE 3");
		to.addServerMessage("Server msg 2: ERROR CODE 13");
		to.addServerMessage("Server msg 3: ERROR CODE 16");
		LgUser user1 = new LgUser();
		user1.setEmail("ralf@ralf.de");
		user1.setName("Ralf Zakoni");
		user1.setPassword("TheWPWAS");
		user1.setTelephone("030/8828282");
		LgUser user2 = new LgUser();
		user2.setEmail("ralf@ralf.de");
		user2.setName("Ralf Zakoni");
		user2.setPassword("TheWPWAS");
		user2.setTelephone("030/8828282");
		to.addData(user1);
		to.addData(user2);
		return to;
	}
}
