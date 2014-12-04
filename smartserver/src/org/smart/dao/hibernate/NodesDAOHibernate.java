package org.smart.dao.hibernate;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.smart.dao.NodesDAO;
import org.smart.model.Node;
import org.smart.utils.HibernateQueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
 
@Repository
public class NodesDAOHibernate extends BaseHibernateDAO implements NodesDAO {
	
	@Autowired
	private ApplicationContext context;
 
    public void addNode(Node node) {
        this.getSession().save(node);
    }
    
    @Override
    public void saveNode(Node node){
    	this.getSession().saveOrUpdate(node);
    }
 
	public List<Node> listNodes() {
		String query=this.getQuery("listNodes");
		return  HibernateQueryUtils.genericListQuery(query, this.getSession());
    }
 
    public void removeNode(Integer id) {
        HibernateQueryUtils.deleteOneById(id, Node.class, this.getSession());
    }

	@Override
	public Node findOne(Integer id) {
		return HibernateQueryUtils.getOneById(id, Node.class, this.getSession());
	}

	@Override
	public Node findOneByNodeId(Integer nodeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nodeId", nodeId);
		return  HibernateQueryUtils.genericUniqueQuery(this.getQuery("findOneByNodeId"), params, this.getSession());
	}
}