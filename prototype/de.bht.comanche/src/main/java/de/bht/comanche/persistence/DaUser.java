package de.bht.comanche.persistence;

import java.util.Collection;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;
import javassist.NotFoundException;

public interface DaUser {
	void save(LgUser user); // insert or update
	void delete(LgUser user);
	LgUser find(long id) throws NotFoundException;
    Collection<LgUser> findByName(String name);
}
