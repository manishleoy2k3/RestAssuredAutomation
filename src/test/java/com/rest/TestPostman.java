package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import static org.hamcrest.Matchers.equalTo;

public class TestPostman {
	
	Header header = new Header("header", "value1");
	Header matchHeader = new Header("x.mock.match.request.headers","header");
	Headers headers = new Headers(header, matchHeader);
	
	@BeforeClass
	public void beforeClass() {
		/*
		 * reqSpec = with(). baseUri("https://api.postman.com"). header("X-API-Key",
		 * "PMAK-614eeb1e2f6ed1003cf9682b-f501018f1521326dd7d2d9d7829f42f6cf");
		 */
		RequestSpecBuilder reqSpecbldr = new RequestSpecBuilder();
		reqSpecbldr.setBaseUri("https://api.postman.com");
		reqSpecbldr.addHeader("X-API-Key", "PMAK-614eeb1e2f6ed1003cf9682b-f501018f1521326dd7d2d9d7829f42f6cf");
		reqSpecbldr.log(LogDetail.ALL);
		
		RestAssured.requestSpecification = reqSpecbldr.build();
	}
	
	@Test
    public void validate_status_code(){
		Response resp = get("/workspaces").then().log().all().extract().response();
		assertThat(resp.statusCode(), is(equalTo(200)));
    }
    
    @Test
    public void validate_response_body(){
    	Response resp = get("/workspaces").then().log().all().extract().response();
		assertThat(resp.statusCode(), is(equalTo(200)));
		assertThat(resp.path("workspaces[0].name").toString(), equalTo("ManishWorkspace"));
    }
    
    //@Test
    public void extract_Response(){
        String name = given().
                baseUri("https://api.postman.com").
                header("X-API-Key","PMAK-614eeb1e2f6ed1003cf9682b-f501018f1521326dd7d2d9d7829f42f6cf").
                log().all().
        when().
                get("/workspaces").
        then().
        		log().all().
                assertThat().
                statusCode(200).
                extract().response().path("workspaces[0].name");
                //extract().response().asString();
        System.out.println("workspace name is: " + name);
        assertThat(name, equalTo("ManishWorkspace"));
        Assert.assertEquals(name, "ManishWorkspace");
        
        //System.out.println("Response = " + JsonPath.from(res).getString("workspaces[0].name"));
        
        //JsonPath jsonPath = new JsonPath(res.asString());
        //System.out.println("Response = " + jsonPath.getString("workspaces[0].name"));
        //System.out.println("Response = " + res.path("workspaces[0].name"));                  
    }
}
