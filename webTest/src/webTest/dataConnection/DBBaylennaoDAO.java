package webTest.dataConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/* class DBBaylennaoDAO la class dung de ket noi voi 
 * datenbank co persistentunit-name(duoc khai bao trong
 * persistent.xml) la  "baylennao".
 * 
 * trong day se chua tat ca cac DAO cua moi table rieng le,
 * nhung DAO nay dung de ket noi voi cac bang rieng le voi muc dich 
 * xu ly va chinh sua cac table rieng.
 * 
 * class nay sau nay se dc su dung 1 objekt duy nhat cho moi servlet
 * va co the ket noi voi tat ca cac bang rieng le ma ko can khai bao 
 * nhieu DAO objekt rieng le.
 * 
 * voi moi DAO khai bao them se dc viet them methode getTenbangDAO()
 * vao trong class.
 */
public class DBBaylennaoDAO {
	
	@PersistenceUnit(unitName="baylennao")
	private final EntityManagerFactory emf;
	private final EntityManager em;
	
	public DBBaylennaoDAO() {
		this.emf = Persistence.createEntityManagerFactory("baylennao");
		this.em = emf.createEntityManager();
	}

	public UserDAO getUserDAO() {
		return new UserDAO(em);
	}
	
	public GroupDAO getGroupDAO() {
		return new GroupDAO(em);
	}
}
