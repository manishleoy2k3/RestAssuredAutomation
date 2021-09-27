package com.rest;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.testng.Assert;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;

public class AutomateMultipleHeader {

	Header header = new Header("header", "value1");
	Header matchHeader = new Header("x.mock.match.request.headers","header");
	
	Headers headers = new Headers(header, matchHeader);
	
	
	@org.testng.annotations.Test
    public void validate_Request_body(){
		RequestSpecification reqSpec = given().
				baseUri("https://153d7bfc-7331-4891-acea-805b8db15a4d.mock.pstmn.io").
                headers(headers);
		
        given(reqSpec).
        when().
        		log().all().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
}
