package org.smart.dao.hibernate;
 
import java.util.List;
import org.smart.dao.EdgeNodesDAO;
import org.smart.model.EdgeNode;
import org.smart.utils.HibernateQueryUtils;
import org.springframework.stereotype.Repository;
 
@Repository
public class EdgeNodesDAOHibernate extends BaseHibernateDAO implements EdgeNodesDAO {
 
    public void addEdgeNode(EdgeNode edgeNode) {
        this.getSession().save(edgeNode);
    }
    
    @Override
    public void saveEdgeNode(EdgeNode edgeNode){
    	this.getSession().saveOrUpdate(edgeNode);
    }
 
	public List<EdgeNode> listEdgeNodes() {
        return  HibernateQueryUtils.genericListQuery(this.getQuery("listEdgeNodes"), this.getSession());
    }
 
    public void removeEdgeNode(Integer id) {
        HibernateQueryUtils.deleteOneById(id, EdgeNode.class, this.getSession());
    }

	@Override
	public EdgeNode findOne(Integer id) {
		return HibernateQueryUtils.getOneById(id, EdgeNode.class, this.getSession());
	}
}