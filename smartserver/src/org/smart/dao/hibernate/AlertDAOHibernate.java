package org.smart.dao.hibernate;
 
import java.util.List;

import org.smart.dao.AlertDAO;
import org.smart.model.Alert;
import org.smart.utils.HibernateQueryUtils;
import org.springframework.stereotype.Repository;
 
@Repository
public class AlertDAOHibernate extends BaseHibernateDAO implements AlertDAO {
 
    public void addAlert(Alert alert) {
        this.getSession().save(alert);
    }
 
	public List<Alert> listAlert() {
        return  HibernateQueryUtils.genericListQuery(this.getQuery("listAlert"), this.getSession());
    }
 
    public void removeAlert(Integer id) {
        HibernateQueryUtils.deleteOneById(id, Alert.class, this.getSession());
    }

	@Override
	public Alert findOne(Integer id) {
		return HibernateQueryUtils.getOneById(id, Alert.class, this.getSession());
	}
}