package org.smart.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.smart.model.Alert;
import org.smart.model.Node;
import org.smart.service.AlertManager;
import org.smart.service.JsonManager;
import org.smart.service.NodesManager;
import org.smart.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
/**
 * Controller for the nodes map dialog
 * TODO: Move all the urls in a more configurable place
 * @author Wladimir Totino
 *
 */
@Controller
@RequestMapping(value = "/nodes")
public class NodesController extends BaseController {
	/* Url of the santander edge node
	 *  TODO: If needed, edit org.smart.task.UpdatesNodesTask to use Edge Nodes saved in the database
	 *  or those from a web service when it will return urls too 
	 */
	public static final String NODES_URL="http://data.smartsantander.eu/SMART";
	
	/* Url of the edge nodes list web service
	 *  TODO: move it in a more configurable place
	 */
	public static final String LOCATIONS_URL="http://95.110.200.220/SmartCopDataCollector/locations.json";
 
    @Autowired
    private JsonManager jsonManager;
    
    @Autowired
    private NodesManager nodesManager;
    
    @Autowired
    private AlertManager alertManager;
 
    /**
     * Main dialog of the Nodes map
     * @param request
     * @param map
     * @return
     * @throws ServletException
     */
    @RequestMapping("/main.htm")
    public String nodesIndex(HttpServletRequest request, Map<String, Object> map) throws ServletException {
        return "nodes/main";
    }
    
    /**
     * retrieves the edge nodes
     * @return
     * @throws ServletException
     * @throws ClientProtocolException
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping("/getlocations.htm")
    public ResponseEntity<String> getLocations() throws ServletException, ClientProtocolException, IOException, JSONException {
        String results;
		try {
			results = this.jsonManager.getJsonFromUrl(LOCATIONS_URL);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"success\" : false, \"message\" : \"authentication-failure\"}", JsonUtils.getJsonHeaders(), HttpStatus.NOT_FOUND);
		} 
        return new ResponseEntity<String>(results, JsonUtils.getJsonHeaders(), HttpStatus.OK);
    }  
    
    /**
     * retrieves the logs
     * @return
     * @throws ServletException
     * @throws ClientProtocolException
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping("/getlogs.htm")
    public ResponseEntity<String> getLogs() throws ServletException, ClientProtocolException, IOException, JSONException {
        String results;
		try {
			results = this.jsonManager.getJsonFromUrl("http://95.110.200.220/SmartCopDataCollector/logs.json");
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"success\" : false, \"message\" : \"authentication-failure\"}", JsonUtils.getJsonHeaders(), HttpStatus.NOT_FOUND);
		} 
        return new ResponseEntity<String>(results, JsonUtils.getJsonHeaders(), HttpStatus.OK);
    }  
    
    /**
     * retrieves the last alert for a certain edge node
     * @param nodeId
     * @return
     * @throws ServletException
     * @throws ClientProtocolException
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping("/getlastalert/{nodeId}.htm")
    public ResponseEntity<String> getLastAlert(@PathVariable("nodeId") String nodeId) throws ServletException, ClientProtocolException, IOException, JSONException {
        String results;
		try {
			results = this.jsonManager.getJsonFromUrl("http://95.110.200.220/SmartCopDataCollector/last_activity.json?loc_id="+nodeId);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"success\" : false, \"message\" : \"authentication-failure\"}", JsonUtils.getJsonHeaders(), HttpStatus.NOT_FOUND);
		} 
        return new ResponseEntity<String>(results, JsonUtils.getJsonHeaders(), HttpStatus.OK);
    }
    
    /**
     * Opens a dialog window showing a map from http://telesto.zapto.org:85/SMART_EdgeNodes_Map/
     * currently unusued
     * @return
     * @throws ServletException
     */
    @RequestMapping("/map.htm")
    public String nodesMap() throws ServletException {
        return "nodes/map";
    }
    
    /**
     * loads a node's sensors from the local database and returns them as JSON string
     * @return
     * @throws ServletException
     */
    @RequestMapping(value="/nodeslistjson.htm", method = RequestMethod.GET)
    public ResponseEntity<String> getNodesList() throws ServletException {
        String nodesList;
		try {
			nodesList = nodesManager.getNodesListAsString();
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"success\" : false, \"message\" : \"authentication-failure\"}", JsonUtils.getJsonHeaders(), HttpStatus.NOT_FOUND);
		} 
        return new ResponseEntity<String>(nodesList, JsonUtils.getJsonHeaders(), HttpStatus.OK);
    }
    
    /**
     * retrieves the alerts 
     * @return
     * @throws ServletException
     * @throws ClientProtocolException
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping(value="/alertslist.htm", method = RequestMethod.GET)
    public ResponseEntity<String> getAlertsList() throws ServletException, ClientProtocolException, IOException, JSONException {
        String results;
		try {
			results = this.jsonManager.getJsonFromUrl("http://95.110.200.220/SmartCopDataCollector/alarms.json");
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"success\" : false, \"message\" : \"authentication-failure\"}", JsonUtils.getJsonHeaders(), HttpStatus.NOT_FOUND);
		} 
        return new ResponseEntity<String>(results, JsonUtils.getJsonHeaders(), HttpStatus.OK);
    }
    
    /**
     * Opens a dialog containing a video from an alert
     * TODO: it currently supports only media automatically played by browsers
     * it doesn't work with other formats. We should find either a player 
     * that supports different formats or that uses a different player for each format 
     * @param request
     * @param model
     * @return
     */
	@RequestMapping(value="/video.htm", method = RequestMethod.GET)
	public String openVideo(HttpServletRequest request, Map<String, Object> model) {
		model.put("url", request.getParameter("url"));
		return "nodes/video";
	}
    
	/**
	 * Retrieves a single node
	 * @param nodeId
	 * @return
	 * @throws ServletException
	 */
    @RequestMapping(value="/getsinglenode/{nodeId}.htm")
    public ResponseEntity<String> getSingleNode(@PathVariable("nodeId") Integer nodeId) throws ServletException {
        Node node;
        String nodeText;
		try {
			node = nodesManager.findOneByNodeId(nodeId);
			if(node!=null){
				nodeText=node.getText();
			} else {
				throw new ServletException("The node doesn't exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("{\"success\" : false, \"message\" : \"failure\"}", JsonUtils.getJsonHeaders(), HttpStatus.NOT_FOUND);
		} 
        return new ResponseEntity<String>(nodeText, JsonUtils.getJsonHeaders(), HttpStatus.OK);
    }
    
    /**
     * retrieves a single alert
     * @param alertId
     * @return
     * @throws ServletException
     */
    @RequestMapping(value="/getsinglealert/{alertId}.htm")
    public ResponseEntity<String> getSingleAlert(@PathVariable("alertId") Integer alertId) throws ServletException {
        Alert alert;
        String alertText;
		try {
			alert = alertManager.findOneByNodeId(alertId);
			if(alert!=null){
				alertText=alert.toJsonString();
			} else {
				throw new ServletException("The alert doesn't exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("{\"success\" : false, \"message\" : \"failure\"}", JsonUtils.getJsonHeaders(), HttpStatus.NOT_FOUND);
		} 
        return new ResponseEntity<String>(alertText, JsonUtils.getJsonHeaders(), HttpStatus.OK);
    }
}