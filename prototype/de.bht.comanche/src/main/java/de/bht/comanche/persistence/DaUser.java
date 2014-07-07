package de.bht.comanche.persistence;

import java.util.Collection;

import javassist.NotFoundException;
import de.bht.comanche.logic.LgUser;

public interface DaUser {
	void save(LgUser user); // insert or update
	void delete(LgUser user);
	LgUser find(long id) throws NotFoundException;
    Collection<LgUser> findByName(String name) throws NotFoundException;
}
