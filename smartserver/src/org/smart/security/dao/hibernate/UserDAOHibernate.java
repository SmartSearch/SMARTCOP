package org.smart.security.dao.hibernate;
 
import java.util.HashMap;
import java.util.Map;

import org.smart.dao.hibernate.BaseHibernateDAO;
import org.smart.security.dao.UserDAO;
import org.smart.security.model.SecurityUser;
import org.smart.utils.HibernateQueryUtils;
import org.springframework.stereotype.Repository;

/**
 * Hibernate implementation of UserDAO
 * @author Wladimir Totino
 *
 */
@Repository
public class UserDAOHibernate extends BaseHibernateDAO implements UserDAO {
 
	@Override
	public SecurityUser findByName(String username) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("username", username);
		return HibernateQueryUtils.genericUniqueQuery("from SecurityUser where username = :username ", params, this.getSession());
	}
}