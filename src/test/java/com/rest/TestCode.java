package com.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
    	
		//System.out.println(colorMap1.toString());
		
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
		 
    	System.out.println(colorMap2.toString());
    	List<HashMap<String, Object>> colorsList = new ArrayList<HashMap<String, Object>>();
    	colorsList.add(colorMap1);
    	colorsList.add(colorMap2);
    	
    	
    	HashMap<String, List<HashMap<String, Object>>> jsonMap = new HashMap<String, List<HashMap<String, Object>>>();
    	jsonMap.put("colors", colorsList);
    	
    	System.out.println(jsonMap.toString());
	}

}
