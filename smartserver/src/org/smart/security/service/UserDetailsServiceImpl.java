package org.smart.security.service;

import org.smart.security.dao.UserDAO;
import org.smart.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;    
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of default Spring Security UserDetailsService, this class allows to retrieve users from a Database
 * rather than from an xml file
 * @author Wladimir Totino
 *
 */
@Service("userDetailsService") 
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired private UserDAO dao;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		SecurityUser userEntity = dao.findByName(username);
		if (userEntity == null){
			throw new UsernameNotFoundException("User not found");
		}
		
		UserDetails user = userEntity.getDetails();

		return user;
	}
}