package de.bht.comanche.persistence;

import java.util.List;

public class PoolImpl implements Pool {

	@Override
	public boolean save(DbObject io_object) {
		// TODO Auto-generated method stub
		// TODO: Frage: DbObject wieder ersetzen durch DaGeneric<E>, dann folgendes (?):
		// io_object.save();
		return false;
	}

	@Override
	public boolean delete(DbObject io_object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DbObject find(Class<?> i_persistentClass, Integer i_oid)
			throws NoPersistentClassExc, OidNotFoundExc {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DbObject> findAll(Class<?> i_persistentClass)
			throws NoPersistentClassExc {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DbObject> findManyByQuery(Class<?> i_resultClass,
			Class<?> i_queryClass, Object[] i_args)
			throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc,
			ArgumentTypeExc {
		// TODO Auto-generated method stub
		return null;
	}

}
