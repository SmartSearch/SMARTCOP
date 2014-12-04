package org.smart.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.http.HttpHeaders;
/**
 * Collection of Helper methods used to work with JSON data 
 * @author Wladimir Totino
 *
 */
public class JsonUtils {

	/**
	 * returns http headers in a Spring object when returning JSON data as response
	 * @return
	 */
    public static HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return headers;
    }
    
    /**
     * converts a simple Map<String, String> (which represent a JSON object without arrays inside) to a JSON string
     * TODO: checks wheter this method is really unusued
     * @param objects
     * @return
     */
    public static String toJsonString(Map<String, String> objects){
    	String str="{";
    	Iterator<Entry<String, String>> it=objects.entrySet().iterator();
    	while(it.hasNext()){
    		Entry<String, String> next = it.next();
    		str+="\""+next.getKey()+"\" : \""+next.getValue()+"\", ";
    	}
    	str+="}";
    	return str;
    }

}
