package webTest.dataConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public abstract class BaseDAO {
	@PersistenceUnit(unitName = "login")
	protected EntityManagerFactory emf;
	protected EntityManager em;
	
	public BaseDAO(){
		emf = Persistence.createEntityManagerFactory("login");
		em = emf.createEntityManager();
	}
}
