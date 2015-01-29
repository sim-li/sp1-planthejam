package de.bht.comanche.persistence;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaHibernateJpaPool.DaFindOneByKeyExc;

public class DeletionTest {
	private LgUser alice;
	private LgUser bob;
	private LgUser carol;
	private TestUtils testUtils = new TestUtils();
	private LgSurvey surveyForEvaluation;

	@Before
	public void buildUp() {
		TestUtils.resetJPADatabase();
		alice = testUtils.registerTestUser("Alice");
		bob = testUtils.registerTestUser("Bob");
		carol = testUtils.registerTestUser("Carol");
		this.surveyForEvaluation = testUtils.saveSurvey(new LgSurvey()
				.addParticipants(bob, carol)
				.setPossibleTimePeriods(
						testUtils.buildTimePeriods(
								"02.12.1982/13:40 -> 02.12.1982/15:40",
								"03.12.1984/13:40 -> 04.12.1986/15:40",
								"05.12.1986/13:45 -> 06.12.1986/15:40"))
				.setDeterminedTimePeriod(
						new LgTimePeriod().setStartTime(new Date()).setEndTime(
								new Date())));
	}

	//PASS
	@Test
	public void deleteSurveyTest() {
		new TestTransaction<Object>("Alice") {
			@Override
			public Object execute() {
				startSession().deleteSurvey(surveyForEvaluation.getOid());
				return null;
			}
		}.getResult();
		
		final List<DaObject> queryResults = new TestTransaction<List<DaObject>>(
				"Alice") {
			final Object[] EMPTY_QUERY = new Object[0];
			public List<DaObject> execute() {
				final LgUser user = startSession();
				final List<DaObject> searchResults = new ArrayList<DaObject>();
				searchResults.addAll(user.findManyByQuery(LgSurvey.class,
						LgSurvey.class, "from LgSurvey", EMPTY_QUERY));
//				searchResults.addAll(user.findManyByQuery(LgInvite.class,
//						LgInvite.class, "from LgInvite", EMPTY_QUERY));
				return searchResults;
			}
		}.getResult();
		assertThat(queryResults).isEmpty();
	}
	
	@Test
	public void deleteUserTest() {
		new TestTransaction<Object>("Alice") {
			@Override
			public Object execute() {
				startSession().deleteThisAccount();
				return null;
			}
		}.getResult();
		final List<DaObject> queryResults = new TestTransaction<List<DaObject>>(
				"Bob") {
			final Object[] EMPTY_QUERY = new Object[0];
			public List<DaObject> execute() {
				final LgUser user = startSession();
				final List<DaObject> searchResults = new ArrayList<DaObject>();
				try {
//				searchResults.addAll(user.findManyByQuery(LgSurvey.class,
//						LgSurvey.class, "from LgSurvey", EMPTY_QUERY));
//				searchResults.addAll(user.findManyByQuery(LgInvite.class,
//						LgInvite.class, "from LgInvite", EMPTY_QUERY));
					searchResults.add(user.findOneByKey(LgUser.class, "oid", alice.getOid()));
				} catch (Exception e) {
					;
				}
				return searchResults;
			}
		}.getResult();
		assertThat(queryResults).isEmpty();
	}
	
	// Fails
	@Ignore
	@Test
	public void deleteUserWithSurveyTest() {
		new TestTransaction<Object>("Alice") {
			@Override
			public Object execute() {
				startSession().deleteThisAccount();
				return null;
			}
		}.getResult();
		final List<DaObject> queryResults = new TestTransaction<List<DaObject>>(
				"Bob") {
			final Object[] EMPTY_QUERY = new Object[0];
			public List<DaObject> execute() {
				final LgUser user = startSession();
				final List<DaObject> searchResults = new ArrayList<DaObject>();
				try {
				searchResults.addAll(user.findManyByQuery(LgSurvey.class,
						LgSurvey.class, "from LgSurvey", EMPTY_QUERY));
				searchResults.addAll(user.findManyByQuery(LgInvite.class,
						LgInvite.class, "from LgInvite", EMPTY_QUERY));
					searchResults.add(user.findOneByKey(LgUser.class, "oid", alice.getOid()));
				} catch (Exception e) {
					;
				}
				return searchResults;
			}
		}.getResult();
		assertThat(queryResults).isEmpty();
	}
	
	// FAILS
	@Ignore
	@Test
	public void deleteUserWithInvitesTest() {
		new TestTransaction<Object>("Carol") {
			@Override
			public Object execute() {
				startSession().deleteThisAccount();
				return null;
			}
		}.getResult();
		final List<DaObject> queryResults = new TestTransaction<List<DaObject>>(
				"Bob") {
			public List<DaObject> execute() {
				final LgUser user = startSession();
				final List<DaObject> searchResults = new ArrayList<DaObject>();
				try {
				searchResults.addAll(
						user.findManyByKey(LgInvite.class, "user_oid", carol.getOid())
				);
				searchResults.add(user.findOneByKey(LgUser.class, "oid", carol.getOid()));
				} catch (Exception e) {
					;
				}
				return searchResults;
			}
		}.getResult();
		assertThat(queryResults).isEmpty();
		final LgSurvey surveyFromQuery = new TestTransaction<LgSurvey>(
				"Bob") {
			public LgSurvey execute() {
				final LgUser user = startSession();
				try {
					return user.findOneByKey(LgSurvey.class, "oid", surveyForEvaluation.getOid());
				} catch (Exception e) {
					;
				}
				return null;
			}
		}.getResult();
		surveyForEvaluation.removeParticipants(this.carol);
		assertThat(surveyFromQuery).isEqualTo(this.surveyForEvaluation);
	}
}
