package com.rest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.with;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;


public class AutomatePost {

	@BeforeClass
	public void beforeClass() {
		String key = null;
		File file = new File("postmanKey" + File.separator + "postman-key.txt");
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			key = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
			
		RequestSpecBuilder reqSpecbldr = new RequestSpecBuilder().
				setBaseUri("https://api.postman.com").
				addHeader("X-API-Key", key).
				setContentType(ContentType.JSON).
				log(LogDetail.ALL);
		RestAssured.requestSpecification = reqSpecbldr.build();
		
		ResponseSpecBuilder resspecbldr = new ResponseSpecBuilder().
				expectStatusCode(200).
				expectContentType(ContentType.JSON).
				log(LogDetail.ALL);
		RestAssured.responseSpecification = resspecbldr.build();
	}
	
	@Test
	public void validate_post_request_bdd_style() {
		String payload = "{\r\n"
				+ "    \"workspace\": {\r\n"
				+ "        \"name\": \"NewWorkspace-post\",\r\n"
				+ "        \"type\": \"personal\",\r\n"
				+ "        \"description\": \"Some description\"\r\n"
				+ "    }\r\n"
				+ "}";
		
		given().
			body(payload).
		when().
			post("/workspaces").
		then().
			assertThat().
			body("workspace.name", equalTo("NewWorkspace-post"),
					"workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
	}
	
	@Test
	public void validate_post_request_non_bdd_style() {
		String payload = "{\r\n"
				+ "    \"workspace\": {\r\n"
				+ "        \"name\": \"NewWorkspace-post\",\r\n"
				+ "        \"type\": \"personal\",\r\n"
				+ "        \"description\": \"Some description\"\r\n"
				+ "    }\r\n"
				+ "}";
		Response resp = with().
				body(payload).
				post("/workspaces");
		assertThat(resp.<String>path("workspace.name"), equalTo("NewWorkspace-post"));
		assertThat(resp.<String>path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
	}
}
