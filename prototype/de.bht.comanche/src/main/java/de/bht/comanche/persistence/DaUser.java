package de.bht.comanche.persistence;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import javassist.NotFoundException;
import de.bht.comanche.logic.LgUser;

public interface DaUser {
	void save(LgUser user) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException ;
	void delete(LgUser user)  throws TransactionRequiredException, IllegalArgumentException;
	LgUser find(long id) throws NotFoundException, NoPersistentClassExc, OidNotFoundExc;
    Collection<LgUser> findByName(String name) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc;
    void beginTransaction();
    void endTransaction(boolean success);
}
