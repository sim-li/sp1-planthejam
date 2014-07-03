package webTest.dataConnection;

import javax.persistence.EntityManager;

public abstract class BaseDAO {
	protected EntityManager em;
	
	public BaseDAO(EntityManager em){
		this.em = em;
	}
}
