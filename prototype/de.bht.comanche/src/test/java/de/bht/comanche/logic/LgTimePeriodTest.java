package de.bht.comanche.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import de.bht.comanche.persistence.DaEmProvider;

/**
 * @author Maxim Novichkov;
 *
 */

@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgTimePeriodTest {
	
	LgSurvey survey;
	
	@BeforeClass 
	public static void initializeDb() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		Persistence.createEntityManagerFactory(DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
		assertTrue("Initialized JPA Database -> Pre Test Cleannup", true);

		LgSession session = new LgSession();
		session.getApplication().beginTransaction();
		
		final LgUser alice = new LgUser();
		alice.setName("Alice");
		alice.setEmail("test@test.de");
		alice.setPassword("testtest");
		session.register(alice);
		
		final LgUser bob = new LgUser();
		bob.setName("Bob");
		bob.setEmail("bob@test.de");
		bob.setPassword("testtest");
		session.register(bob);
		
		final LgUser pit = new LgUser();
		pit.setName("Pit");
		pit.setEmail("pit@test.de");
		pit.setPassword("testtest");
		session.register(pit);
		session.getApplication().endTransaction(true);	
	}
	
	public LgSession start(){
		LgSession session = new LgSession();
		session.getApplication().beginTransaction();
		return session;
	}
	
//	@Test
//	public void test1CreateSurvey(){
//		final LgSession session = start();
//		final LgUser user = startForUser(session);
//		List<LgInvite> lg = new ArrayList<LgInvite>();
//		LgSurvey Dbsurvey = new LgSurvey().setName("NewTest").setInvites(lg);
//		user.saveObj(Dbsurvey);
//		end(session);
//	}
	
	@Test
	public void test2CreateInvite(){
		final LgSession session = start();
		final LgUser user = startForUser(session);
		LgSurvey Dbsurvey = new LgSurvey().setName("NewTest");
		LgInvite invite = new LgInvite().setHost(true).setIgnored(false).setUser(user).setSurvey(Dbsurvey);
		user.save(invite);
		end(session);
	}
	
	@Test
	public void test3SaveTPforInvite(){
		final LgSession session = start();
		final LgUser user = startForUser(session);
		LgInvite inviteDb = user.getInvites().get(0);
		LgTimePeriod tp = new LgTimePeriod().setDurationMinutes(10).setStartTime(new Date(8099)).setInvite(inviteDb);
		session.getUser().saveObj(tp);
		end(session);
	}
	
	@Test
	public void test4CreateSurveyWithInvite(){
		final LgSession session = start();
		final LgUser user = startForUser(session);

		List<LgInvite> lg = new ArrayList<LgInvite>();
		lg.add(user.getInvites().get(0));
		LgSurvey survey = new LgSurvey().setName("NewTest").setInvites(lg);
		user.saveObj(survey);
		
		
		end(session);
	}
	
	@Test
	public void test5SaveTPforInvite(){
		final LgSession session = start();
		final LgUser user = startForUser(session);
		System.out.println(user.getInvites().get(0).getOid() + " 2====================================");
		LgSurvey survey = user.getInvites().get(0).getSurvey();
		LgTimePeriod tp = new LgTimePeriod().setDurationMinutes(10).setStartTime(new Date(8099)).setSurvey(survey);
		session.getUser().saveObj(tp);
		end(session);
		}
	
//	@Test
	public void test4deleteUser(){
		final LgSession session = start();
		final LgUser bob = session.startFor("Bob");
		bob.deleteAccount();
//		assertEquals(0, startFor().getGroups().size());
	}
	
//	@Test
	public void test5deleteMember(){
		final LgSession session = start();
		final LgUser sessionUser = startForUser(session);
		final LgGroup aliceGroup = sessionUser.getGroups().get(0);
		final LgUser bob = session.startFor("Bob");
		final long bob_moid = session.getUser().search(aliceGroup.getOid(), bob.getOid()).get(0).getOid();
		sessionUser.getGroup(aliceGroup.getOid()).deleteMember(bob_moid);
		end(session);
		assertEquals(null, getAliceGroup(aliceGroup.getOid()).getMember(bob_moid));
	}
		
	public void end(LgSession session){
		session.getApplication().endTransaction(true);
	}
	
	public LgUser startForUser(final LgSession session){
		return session.startFor("Alice");
	}
	
	public LgUser startFor(){
		return new LgSession().startFor("Alice");
	}
	
	public LgGroup getAliceGroup(final long oid){
		return startFor().getGroup(oid);
	}
	
}
