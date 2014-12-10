//package de.bht.comanche.logic;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import de.bht.comanche.logic.LgInvite;
//import de.bht.comanche.logic.LgUser;
//import de.bht.comanche.persistence.DaObject;
//import de.bht.comanche.persistence.DaPool;
//
///**
// * @author Sebastian Dass&eacute;
// *
// */
////@Ignore
//@SuppressWarnings("unqualified-field-access")
//public class LgInviteTest {
//	
//	// TODO we need some kind of mocked JPA to run save and delete tests
//	
//	private static final long INVITE_OID = 123;
//	
//	private DaPool pool;
//	private LgInvite anInvite;
//	private LgUser alice; 
//	
//	@Before
//	public void setUp() {
//		pool = new MockPool();
//		alice = new LgUser().setName("Alice");
//		alice.attach(pool);
//		anInvite = new LgInvite(INVITE_OID);
//	}
//
//	@Test
//	public void createEmptyInvite() {
//		final LgInvite invite = new LgInvite();
//		assertTrue(invite != null);
//	}
//	
//	@Test
//	public void createInviteWithOid() {
//		final LgInvite invite = new LgInvite(INVITE_OID);
//		assertEquals(INVITE_OID, invite.getOid());
//	}
//	
//	@Test
//	public void saveInviteWithoutUser() {
//		anInvite.attach(pool);
//		final LgInvite actual = anInvite.save();
//		assertEquals(anInvite, actual);
//		assertTrue(pool.contains(anInvite));
//	}
//	
//	@Test
//	public void saveInviteWithUser() {
//		final LgInvite savedInvite = alice.save(anInvite);
//		assertEquals(anInvite, savedInvite);
//		assertTrue(pool.contains(anInvite));
//		assertEquals(alice, savedInvite.getUser());
//		
//		//---- the invite is only added into the db, the user's actual list is not updated (?) 
////		assertTrue(alice.getInvites().contains(anInvite));
////		assertEquals(anInvite, alice.getInvite(INVITE_OID));
//	}
//	
//	@Test
//	public void deleteUnsavedInviteFromPoolThrowsException() {
//		anInvite.setUser(alice);
//		try {
//			anInvite.delete();
//			fail("delete LgInvite from an empty pool should throw an Exception");
//		} catch (Exception expected) {
////			// TODO better throw a dedicated multex.Exc
////			assertEquals(expected.getClass(), multex.Exc.class);
//		}
//	}
//	
////	@Test
////	public void saveAndDeleteInvite() {
////		anInvite.setUser(alice);
////		final LgInvite savedInvite = alice.save(anInvite);
////		assertTrue(pool.contains(anInvite));
////		
////		//---- the invite is only added into the db, the user's actual list is not updated (?) 
//////		assertTrue(alice.getInvites().contains(anInvite));
//////		assertEquals(anInvite, alice.getInvite(INVITE_OID));
////		
////		alice.deleteInvite(INVITE_OID);
////		assertFalse(pool.contains(anInvite));
////
////		//---- the invite is only added into the db, the user's actual list is not updated (?) 
//////		assertTrue(!alice.getInvites().contains(anInvite));
//////		assertNull(alice.getInvite(INVITE_OID));
////	}
//	
////	@Test
////	public void deletingSomeoneElsesInviteHasNoEffect() {
////		anInvite.setUser(alice);
////		alice.save(anInvite);
////		assertTrue(pool.contains(anInvite));
////		final LgUser bob = new LgUser().setName("Bob");
////		bob.attach(pool);
////		bob.deleteInvite(INVITE_OID);
////		assertTrue(pool.contains(anInvite));
////	}
//	
//	
//	private static final class MockPool implements DaPool {
//		private final List<DaObject> data;
//		
//		public MockPool() {
//			data = new ArrayList<DaObject>();
//		}
//		
//		public void insert(final DaObject io_object) {
//			data.add(io_object);
//		}
//		
//		public <E extends DaObject> List<E> findManyByTwoKeys(Class<E> persistentClass, String firstKeyFieldName,
//				Object firstKey, String secondKeyFieldName, Object secondKey) {
//			return null;
//		}
//		
//		@SuppressWarnings("unchecked")
//		public <E extends DaObject> E save(DaObject io_object) {
//			io_object.attach(this);
//			data.add(io_object);
//			return (E) io_object;
//		}
//		
//		public boolean delete(final DaObject io_object) {
//			return data.remove(io_object);
//		}
//		
//		public boolean contains(final DaObject io_object) {
//			return data.contains(io_object);
//		}
//		
//		public <E extends DaObject> E find(Class<E> persistentClass, long oid) { throw new RuntimeException("not implemented"); }
//		public <E extends DaObject> List<E> findAll(Class<E> persistentClass) { throw new RuntimeException("not implemented"); }
//		public <E extends DaObject> E findOneByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue) { throw new RuntimeException("not implemented"); }
//		public <E extends DaObject> List<E> findManyByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue) { throw new RuntimeException("not implemented"); }
//		public <E extends DaObject> List<E> findManyByQuery(Class<E> resultClass, Class<?> queryClass, String queryString, Object[] args) { throw new RuntimeException("not implemented"); }
//		public <E extends DaObject> E findOneByTwoKeys (Class<E> persistentClass, String firstKeyFieldName, Object firstKey, String secondKeyFieldName, Object secondKey) { 
//			throw new RuntimeException("not implemented"); 
//		}
//	}
//}
