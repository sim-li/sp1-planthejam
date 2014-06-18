package de.bht.comanche.server;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HelloWorldIT {
	
	@Test
	public void testHelloWorld() throws Exception{
		final String body = get("/helloworld").asString();
		assertThat(body, equalTo("Hello World!"));
	}

}
