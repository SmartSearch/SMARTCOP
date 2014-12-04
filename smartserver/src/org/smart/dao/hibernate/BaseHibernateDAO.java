package org.smart.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.smart.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * DAO containing common methods shared with all the other DAOs
 * @author Wladimir Totino
 *
 */
//Annotation that defines the package where the query.properties file resides. It must be in a package to be found by the annotation
@PropertySource("classpath:/org/smart/dao/hibernate/query.properties")
public class BaseHibernateDAO  extends Commons {
    @Autowired
    private SessionFactory sessionFactory;
    
	@Autowired
	private Environment env;
	
	/**
	 * Conveniency method to get the current Hibernate Session
	 * @return
	 */
    protected Session getSession() {
    	return this.sessionFactory.getCurrentSession();
    }
    
    protected Environment getEnvironment() {
    	return this.env;
    }
    
    /**
     * Get a query from the query.properties file contained in org.smart.dao.hibernate package
     * as specified in the PropertySource annotation at the start of the class
     * @param queryName
     * @return
     */
    protected String getQuery(String queryName){
    	return this.env.getProperty("query."+queryName);
    }
}
