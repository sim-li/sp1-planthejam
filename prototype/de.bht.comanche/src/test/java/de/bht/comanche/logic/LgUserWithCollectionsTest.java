package de.bht.comanche.logic;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.bht.comanche.persistence.DaEmProvider;


/**
 * @author Maxim Novichkov;
 * @author Simon Lischka
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LgUserWithCollectionsTest {
    private LgUser alice;
    private LgSession session;
    private LgTimePeriod timePeriod;

    @BeforeClass
    public static void resetDatabase() {
        Map<String, String> properties = new HashMap<String, String>(1);
        properties.put("hibernate.hbm2ddl.auto", "create");
        Persistence.createEntityManagerFactory(DaEmProvider.PERSISTENCE_UNIT_NAME, properties);
    }
    
    @Before
    public void buildUp() {
        beginTransaction();
        registerAlice();
        alice = session.startFor("Alice");
        timePeriod = new LgTimePeriod()
            .setDurationMins(10)
            .setStartTime(new Date(8099));
    }

    private void registerAlice() {
        alice = new LgUser().setName("Alice").setEmail("test@test.de")
                .setPassword("testtest");
        session.register(alice);
    }

    /**
     * This test actually includes the checking of data, thus the part before the commit
     * is what would be called in a Rest Service.
     */
    @Test
    public void saveMessagesTest() {
        saveDemoMessages();
        commitAndRestartTransaction();
        alice = session.startFor("Alice");
        assertTrue("Alice has message 'Hello'", alice.getMessages().contains("Hello"));
        assertTrue("Alice has message 'Kitty'", alice.getMessages().contains("Kitty"));
    }
    
    @Test
    public void removeAndAddUpdateMessageTest() {
    	 saveDemoMessages();
    	 commitAndRestartTransaction();
    	 alice = session.startFor("Alice");
    	 alice.getMessages().remove("Hello");
    	 alice.getMessages().add("Update");
    	 commitAndRestartTransaction();
    	 alice = session.startFor("Alice");
    	 assertTrue("Alice has Updated message", alice.getMessages().contains("Update"));
    	 assertFalse("Alice does not have old message", alice.getMessages().contains("Hello"));
    }

    @Test
	public void saveDemoMessages() {
		List<String> messages = new ArrayList<String>();
        messages.add("Hello");
        messages.add("Kitty");
        alice.setMessages(messages);
        alice.save();
	}

    @Test
    public void saveTimePeriodTest() {
    	List<LgTimePeriod> timePeriods = new ArrayList<LgTimePeriod>();
    	final LgTimePeriod timePeriod20 = new LgTimePeriod()
		.setStartTime(new Date())
		.setDurationMins(20);
    	final LgTimePeriod timePeriod40 = new LgTimePeriod()
		.setStartTime(new Date())
		.setDurationMins(40);
    	final LgTimePeriod timePeriod60 = new LgTimePeriod()
		.setStartTime(new Date())
		.setDurationMins(60);
    	timePeriods.add(timePeriod20);
    	timePeriods.add(timePeriod40);
    	timePeriods.add(timePeriod60);
    	alice.setGeneralAvailability(timePeriods);
    	alice.save();
    	commitAndRestartTransaction();
    	alice = session.startFor("Alice");
    	
    	final Collection<LgTimePeriod> alicesAvailability = alice.getGeneralAvailability();
    	assertThat(extractProperty("durationMins")
    			.from(alicesAvailability))
    			.contains(20, 40, 60);
    }
    
    @After
    public void tearDown() {
        alice.deleteThisAccount();
        endTransaction();
    }

    private void beginTransaction() {
        session = new LgSession();
        session.getApplication().beginTransaction();
    }

    private void endTransaction() {
        session.getApplication().endTransaction(true);
    }

    private void commitAndRestartTransaction() {
        endTransaction();
        beginTransaction();
    }
}
