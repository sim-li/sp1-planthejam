package de.bht.comanche.persistence;

import java.util.Collection;

import de.bht.comanche.logic.LgUser;
import javassist.NotFoundException;

public interface DaUser {
	// call from restservice
	// sandwiching:
	// save:
	// 		begintransactino
    //		user.save
	//  	endtransaction
	void save(LgUser user); // insert or update
    void delete(LgUser user);
    LgUser find(long id) throws NotFoundException;
    Collection<LgUser> findAll();
    Collection<LgUser> findByName(String name);
}
