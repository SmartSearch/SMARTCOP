package org.smart.service.impl;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.smart.dao.NodesDAO;
import org.smart.model.Node;
import org.smart.service.NodesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NodesManagerImpl implements NodesManager {

	@Autowired
	private NodesDAO nodeDao;

	@Transactional
	public void addNode(Node node) {
		nodeDao.addNode(node);
	}

	@Transactional
	public List<Node> listNodes() {
		return nodeDao.listNodes();
	}

	@Transactional
	public void removeNode(Integer id) {
		nodeDao.removeNode(id);
	}
	
	@Transactional
	@Override
	public void saveNode(Node node){
		nodeDao.saveNode(node);
	}
	
	@Transactional
	@Override
	public Node findOne(Integer id){
		return nodeDao.findOne(id);
	}

	@Override
	public String getNodesListAsString() throws JSONException {
		List<Node> nodes=this.listNodes();
		String str="[";
		Iterator<Node> it=nodes.iterator();
		while(it.hasNext()){
			Node next=it.next();
			str+=next.getTextWithId();
			if(it.hasNext()){
				str+=",";
			}
		}
		str+="]";
		return str;
	}

	@Transactional
	@Override
	public Node findOneByNodeId(Integer nodeId) {
		return nodeDao.findOneByNodeId(nodeId);
	}
}