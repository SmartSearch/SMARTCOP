package org.smart.web;

import java.util.Map;

import javax.servlet.ServletException;

import org.smart.exceptions.WebErrorException;
import org.smart.model.EdgeNode;
import org.smart.service.EdgeNodesManager;
import org.smart.utils.JsonUtils;
import org.smart.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
/**
 * Controller for actions related to edge nodes manager page, currently disabled in the interface
 * @author Wladimir Totino
 *
 */
@Controller
@RequestMapping(value = "/edgenodes")
public class EdgeNodesController extends BaseController {
 
    @Autowired
    private EdgeNodesManager edgeNodesManager;

    /**
     * Opens the edge nodes manager dialog
     * @param map
     * @return
     * @throws WebErrorException
     */
    @RequestMapping("/edgenodesmanager.htm")
    public String listEdgeNodes(Map<String, Object> map) throws WebErrorException {
        map.put("edgeNode", new EdgeNode());
        map.put("edgeNodesList", edgeNodesManager.listEdgeNodes());
        map.put("edit", new Boolean(false));
        return "edgenodes/edgenodesmanager";
    }
 
    /**
     * inserts a new edge node
     * @param edgeNode
     * @param result
     * @return
     */
    @RequestMapping(value = "/addedgenode.htm", method = RequestMethod.POST)
    public String addEdgeNode(@ModelAttribute("edgeNode") EdgeNode edgeNode, BindingResult result) {
    	edgeNode.setLastUpdate(Utils.getNow());
    	edgeNodesManager.saveEdgeNode(edgeNode);
        return "redirect:/edgenodes/edgenodesmanager.htm";
    }
 
    /**
     * Deletes an edge node
     * @param edgeNodeId
     * @return
     */
    @RequestMapping("/delete/{edgeNodeId}.htm")
    public String deleteEdgeNode(@PathVariable("edgeNodeId") Integer edgeNodeId) {
    	if(edgeNodeId!=1){
    		edgeNodesManager.removeEdgeNode(edgeNodeId);
    	}
        return "redirect:/edgenodes/edgenodesmanager.htm";
    }
    
    /**
     * Edits an edge node
     * @param edgeNodeId
     * @param map
     * @return
     * @throws WebErrorException
     */
    @RequestMapping("/edit/{edgeNodeId}.htm")
    public String editEdgeNode(@PathVariable("edgeNodeId") Integer edgeNodeId, Map<String, Object> map) throws WebErrorException {
    	EdgeNode edgeNode=edgeNodesManager.findOne(edgeNodeId);
    	if(edgeNode==null){
    		throw new WebErrorException("Edge Node not found");
    	}
    	map.put("edit", new Boolean(true));
    	map.put("edgeNode", edgeNode);
    	map.put("edgeNodesList", edgeNodesManager.listEdgeNodes());

    	return "edgenodes/edgenodesmanager";
    }
    
    /**
     * Retrieves the edge nodes list as JSON string
     * @return
     * @throws ServletException
     */
    @RequestMapping(value="/edgenodeslistjson.htm", method = RequestMethod.GET)
    public ResponseEntity<String> getNodesList() throws ServletException {
        String edgeNodesList;
		try {
			edgeNodesList = edgeNodesManager.getEdgeNodesListAsString();
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"success\" : false, \"message\" : \"failure\"}", JsonUtils.getJsonHeaders(), HttpStatus.NOT_FOUND);
		} 
        return new ResponseEntity<String>(edgeNodesList, JsonUtils.getJsonHeaders(), HttpStatus.OK);
    }
}