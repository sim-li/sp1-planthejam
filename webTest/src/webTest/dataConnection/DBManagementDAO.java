package webTest.dataConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public class DBManagementDAO {
	
	@PersistenceUnit(unitName="management")
	private final EntityManagerFactory emf;
	private final EntityManager em;
	
	public DBManagementDAO() {
		this.emf = Persistence.createEntityManagerFactory("management");
		this.em = emf.createEntityManager();
	}

	public UserDAO getUserDAO() {
		return new UserDAO(em);
	}
	
	public GroupDAO getGroupDAO() {
		return new GroupDAO(em);
	}
}
