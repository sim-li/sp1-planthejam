package webTest.dataConnection;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import webTest.entity.User;
import webTest.exception.DatabaseException;

public class UserDAO extends BaseDAO{
	
	public UserDAO(EntityManager em) {
		super(em);
	}

	public void createUser(User user) throws DatabaseException {
		em.getTransaction().begin();
		boolean ok = false;
		try{
			em.persist(user);
			em.getTransaction().commit();
			ok = true;
		}catch(EntityExistsException e){
			throw new DatabaseException("User existed!");
		}catch(TransactionRequiredException t){
			throw new DatabaseException("can not connect with database! cased :"+ t.getMessage());
		}catch(IllegalArgumentException i){
			throw new DatabaseException("uncompatible Type. can not add object to database!");
		}finally{
			if(ok)
				em.close();
		}
	}
	
	public void deleteUser(User user) throws DatabaseException{
		em.getTransaction().begin();
		boolean ok = false;
		try{
		em.remove(user);
		em.getTransaction().commit();
		ok = true;
		} catch (IllegalArgumentException i){
			throw new DatabaseException("can not delete this user from database");
		} catch(TransactionRequiredException t){
			throw new DatabaseException(" there is no transaction");
		}finally{
			if(ok)
				em.close();
		}
	}
	
	public void updateUser(User user) throws DatabaseException{
		em.getTransaction().begin();
		boolean ok = false;
		try {
			em.merge(user);
			em.getTransaction().commit();
			ok = true;
		} catch (IllegalArgumentException i) {
			throw new DatabaseException("can not update this user to database");
		} catch (TransactionRequiredException t ){
			throw new DatabaseException("there is no transaction");
		} finally {
			if(ok)
					em.close();
		}			
	}
	
	public User getUserByID(long id) {
		em.getTransaction().begin();
		User user = em.find(User.class, id);
		em.getTransaction().commit();
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		Query query = em.createQuery("from User u");
		List<User> users = query.getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {
		Query query = em.createQuery(
				"from User u where u.username like :username", User.class);
		query.setParameter("username", username);

		List<User> result = query.getResultList();
		if (result.size() == 0)
			return null;
		else
			return result.get(0);
	}
}
