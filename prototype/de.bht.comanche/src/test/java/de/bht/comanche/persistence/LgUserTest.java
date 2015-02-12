package de.bht.comanche.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.bht.comanche.logic.LgMessage;
import de.bht.comanche.logic.LgTimePeriod;
import de.bht.comanche.logic.LgUser;

/**
 * @author Simon Lischka
 */
@Ignore
public class LgUserTest {
	/**
	 * REWRITE!
	 */
    private LgUser alice;
    private Set <LgMessage> persistedMessages = new HashSet<LgMessage>();
    private Set <LgMessage> variousMessages = new HashSet<LgMessage>();
    private HashSet<LgTimePeriod> variousTimePeriods = new HashSet<LgTimePeriod>();
    private Set <LgTimePeriod> persistedGeneralAvailabilty;
    
    @BeforeClass
    public static void resetDatabase() {
        Map<String, String> properties = new HashMap<String, String>(1);
        properties.put("hibernate.hbm2ddl.auto", "create");
        Persistence.createEntityManagerFactory(
                DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
    }
	
    @Before
    public void buildUp() {
        createAlicesUserAccount();
    }

    private void createAlicesUserAccount() {
        this.alice = new LgUser().setName("Alice").setEmail("test@test.de")
                .setPassword("testtest");
        new TestTransaction<LgUser>("Alice") {
            @Override
            public LgUser execute() {
                getSession().register(alice);
                return startSession();
            }
        }.getResult();
    }
    
    private void updateAlicesUserAccount() {
        new TestTransaction<LgUser> ("Alice") {
                @Override
                public LgUser execute() {
                    // Prevents owning pool exception
                    return getSession().save(new LgUser().updateWith(alice));
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
    
    private void fetchAlicesMessagesForLazyAttempt() {
        this.alice  = (new TestTransaction<LgUser> ("Alice") {
            @Override
            /**
             * Persist demo time periods to Alice's account
             */
            public LgUser execute() {
                final LgUser alice = startSession();
                alice.getMessages();
                for (LgMessage msg : alice.getMessages()) {
                    persistedMessages.add(msg);
                }
                return alice;
            }
        }).getResult();
    }
    
    private void fetchAlicesMessagesForEagerAttempt() {
    	fetchAlicesUserAccount();
        this.persistedMessages = this.alice.getMessages();
    }
    
    // FAILS
    @Test
    public void saveDemoMessagesTest() {
        buildVariousMessages("Hello", "Kitty");
        saveMessagesToAlicesAccount();
        fetchAlicesMessagesForEagerAttempt();
        for (LgMessage m : this.persistedMessages) {
            assertTrue(
                    this.variousMessages.contains(
                            new LgMessage().updateWith(m)
                    )
            );
        }
    }
    
    @Test
    public void updateMessagesInUserAccountTest() {
    	buildVariousMessages("Hello", "Kitty Two");
        saveMessagesToAlicesAccount();
        fetchAlicesMessagesForEagerAttempt();
        for (LgMessage m : this.persistedMessages) {
            assertTrue(
                    this.variousMessages.contains(
                            new LgMessage().updateWith(m)
                    )
            );
        }
    }
    
    // ROUGH DRAFT
    @Test
    public void deleteMessagesInUserAccountTest() {
    	buildVariousMessages("Hello", "Kitty Two");
        saveMessagesToAlicesAccount();
        fetchAlicesMessagesForEagerAttempt();
        alice.setMessages(new HashSet<LgMessage>());
        saveMessagesToAlicesAccount();
        fetchAlicesMessagesForEagerAttempt();
        assertTrue(this.persistedMessages.size() == 0);
    }
   
    private void saveMessagesToAlicesAccount() {
    	alice.setMessages(variousMessages);
        updateAlicesUserAccount();
    }

    @Test
    public void saveTimePeriodInUserTest() {
//        buildVariousTimePeriods(20, 40, 60);
        saveGeneralAvailability();
        retrieveGeneralAvailability();
        assertEquals(persistedGeneralAvailabilty, variousTimePeriods);
    }
    

    @Test
    public void updateTimePeriodInUserTest() {
//        buildVariousTimePeriods(20, 40, 60);
        saveGeneralAvailability();
        retrieveGeneralAvailability();
        // UPDATE
//        buildVariousTimePeriods(70, 80, 90);
        saveGeneralAvailability();
        retrieveGeneralAvailability();
        assertTrue(this.persistedGeneralAvailabilty.size() == 3);
        assertTrue(this.persistedGeneralAvailabilty.contains(variousTimePeriods));
    }
    
    @Test
    public void updateUserAccountTest() {
    	
    }
    
    @Test
    public void deleteCompleteUserAccountTest() {
    	
    }
    
    private void saveGeneralAvailability() {
        this.alice.setGeneralAvailability(variousTimePeriods);
        updateAlicesUserAccount();
    }

    private void retrieveGeneralAvailability() {
        fetchAlicesUserAccount();
        this.persistedGeneralAvailabilty = this.alice.getGeneralAvailability();
    }

    private void buildVariousMessages(String ... messages) {
        for (int i = 0; i < messages.length; i++) {
            final LgMessage message = new LgMessage();
            message.setMessage(messages[i]);
            this.variousMessages.add(message);
        }
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