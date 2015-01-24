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

public class LgInviteTest {
    private LgUser alice;
    private LgUser bob;
    private LgUser pit;
    
    @BeforeClass
    public static void resetDatabase() {
        Map<String, String> properties = new HashMap<String, String>(1);
        properties.put("hibernate.hbm2ddl.auto", "create");
        Persistence.createEntityManagerFactory(
                DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
    }

    @Before
    public void buildUp() {
        prepareTestData();
        fetchBob();
    }
    
    private void prepareTestData() {
        this.alice = new LgUser().setName("Alice").setEmail("test@test.de")
                .setPassword("testtest");
        this.bob = new LgUser().setName("Bob").setEmail("test@test.de")
                .setPassword("testtest");
        this.pit = new LgUser().setName("Pit").setEmail("test@test.de")
                .setPassword("testtest");
        new TestTransaction<String>("") {
            @Override
            public String execute() {
                alice = getSession().register(alice);
                bob = getSession().register(bob);
                pit = getSession().register(pit);
                return "";
            }
        }.getResult();
        new TestTransaction<String>("") {
            @Override
            public String execute() {
        	  alice.saveSurvey(
              		new LgSurvey()
              		.setName("Test")
              		.addInvite(new LgInvite().setUser(bob)));
              return ""; 	
            }
        }.getResult();
        
    }
    
    private void fetchBob() {
        this.bob  = (new TestTransaction<LgUser> ("bob") {
            @Override
            /**
             * Persist demo time periods to Alice's account
             */
            public LgUser execute() {
                final LgUser bob = startSession();
                return bob;
            }
        }).getResult();
    }
    
    
    @Test
    public void receiveInviteTest() {
    }
    
    @Test
    public void updateInviteTest() {
//    	bob.getInvitesAsParticipant();
//    	   final LgInvite bobsUpdatedInvite  = (new TestTransaction<LgInvite> ("bob") {
//               @Override
//               /**
//                * Persist demo time periods to Alice's account
//                */
//               public LgInvite execute() {
//            		return startSession().saveInvite(invite);
//               }
//           }).getResult();
    	
    }
}
