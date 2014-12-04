package org.smart.dao.json;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.smart.dao.JsonDAO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Current implementation of the JsonDAO that allows data retrieval from JSON sources
 * @author Wladimir Totino
 *
 */
@Repository
public class JsonImplDAO implements JsonDAO {
	
	/**
	 * Generates an http entity for apache http client for a get request
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private HttpEntity getHttpEntity(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		return entity;
	}
	
	/**
	 * Generates an http entity for apache http client for a put request
	 * @param url
	 * @param body
	 * @param contentType
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private HttpEntity getHttpPutEntity(String url, String body, String contentType) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPut httpput = new HttpPut(url);
		httpput.setHeader("Content-Type", contentType+"; charset=utf-8");
		StringEntity bodyEntity = new StringEntity(body);
		httpput.setEntity(bodyEntity);
		CloseableHttpResponse response = httpclient.execute(httpput);
		HttpEntity entity = response.getEntity();
		return entity;
	}
	
	@Override
	public JSONObject getJsonObjectFromXML(String url, String body, String contentType) throws ClientProtocolException, IOException, JSONException {
		HttpEntity entity = this.getHttpPutEntity(url, body, contentType);
		if (entity != null) {
			return XML.toJSONObject(EntityUtils.toString(entity, Charset.forName("UTF-8")));
		}
		throw new JSONException("There was an error trying to complete the request");
	}
	
	public JSONArray getJsonArray(String url) throws ClientProtocolException, IOException, JSONException {
		HttpEntity entity = this.getHttpEntity(url);
		if (entity != null) {
			return new JSONArray(EntityUtils.toString(entity));
		}
		throw new JSONException("There was an error trying to complete the request");
	}
	
	public String getJsonAsString(String url) throws ClientProtocolException, IOException, JSONException {
		HttpEntity entity = this.getHttpEntity(url);
		if (entity != null) {
			return EntityUtils.toString(entity);
		}
		throw new JSONException("There was an error trying to complete the request");
	}
}
