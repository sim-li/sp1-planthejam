package webTest.dataConnection;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import webTest.entity.Group;
import webTest.exception.DatabaseException;

public class GroupDAO extends BaseDAO{
	
	public GroupDAO(EntityManager em){
		super(em);
	}
	
	public void createGroup(Group group) throws DatabaseException {
		em.getTransaction().begin();
		boolean ok = false;
		try{
			em.persist(group);
			em.getTransaction().commit();
			ok = true;
		}catch(EntityExistsException e){
			throw new DatabaseException("Group existed!");
		}catch(TransactionRequiredException t){
			throw new DatabaseException("can not connect with database! cased :"+ t.getMessage());
		}catch(IllegalArgumentException i){
			throw new DatabaseException("uncompatible Type. can not add object to database!");
		}finally{
			if(ok)
				em.close();
		}
	}
	
	public void deleteGroup(Group group) throws DatabaseException{
		em.getTransaction().begin();
		boolean ok = false;
		try{
		em.remove(group);
		em.getTransaction().commit();
		ok = true;
		} catch (IllegalArgumentException i){
			throw new DatabaseException("can not delete this group from database");
		} catch(TransactionRequiredException t){
			throw new DatabaseException(" there is no transaction");
		}finally{
			if(ok)
				em.close();
		}
	}
	
	public void updateGroup(Group group) throws DatabaseException{
		em.getTransaction().begin();
		boolean ok = false;
		try {
			em.merge(group);
			em.getTransaction().commit();
			ok = true;
		} catch (IllegalArgumentException i) {
			throw new DatabaseException("can not update this group to database");
		} catch (TransactionRequiredException t ){
			throw new DatabaseException("there is no transaction");
		} finally {
			if(ok)
					em.close();
		}			
	}
	public Group getGroupByID(long id) {
		em.getTransaction().begin();
		Group group = em.find(Group.class, id);
		em.getTransaction().commit();
		return group;
	}

	@SuppressWarnings("unchecked")
	public List<Group> getAllGroups() {
		Query query = em.createQuery("from Group g");
		List<Group> groups = query.getResultList();
		return groups;
	}

	@SuppressWarnings("unchecked")
	public Group getGroupByGroupname(String groupname) {
		Query query = em.createQuery(
				"from Group g where g.name like :groupname", Group.class);
		query.setParameter("groupname", groupname);

		List<Group> result = query.getResultList();
		if (result.size() == 0)
			return null;
		else
			return result.get(0);
	}
}
