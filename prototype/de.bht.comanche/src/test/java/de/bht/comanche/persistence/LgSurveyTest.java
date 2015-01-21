package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgMessage;
import de.bht.comanche.logic.LgStatus;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgUser;

public class LgSurveyTest {
    private LgUser alice;
    private LgUser bob;
    private LgUser pit;
    private List<LgUser> allAcountsOnSystem;
	private LgSurvey freshSurvey = new LgSurvey();
	private LgSurvey persistedSurvey;
	private LgSurvey updatedSurvey;
    private LgInvite inviteForBob;
    private LgInvite inviteForPit;
    private Set<LgTimePeriod> variousTimePeriods = new HashSet<LgTimePeriod>();
	private Long oidOfSavedSurvey;
    
    @BeforeClass
    public static void resetDatabase() {
        Map<String, String> properties = new HashMap<String, String>(1);
        properties.put("hibernate.hbm2ddl.auto", "create");
        Persistence.createEntityManagerFactory(
                DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
    }

    @Before
    public void buildUp() {
        createAllUserAccounts();
        fetchAlicesUserAccount();
        getAllOtherAccounts();
    }

    private void createAllUserAccounts() {
        this.alice = new LgUser().setName("Alice").setEmail("test@test.de")
                .setPassword("testtest");
        this.bob = new LgUser().setName("Bob").setEmail("test@test.de")
                .setPassword("testtest");
        this.pit = new LgUser().setName("Pit").setEmail("test@test.de")
                .setPassword("testtest");
        new TestTransaction<String>("") {
            @Override
            public String execute() {
                getSession().register(alice);
                getSession().register(bob);
                getSession().register(pit);
                return "";
            }
        }.getResult();
    }
    
    private void fetchAlicesUserAccount() {
        this.alice  = (new TestTransaction<LgUser> ("Alice") {
            @Override
            /**
             * Persist demo time periods to Alice's account
             */
            public LgUser execute() {
                final LgUser alice = startSession();
                return alice;
            }
        }).getResult();
    }
    
    public void getAllOtherAccounts() {
    	allAcountsOnSystem = new TestTransaction<List<LgUser>> ("Alice") {
            @Override
            public List<LgUser> execute() {
                return getSession().getAllUsers();
            }
        }.getResult();
    }
   
    @Ignore
    @Test
    public void getAllAcountsDoesntContainOwnerTest() {
    	assertFalse(allAcountsOnSystem.contains(alice));
    }
    
    
    @Test
    public void savePossibleTimePeriodsTest() {
    	createAndPersistAFreshSurvey();
    	retrieveAlicesSurvey();
    	assertEquals(persistedSurvey.getPossibleTimePeriods(), this.freshSurvey.getPossibleTimePeriods());
    }

    public void createAndPersistAFreshSurvey() {
    	addInvitesToFreshSurvey(bob, pit);
    	buildVariousTimePeriods(20, 40, 60);
    	freshSurvey.setPossibleTimePeriods(variousTimePeriods);
    	saveAlicesSurvey();
    	
    }
    
    private void retrieveAlicesSurvey() {
        this.persistedSurvey = new TestTransaction<LgSurvey> ("Alice") {
            @Override
            public LgSurvey execute() {
            	 return startSession().getSurvey(oidOfSavedSurvey);
            }
        }.getResult();
    }
    
    private void addInvitesToFreshSurvey(LgUser ... users) {
    	for (LgUser user : users) {
    		freshSurvey.addInvite(new LgInvite().
    				setHost(false).
    				setIgnored(LgStatus.UNDECIDED)
    				.setUser(user));
    	}
    }
    
    private void buildVariousTimePeriods(int ... durations) {
        for (int i = 0; i < durations.length; i++) {
            this.variousTimePeriods.add(new LgTimePeriod().setStartTime(
                new Date()).setDurationMins(durations[i])
            );
        }
    }
    
    private void saveAlicesSurvey() {
         this.oidOfSavedSurvey = new TestTransaction<Long> ("Alice") {
                @Override
                public Long execute() {
                    return startSession().saveSurvey(freshSurvey).getOid();
                }
            }.getResult();
    }
    
    private void updateAlicesSurvey() {
        updatedSurvey = new TestTransaction<LgSurvey> ("Alice") {
                @Override
                public LgSurvey execute() {
                	 return startSession().updateSurvey(updatedSurvey);
                }
            }.getResult();
    }
    
    @After
    public void tearDown() {
        deleteAlicesUserAccount();
    }

    private void deleteAlicesUserAccount() {
        new TestTransaction<LgUser> ("Alice") {
            @Override
            /**
             * Persist demo time periods to Alice's account
             */
            public LgUser execute() {
                final LgUser alice = startSession();
                alice.deleteThisAccount();
                return alice;
            }
        }.getResult();
    }
	
}
