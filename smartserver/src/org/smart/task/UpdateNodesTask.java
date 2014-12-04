package org.smart.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smart.model.Node;
import org.smart.service.JsonManager;
import org.smart.service.NodesManager;
import org.smart.web.NodesController;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateNodesTask {
	@Autowired
	private JsonManager jsonManager;
	@Autowired
	private NodesManager nodesManager;

	/**
	 * This class implements a task that should update the database with data from an Edge Node
	 * It is currently disabled because the current application uses an external web service to retrieve data
	 * so this is not needed anymore, the current web service updates only edge nodes and alerts data but not the sensors 
	 * data, which is coming instead from the internal database. Once there is a proper web service for sensors data
	 * this class should be modified accordingly, or just use the web service to load JSON data like it's done with other
	 * data. To reactivate the task uncomment the "Scheduled" line and add a @ so Spring will recognize it as an annotation
	 * for a task and change the boolean run variable to true
	 * The task is always active but if the task is being stopped for some reason, it will start again one second after. 
	 * It pools the data source every 5 seconds. It would be best to have a streaming data source using JWebSocket
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws Exception
	 */
	//Scheduled(fixedDelay=1000)
	public void executeTask() throws InterruptedException, ClientProtocolException, IOException, JSONException{
		boolean run=false;
		String nodesList;
		List<Node> currentNodesList;
		Map<String, Node> currentNodes;
		while(run){
			System.out.println("Updating Nodes");
			currentNodesList = new ArrayList<Node>();
			currentNodes=new HashMap<String, Node>();
			nodesList = jsonManager.getJsonFromUrl(NodesController.NODES_URL);
			JSONArray nodesArray=new JSONArray(nodesList);
			int nodesListlength=nodesArray.length();
			currentNodesList=nodesManager.listNodes();
			int currentNodesLength=currentNodesList.size();
			for(int i=0; i<currentNodesLength; i++){
				currentNodes.put(currentNodesList.get(i).getNodeId().toString(), currentNodesList.get(i));
			}
			
			//Takes items from the array and compares them to data in the database
			for(int i=0; i<nodesListlength; i++){
				JSONObject item=nodesArray.getJSONObject(i);
				Node current=currentNodes.get(new Integer(item.getInt("nodeId")).toString());
				//If the node doesn't exist or is different than the actual node
				if(current==null){
					System.out.println("Adding new node with ID: "+item.getInt("nodeId"));
					Node newNode=new Node(item);
					nodesManager.addNode(newNode);
				} else if(!current.coompareJson(item.toString())){
					System.out.println("Updating exsisting node with ID: "+item.getInt("nodeId"));
					current.setDataFromJsonObject(item);
					nodesManager.saveNode(current);
				}
			}
			Thread.sleep(5000);
		}
	}
}