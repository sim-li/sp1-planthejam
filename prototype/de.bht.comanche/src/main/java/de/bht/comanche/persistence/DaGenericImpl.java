package de.bht.comanche.persistence;

import java.util.Collection;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.LgUser;

public class DaGenericImpl<E> implements DaGeneric<E> {
	
	private Class<E> type;
	private Pool<E> pool;
	
	public DaGenericImpl(Class<E> type, Pool<E> pool) {
		this.type = type;
		this.pool = pool;
	}

	public static void main (String[] args) {
//		DaGenericImpl<LgUser> da = new DaGenericImpl<LgUser>(LgUser.class, null);
//		System.out.println(da.type.getSimpleName());
	}
	
	@Override
	public void save(E entity) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException {
		pool.save(entity);
		
	}

	@Override
	public void delete(E entity) throws TransactionRequiredException, IllegalArgumentException {
		pool.delete(entity);
	}

	@Override
	public E find(long id) throws NotFoundException, NoPersistentClassExc, OidNotFoundExc {
		return pool.find(type, id);
		
	}

	@Override
	public Collection<E> findAll() throws NoPersistentClassExc {
		return pool.findAll(type);
	}

	@Override
	public Collection<E> findByField(String fieldName, Object fieldValue) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc { 
		//SELECT c.capital.name FROM Country AS c WHERE c.name = :name
		final String OBJECT_NAME = type.getSimpleName();
		String [] args = {
				fieldName,
				OBJECT_NAME,
				(String) fieldValue
		};
		return pool.findManyByQuery(type, "SELECT c FROM %2$s AS c WHERE c.%1$s LIKE '%3$s'", args);
	}

	@Override
	public Collection<E> findByWhere(String whereClause, Object... args) {
//		pool.findManyByQuery(type, "SELECT c.", i_args)
		return null;
	}

	@Override
	public Collection<E> findByExample(E example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beginTransaction() {
		pool.beginTransaction();
	}

	@Override
	public void endTransaction(boolean success) {
		pool.endTransaction(success);
	}
	
	public Pool<E> getPool() {
		return this.pool;
	}

}
