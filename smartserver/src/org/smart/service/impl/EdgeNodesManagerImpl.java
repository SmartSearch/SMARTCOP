package org.smart.service.impl;

import java.util.Iterator;
import java.util.List;

import org.smart.dao.EdgeNodesDAO;
import org.smart.model.EdgeNode;
import org.smart.service.EdgeNodesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EdgeNodesManagerImpl implements EdgeNodesManager {

	@Autowired
	private EdgeNodesDAO edgeNodeDao;

	@Transactional
	public void addEdgeNode(EdgeNode edgeNode) {
		edgeNodeDao.addEdgeNode(edgeNode);
	}

	@Transactional
	public List<EdgeNode> listEdgeNodes() {
		return edgeNodeDao.listEdgeNodes();
	}

	@Transactional
	public void removeEdgeNode(Integer id) {
		edgeNodeDao.removeEdgeNode(id);
	}
	
	@Transactional
	@Override
	public void saveEdgeNode(EdgeNode edgeNode){
		edgeNodeDao.saveEdgeNode(edgeNode);
	}
	
	@Transactional
	@Override
	public EdgeNode findOne(Integer id){
		return edgeNodeDao.findOne(id);
	}

	@Override
	public String getEdgeNodesListAsString() {
		List<EdgeNode> edgeNodes=this.listEdgeNodes();
		String str="[";
		Iterator<EdgeNode> it=edgeNodes.iterator();
		while(it.hasNext()){
			EdgeNode next=it.next();
			str+=next.toJsonString();
			if(it.hasNext()){
				str+=",";
			}
		}
		str+="]";
		return str;
	}
}