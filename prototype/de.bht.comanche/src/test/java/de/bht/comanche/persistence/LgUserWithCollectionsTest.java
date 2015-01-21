package de.bht.comanche.persistence;

//import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgUserWithCollectionsTest {

    private LgUser alice;
    private Set <LgMessage> messages = new HashSet<LgMessage>();
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
        fetchAlicesUserAccount();
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
                    messages.add(msg);
                }
                return alice;
            }
        }).getResult();
    }
    
    private void fetchAlicesMessagesForEagerAttempt() {
        this.messages = this.alice.getMessages();
    }
    
    @Test
    public void saveDemoMessagesTest() {
        buildVariousMessages("Hello", "Kitty");
        saveMessagesToAlicesAccount();
        for (LgMessage m : this.messages) {
            assertTrue(
                    this.variousMessages.contains(
                            new LgMessage().updateWith(m)
                    )
            );
        }
    }
    
    private void saveMessagesToAlicesAccount() {
        updateAlicesUserAccount();
//      fetchAlicesMessagesForLazyAttempt();
        fetchAlicesMessagesForEagerAttempt();
    }

    @Test
    @SuppressWarnings("unused")
    public void saveTimePeriodInUserTest() {
        buildVariousTimePeriods(20, 40, 60);
        saveGeneralAvailability();
        retrieveGeneralAvailability();
        
        @SuppressWarnings("unused")
		final int sizeOfPersistedGeneralVailability = persistedGeneralAvailabilty.size();
        final int sizeOfVariousTimePeriods = variousTimePeriods.size();
        
        HashSet<LgTimePeriod> timePeriodsCopy = new HashSet<LgTimePeriod>();
        for (LgTimePeriod t : this.persistedGeneralAvailabilty) {
        	timePeriodsCopy.add(new LgTimePeriod().updateWith(t));
        }
        Object[] timePeriodsCopyAsArray = timePeriodsCopy.toArray();
        Object[] variousTimePeriodsAsArray = variousTimePeriods.toArray();
        assertEquals("Length is equal? ", timePeriodsCopyAsArray.length, variousTimePeriodsAsArray.length);
        for (int i = 0; i < timePeriodsCopyAsArray.length; i++) {
        	LgTimePeriod tp1 = (LgTimePeriod) timePeriodsCopyAsArray[i];
        	LgTimePeriod tp2 = (LgTimePeriod) variousTimePeriodsAsArray[i];
        	assertEquals("Comaparing TP DUR " + tp1.getDurationMins() + " with " + tp2.getDurationMins(),
        			tp1, tp2);
        }
        
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
    
    private void buildVariousTimePeriods(int ... durations) {
        for (int i = 0; i < durations.length; i++) {
            this.variousTimePeriods.add(new LgTimePeriod().setStartTime(
                new Date()).setDurationMins(durations[i])
            );
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