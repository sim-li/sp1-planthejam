package de.bht.comanche;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloMavenTest {
    
    @Test public void sayHelloWorldsaysHelloWorld() {
        assertEquals("Hello World!", HelloMaven.sayHelloWorld());
	}
    
    @Test public void addWorks() {
        assertEquals(5, HelloMaven.add(2, 3));
    }
}
