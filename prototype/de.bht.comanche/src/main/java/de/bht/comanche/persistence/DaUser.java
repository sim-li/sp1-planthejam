package de.bht.comanche.persistence;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import javassist.NotFoundException;
import de.bht.comanche.logic.LgUser;

public interface DaUser {
	void save(LgUser user) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException ;
	void delete(LgUser user);
	LgUser find(long id) throws NotFoundException;
    Collection<LgUser> findByName(String name) throws NotFoundException;
    void beginTransaction();
    void endTransaction(boolean success);
}
