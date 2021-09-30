package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;

//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import static org.hamcrest.Matchers.equalTo;

public class TestPostman {
	
	Header header = new Header("header", "value1");
	Header matchHeader = new Header("x.mock.match.request.headers","header");
	Headers headers = new Headers(header, matchHeader);
	
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
				log(LogDetail.ALL);
		RestAssured.requestSpecification = reqSpecbldr.build(); 
		 
		 /* resspec = RestAssured.expect(). statusCode(200). contentType(ContentType.JSON).
		 * log().all();
		 */
		ResponseSpecBuilder resspecbldr = new ResponseSpecBuilder().
				expectStatusCode(200).
				expectContentType(ContentType.JSON).
				log(LogDetail.ALL);
		RestAssured.responseSpecification = resspecbldr.build();
	}
	
	@Test
    public void validate_status_code(){
		get("/workspaces");
    }
    
    @Test
    public void validate_response_body(){
    			get("/workspaces");
		//assertThat(resp.path("workspaces[0].name").toString(), equalTo("ManishWorkspace"));
    }
    
    @Test
    public void queryTest() {
    	QueryableRequestSpecification query = SpecificationQuerier.query(RestAssured.requestSpecification);
    	System.out.println(query.getBaseUri());
    	System.out.println(query.getHeaders());
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
