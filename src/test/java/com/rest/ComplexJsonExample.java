package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ComplexJsonExample {
    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://153d7bfc-7331-4891-acea-805b8db15a4d.mock.pstmn.io").
                addHeader("x-mock-match-request-body", "true").
                setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_post_request_payload_json_array_as_list(){
    	List<Integer> rgbaList1 = new ArrayList<Integer>();
    	rgbaList1.add(255);
    	rgbaList1.add(255);
    	rgbaList1.add(255);
    	rgbaList1.add(1);
    	
    	HashMap<String, Object> codeMap1 = new HashMap<String, Object>();
    	codeMap1.put("rgba", rgbaList1);
    	codeMap1.put("hex", "#000");
    	
    	HashMap<String, Object> colorMap1 = new HashMap<String, Object>();
    	colorMap1.put("color", "black");
    	colorMap1.put("category", "hue");
    	colorMap1.put("type", "primary");
    	colorMap1.put("code", codeMap1);
    	
		
		  List<Integer> rgbaList2 = new ArrayList<Integer>(); 
		  rgbaList2.add(0);
		  rgbaList2.add(0); 
		  rgbaList2.add(0); 
		  rgbaList2.add(1);
		  
		  HashMap<String, Object> codeMap2 = new HashMap<String, Object>();
		  codeMap2.put("rgba", rgbaList2); 
		  codeMap2.put("hex", "#FFF");
		  
		  HashMap<String, Object> colorMap2 = new HashMap<String, Object>();
		  colorMap2.put("color", "white"); 
		  colorMap2.put("category", "value");
		  colorMap2.put("code", codeMap2);
		 
    	
    	List<HashMap<String, Object>> colorsList = new ArrayList<HashMap<String, Object>>();
    	colorsList.add(colorMap1);
    	colorsList.add(colorMap2);
    	
    	
    	HashMap<String, List<HashMap<String, Object>>> jsonMap = new HashMap<String, List<HashMap<String, Object>>>();
    	jsonMap.put("colors", colorsList);
    	
        given().
                body(jsonMap).
        when().
                post("/complexJson").
        then().spec(customResponseSpecification).
                assertThat().
                body("msg", equalTo("Success"));
    }
}
