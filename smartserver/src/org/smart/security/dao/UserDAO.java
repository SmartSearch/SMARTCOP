package org.smart.security.dao;
 
import org.smart.security.model.SecurityUser;
 
/**
 * DAO Class used to retrieve users data for the login 
 * @author Wladimir Totino
 *
 */
public interface UserDAO {
    
	/**
	 * Retrieves an user based on its username
	 * @param username
	 * @return
	 */
    public SecurityUser findByName(String username);
}