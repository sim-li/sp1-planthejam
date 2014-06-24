package de.bht.comanche.persistence;

import java.util.List;

public interface Pool {
	public boolean save(DbObject io_object);
	public boolean delete(DbObject io_object);
	public DbObject find(Class<DbObject> i_persistentClass, Integer i_oid) throws NoPersistentClassExc, OidNotFoundExc;
	public List<DbObject> findAll(Class<DbObject> i_persistentClass) throws NoPersistentClassExc;
	public List<DbObject> findManyByQuery(Class<DbObject> i_resultClass, String i_queryString, Object[] i_args) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc;
}
