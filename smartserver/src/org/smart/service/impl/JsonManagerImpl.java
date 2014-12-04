package org.smart.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smart.dao.JsonDAO;
import org.smart.model.Node;
import org.smart.service.JsonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JsonManagerImpl implements JsonManager {
	
	@Autowired
	private JsonDAO jsonDAO;

	@Override
	public List<Node> getNodesList(String url) throws ClientProtocolException, IOException, JSONException {
		JSONArray nodesList = jsonDAO.getJsonArray(url);
		List<Node> nodes = new ArrayList<Node>();
		int nodesLenght=nodesList.length();
		for(int i=0; i<nodesLenght; i++){
			JSONObject item=nodesList.getJSONObject(i);
			nodes.add(new Node(item));
		}
		return nodes;
	}
	
	@Override
	public String getJsonFromUrl(String url) throws ClientProtocolException, IOException, JSONException {
		String results = jsonDAO.getJsonAsString(url);
		return results;
	}
	
	@Override
	public JSONObject getTwitterXMLAsJson(String url, String body, String contentType) throws ClientProtocolException, IOException, JSONException {
		return jsonDAO.getJsonObjectFromXML(url, body, contentType);
	}
}