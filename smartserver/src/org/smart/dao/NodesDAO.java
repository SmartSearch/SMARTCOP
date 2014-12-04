package org.smart.dao;
 
import java.util.List;

import org.smart.model.Node; 

public interface NodesDAO {
     
    public void addNode(Node node);
    public List<Node> listNodes();
    public Node findOne(Integer id);
    public void removeNode(Integer id);
	void saveNode(Node node);
	public Node findOneByNodeId(Integer nodeId);
}