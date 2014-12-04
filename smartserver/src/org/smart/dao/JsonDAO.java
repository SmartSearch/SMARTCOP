package org.smart.dao;
 
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
/**
 * Interface to be implemented for classes that allow data retrieval from JSON sources
 * uses org.json and Apache HttpClient libraries
 * @author Wladimir Totino
 *
 */
public interface JsonDAO {
	/**
	 * retrieves JSON data and returns it as a JSONArray 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONArray getJsonArray(String url) throws ClientProtocolException, IOException, JSONException;
	
	/**
	 * retrieves JSON data and returns it as a String
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String getJsonAsString(String url) throws ClientProtocolException, IOException, JSONException;
	
	/**
	 * retrieves XML data by doing a REST request and converts the data into a JSONObject
	 * @param url the url where to get the XML data from
	 * @param body the body of the REST request
	 * @param contentType content type of the REST request. usually "text/xml"
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONObject getJsonObjectFromXML(String url, String body, String contentType) throws ClientProtocolException, IOException, JSONException;
}