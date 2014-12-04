package org.smart.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Utility Class used to generate queries or retrieve data through Hibernate queries
 * @author Wladimir Totino
 *
 */
@SuppressWarnings("unchecked")
public class HibernateQueryUtils {
	
	/**
	 * Returns a query without parameters 
	 * @param queryString
	 * @param session
	 * @return
	 */
	public static Query genericQuery(String queryString, Session session){
		return HibernateQueryUtils.genericQuery(queryString, new HashMap<String, Object>(), session);
	}
	
	/**
	 * Returns a query with parameters 
	 * @param queryString
	 * @param session
	 * @param params
	 * @return
	 */
	public static Query genericQuery(String queryString, Map<String, Object> params, Session session){
		Query query=session.createQuery(queryString);
		for (Map.Entry<String, Object> entry : params.entrySet()) {
		    query.setParameter(entry.getKey(), entry.getValue());
		}
		return query;
	}
	
	/**
	 * Returns a single object retrieved through a query without parameters
	 * @param queryString
	 * @param session
	 * @return
	 */
    public static <T> T genericUniqueQuery(String queryString, Session session){
		return genericUniqueQuery(queryString, new HashMap<String, Object>(), session);
	}
    
    /**
	 * Returns a single object retrieved through a query with parameters
	 * @param queryString
	 * @param session
	 * @param params
	 * @return
	 */
    public static <T> T genericUniqueQuery(String queryString, Map<String, Object> params, Session session){
		T item=(T)genericQuery(queryString, params, session).uniqueResult();
		return item;
	}
	
    /**
	 * Returns a list of objects retrieved through a query without parameters
	 * @param queryString
	 * @param session
	 * @return
	 */
    public static <T> List<T> genericListQuery(String queryString, Session session){
		return genericListQuery(queryString, new HashMap<String, Object>(), session);
	}
	
    /**
     * Returns a list of objects retrieved through a query with parameters
     * @param queryString
     * @param params
     * @param session
     * @return
     */
    public static <T> List<T> genericListQuery(String queryString, Map<String, Object> params, Session session){
		List<T> items = (List<T>)genericQuery(queryString, params, session).list();
		return items;
	}
    
    /**
     * retrieves a single object using the element id
     * @param id
     * @param modelClass the model object used to represent the table we are retrieving data from
     * @param session
     * @return
     */
	public static <T> T getOneById(int id, Class<T> modelClass, Session session) {
		return (T) session.get(modelClass, id);
	}

	/**
	 * deletes a single object using the element id
     * @param id
     * @param modelClass the model object used to represent the table we are deleting data from
	 * @param session
	 */
	public static <T> void deleteOneById(int id, Class<T> modelClass, Session session) {
		T item=HibernateQueryUtils.getOneById(id, modelClass, session);
		if (item!=null) {
        	session.delete(item);
        }
	}

}
