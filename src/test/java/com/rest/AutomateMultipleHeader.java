package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AutomateMultipleHeader {

	RequestSpecification reqSpec;
	
	Header header = new Header("header", "value1");
	Header matchHeader = new Header("x.mock.match.request.headers","header");
	Headers headers = new Headers(header, matchHeader);
	
	@BeforeClass
	public void beforeClass() {
		reqSpec = with().
				baseUri("https://153d7bfc-7331-4891-acea-805b8db15a4d.mock.pstmn.io").
                headers(headers);
	}
	
	@Test
    public void validate_status_code(){
		Response resp = reqSpec.get("/get").then().log().all().extract().response();
		assertThat(resp.statusCode(), is(equalTo(200)));
    }
	
	@Test
    public void validate_response_body(){
		Response resp = reqSpec.get("/get").then().log().all().extract().response();
		assertThat(resp.statusCode(), is(equalTo(200)));
		//assertThat(resp.path(), is(equalTo(200)));
    }
}
