package de.bht.comanche.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgUser;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;
public class LgSurveyTest {
    private LgUser alice;
    private LgUser bob;
    private LgUser carol;
    
	@BeforeClass
    public static void resetDatabase() {
        Map<String, String> properties = new HashMap<String, String>(1);
        properties.put("hibernate.hbm2ddl.auto", "create");
        Persistence.createEntityManagerFactory(
                DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
    }

    @Before
    public void buildUp() {
        saveUserAccounts();
    }

    /**
     * Saves Alice, Bob, and Carols user accounts in three
     * separate transactions using the register method which
     * is also used in the rest service.
     */
    private void saveUserAccounts() {
    	final LgUser testAccountBob = new LgUser()
	    		.setName("Bob")
	    		.setEmail("test@test.de")
	            .setPassword("testtest");
        final LgUser testAccountAlice = new LgUser()
	    		.setName("Alice")
	    		.setEmail("test@test.de")
	            .setPassword("testtest");
        final LgUser testAccountCarol = new LgUser()
	        	.setName("Carol")
	        	.setEmail("carol@test.de")
	        	.setPassword("testtest");
        alice = new TestTransaction<LgUser>("Alice") {
            @Override
            public LgUser execute() {
                return getSession().register(testAccountAlice);
            }
        }.getResult();
        bob = new TestTransaction<LgUser>("Bob") {
            @Override
            public LgUser execute() {
                return getSession().register(testAccountBob);
            }
        }.getResult();
        carol = new TestTransaction<LgUser>("Bob") {
            @Override
            public LgUser execute() {
                return getSession().register(testAccountCarol);
            }
        }.getResult();
    }
    
//    @Ignore
//    @Test
//    public void getAllAcountsDoesntContainOwnerTest() {
//    	assertFalse(allAcountsOnSystem.contains(alice));
//    }
//    
    /**
     *  Note: User with active transaction will automatically be added as
     *  host when calling saveSurvey. That's why we check for Alice even though
     *  we only add Bob and Carol to the survey.
     */
    @Test
    public void saveSurveyWithInvitesPariticipantsTest() {
    	final LgSurvey surveyForEvaluation = saveSurveyWithInvites();
    	assertThat(extractProperty("user.name").from(surveyForEvaluation.getInvites()))
			.containsExactly("Alice", "Bob", "Carol");
    }
    
    @Test
    public void saveSurveyWithInvitesHostAttributeTest() {
    	final LgSurvey surveyForEvaluation = saveSurveyWithInvites();
    	assertThat(extractProperty("isHost").from(surveyForEvaluation.getInvites()))
    		.containsExactly(true, false, false);
    }

    /**
     * Saves survey with Bob and Carol as Participant.
     * 
     * @return Persisted survey with OID.
     */
	private LgSurvey saveSurveyWithInvites() {
		final LgSurvey aSurvey = new LgSurvey()
    		.addParticipants(bob, carol);
    	final LgSurvey surveyForEvaluation = saveSurveyForAlice(aSurvey);
		return surveyForEvaluation;
	}
    
    @Test
    public void saveSurveyWithTimePeriodsTest() {
    	final LgSurvey freshSurvey = new LgSurvey().setPossibleTimePeriods(
    			buildTimePeriods(20, 40, 60));
    	final LgSurvey surveyForEvaluation = saveSurveyForAlice(freshSurvey);
    	assertThat(extractProperty("durationMins").from(surveyForEvaluation.getPossibleTimePeriods()))
    		.contains(20, 40, 60);
    }
    
    @Test
    public void saveSurveyWithDeterminedTimePeriodTest() {
    	final LgSurvey freshSurvey = new LgSurvey().setDeterminedTimePeriod(new LgTimePeriod()
    			.setDurationMins(20)
    			.setStartTime(new Date()));
    	final LgSurvey surveyForEvaluation = saveSurveyForAlice(freshSurvey);
    	assertThat(surveyForEvaluation.getDeterminedTimePeriod().getDurationMins()).isEqualTo(20);
    }
    
    /**
     * Generates a hash set of time periods, inserts the current 
     * date stamp to fill the field with a value.
     * 
     * @param durations List of durations as integer. Every duration will generate a time period.
     * @return A set of time periods
     */
    private HashSet<LgTimePeriod> buildTimePeriods(int ... durations) {
    	final HashSet<LgTimePeriod> timePeriods = new HashSet<LgTimePeriod>();
        for (int i = 0; i < durations.length; i++) {
            timePeriods.add(new LgTimePeriod()
            	.setStartTime(new Date())
            	.setDurationMins(durations[i])
            );
        }
        return timePeriods;
    }
    
    /**
     * Saves a survey for Alice.
     * 
     * @param freshSurvey Survey to be persisted
     * @return The persisted survey with OID.
     */
	private LgSurvey saveSurveyForAlice(final LgSurvey freshSurvey) {
		final LgSurvey persistedSurvey = new TestTransaction<LgSurvey>("Alice") {
             @Override
             public LgSurvey execute() {
                 return startSession().saveSurvey(freshSurvey);
             }
         }.getResult();
		return persistedSurvey;
	}

    @Test
    public void updateSurveyWithPossibleTimePeriodsTest() {
    	final LgSurvey freshSurvey = new LgSurvey().setPossibleTimePeriods(
    			buildTimePeriods(20, 40, 60));
    	saveSurveyForAlice(freshSurvey);
    	final LgSurvey updatedSurvey =  new LgSurvey().setPossibleTimePeriods(
    			buildTimePeriods(40, 60, 80));
    	final LgSurvey surveyForEvaluation = new TestTransaction<LgSurvey>("Alice") {
        	@Override
	        public LgSurvey execute() {
	           return startSession().updateSurvey(updatedSurvey);
	        }
    	}.getResult();
    	assertThat(extractProperty("durationMins").from(surveyForEvaluation.getPossibleTimePeriods()))
    		.containsExactly(40, 60, 80);
    }

//    @Test
//    public void deleteTimePeriodFromSurveyTest() {
//    	// BUILD UP
//    	buildVariousTimePeriods(20, 40, 60);
//    	saveAndRetrieveTimePeriods();
//    	// MODIFY
//    	buildVariousTimePeriods(60);
//    	persistedSurvey.setPossibleTimePeriods(variousTimePeriods);
//    	assertEquals(persistedSurvey.getPossibleTimePeriods(), this.freshSurvey.getPossibleTimePeriods());
//    }
//
//    @Test
//    public void updateInviteForParticipantsTest() {
//    	
//    }
//    
//    @Test
//    public void deleteInviteForParticipantsTest() {
//    	
//    }
//    
//    
//    private void fetchAlicesUserAccount() {
//        this.alice  = (new TestTransaction<LgUser> ("Alice") {
//            @Override
//            /**
//             * Persist demo time periods to Alice's account
//             */
//            public LgUser execute() {
//                final LgUser alice = startSession();
//                return alice;
//            }
//        }).getResult();
//    }
//    
//    public void getAllOtherAccounts() {
//    	allAcountsOnSystem = new TestTransaction<List<LgUser>> ("Alice") {
//            @Override
//            public List<LgUser> execute() {
//                return getSession().getAllUsers();
//            }
//        }.getResult();
//    }
//   
//   
//	private void saveAndRetrieveTimePeriods() {
//		freshSurvey.setPossibleTimePeriods(variousTimePeriods);
//    	saveAlicesSurvey();
//    	retrieveAlicesSurvey();
//	}
//    
//    @Test
//    public void saveInviteForParticipantsTest() {
//    	addInvitesToFreshSurvey(bob, pit);
//    	saveAlicesSurvey();
//    	retrieveAlicesSurvey();
//    	
//    	assertTrue(persistedSurvey.getInvites().size() == 3);
//    	
//    	LgInvite hostInvite = new LgInvite().setHost(true).setUser(alice);
//    	
//    	assertTrue(persistedSurvey.getInvites().contains(hostInvite));
//    	
//    	for (LgInvite invite : persistedSurvey.getInvites()) {
//    		if (!invite.equals(hostInvite)) {
//    			assertTrue(persistedSurvey.getInvites().contains(invite));
//    		}
//    	}
//    	
//    }
//    
//  
//    
//    private void retrieveAlicesSurvey() {
//        this.persistedSurvey = new TestTransaction<LgSurvey> ("Alice") {
//            @Override
//            public LgSurvey execute() {
//            	 return startSession().getSurvey(oidOfSavedSurvey);
//            }
//        }.getResult();
//    }
//    
//  
//    
//    private void buildVariousTimePeriods(int ... durations) {
//        for (int i = 0; i < durations.length; i++) {
//            this.variousTimePeriods.add(new LgTimePeriod().setStartTime(
//                new Date()).setDurationMins(durations[i])
//            );
//        }
//    }
//    
//    private void saveAlicesSurvey() {
//         this.oidOfSavedSurvey = new TestTransaction<Long> ("Alice") {
//                @Override
//                public Long execute() {
//                    return Long.valueOf(startSession().saveSurvey(freshSurvey).getOid());
//                }
//            }.getResult();
//    }
//    
//    private void updateAlicesSurveyWith(final LgSurvey surveyForUpdate) {
//        updatedSurvey = new TestTransaction<LgSurvey> ("Alice") {
//                @Override
//                public LgSurvey execute() {
//                	 return startSession().updateSurvey(surveyForUpdate);
//                }
//            }.getResult();
//    }
//    
//    @After
//    public void tearDown() {
//        deleteAlicesUserAccount();
//    }
//
	/**
	 * Note: Delete has to be executed in separate
	 * transactions. Only when a transaction was executed,
	 * the contained invites and surveys are deleted.
	 */
	@After
    public void deleteTestData() {
		deleteAccountFor("Alice", "Bob", "Carol");
    }
	
	public void deleteAccountFor(String ... users) {
		for (String user: users) {
			 new TestTransaction<LgUser> (user) {
		            @Override
		            public LgUser execute() {
		                final LgUser user = startSession();
		                user.deleteThisAccount();
		                return user;
		            }
		        }.getResult();
			}
	}
}
