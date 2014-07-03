package de.bht.comanche.server;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HelloWorldTest {
	
	@Test
	public void testHelloWorld() throws Exception{
		final String body = get("/plan-the-jam/rest/helloworld").asString();
		assertThat(body, equalTo("Hello World!"));
	}

}
