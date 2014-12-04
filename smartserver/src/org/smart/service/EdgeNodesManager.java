package org.smart.service;

import java.util.List;

import org.smart.model.EdgeNode;


public interface EdgeNodesManager {     
    public void addEdgeNode(EdgeNode edgeNode);
    public List<EdgeNode> listEdgeNodes();
    public void removeEdgeNode(Integer id);
	void saveEdgeNode(EdgeNode edgeNode);
	public String getEdgeNodesListAsString();
	EdgeNode findOne(Integer id);
}
