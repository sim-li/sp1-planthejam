package webTest.dataConnection;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import webTest.entity.User;

public class DBconnecter {

	@PersistenceUnit(unitName = "login")
	private EntityManagerFactory emf;
	public EntityManager em;

	public DBconnecter() {
		emf = Persistence.createEntityManagerFactory("login");
		em = emf.createEntityManager();
	}

	public void createUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
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
