package webTest.dataConnection;

import java.util.List;

import javax.persistence.Query;

import webTest.entity.Group;

public class GroupDAO extends BaseDAO{
	
	public GroupDAO(){
		super();
	}
	
	public void createGroup(Group group) {
		em.getTransaction().begin();
		em.persist(group);
		em.getTransaction().commit();
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
