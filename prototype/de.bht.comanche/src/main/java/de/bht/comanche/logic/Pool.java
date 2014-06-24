package de.bht.comanche.logic;

import java.util.List;

import de.bht.comanche.persistence.ArgumentCountExc;
import de.bht.comanche.persistence.ArgumentTypeExc;
import de.bht.comanche.persistence.DbObject;
import de.bht.comanche.persistence.NoPersistentClassExc;
import de.bht.comanche.persistence.NoQueryClassExc;
import de.bht.comanche.persistence.OidNotFoundExc;

public interface Pool {
	public boolean save(DbObject io_object);
	public boolean delete(DbObject io_object);
	public DbObject find(Class<? extends DbObject> i_persistentClass, Long i_oid) throws NoPersistentClassExc, OidNotFoundExc;
	public List<? extends DbObject> findAll(Class<? extends DbObject> i_persistentClass) throws NoPersistentClassExc;
	public List<? extends DbObject> findManyByQuery(Class<? extends DbObject> i_resultClass, String i_queryString, Object[] i_args) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc;
}
