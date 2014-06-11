package de.bht.comanche.server;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HelloWorldTest {
	
	@Test
	public void testHelloWorld() throws Exception{
//		get("/rest/helloworld").then().assertThat().body(equalTo("Hello World!"));
		final String body = get("/helloworld").asString();
		assertThat(body, equalTo("Hello World!"));
//		expect().body("hello world", equalTo("Hello World!")).when().get("/rest/helloworld");
//		get("/x").then().assertThat().body(equalTo("something"))
		
	}

}
