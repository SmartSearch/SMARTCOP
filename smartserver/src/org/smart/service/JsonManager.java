package org.smart.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.smart.model.Node;


public interface JsonManager {	
	public List<Node> getNodesList(String url) throws ClientProtocolException, IOException, JSONException;
	public String getJsonFromUrl(String url) throws ClientProtocolException, IOException, JSONException;
	public JSONObject getTwitterXMLAsJson(String url, String body, String contentType) throws ClientProtocolException, IOException, JSONException;
}
