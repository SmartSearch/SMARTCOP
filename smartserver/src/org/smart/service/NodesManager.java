package org.smart.service;

import java.util.List;

import org.json.JSONException;
import org.smart.model.Node;


public interface NodesManager {     
    public void addNode(Node node);
    public List<Node> listNodes();
    public void removeNode(Integer id);
	void saveNode(Node node);
	public String getNodesListAsString() throws JSONException;
	Node findOne(Integer id);
	public Node findOneByNodeId(Integer nodeId);
}
