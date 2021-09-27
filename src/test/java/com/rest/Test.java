package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.testng.Assert;

//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import static org.hamcrest.Matchers.equalTo;

public class Test {

    @org.testng.annotations.Test
    public void validate_Request_body(){
        given().
                baseUri("https://api.postman.com").
                header("X-API-Key","PMAK-614eeb1e2f6ed1003cf9682b-f501018f1521326dd7d2d9d7829f42f6cf").
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
    
    @org.testng.annotations.Test
    public void validate_Response_body(){
        given().
                baseUri("https://api.postman.com").
                header("X-API-Key","PMAK-614eeb1e2f6ed1003cf9682b-f501018f1521326dd7d2d9d7829f42f6cf").
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItem("ManishWorkspace"),
                	 "workspaces.type", hasItem("personal"),
                	 "workspaces[0].name", equalTo("ManishWorkspace"));
                 
    }
    
    @org.testng.annotations.Test
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
