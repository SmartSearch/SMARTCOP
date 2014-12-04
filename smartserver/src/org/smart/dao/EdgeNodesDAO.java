package org.smart.dao;
 
import java.util.List;

import org.smart.model.EdgeNode; 

public interface EdgeNodesDAO {
     
    public void addEdgeNode(EdgeNode edgeNode);
    public List<EdgeNode> listEdgeNodes();
    public EdgeNode findOne(Integer id);
    public void removeEdgeNode(Integer id);
	void saveEdgeNode(EdgeNode edgeNode);
}