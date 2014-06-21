package de.bht.comanche.persistence;

import java.util.Collection;

import de.bht.comanche.model.DmUser;
import javassist.NotFoundException;

public interface DaUser {
	void save(DmUser user); // insert or update
    void delete(DmUser user);
    DmUser find(long id) throws NotFoundException;
    Collection<DmUser> findAll();
    Collection<DmUser> findByName(String name);
}
