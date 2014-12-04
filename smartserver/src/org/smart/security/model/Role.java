package org.smart.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model Object representing a role (or credential) in the database, 
 * a role can be linked to a single user or to a group of users
 * @author Wladimir Totino
 *
 */
@Entity
@Table(name="user_roles")
public class Role {
	
	@Id
    @Column(name="id")
	@GeneratedValue
	private Integer id;
	@Column(name="authority")
	private String authority;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the autority
	 */
	public String getAuthority() {
		return authority;
	}
	/**
	 * @param autority the autority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
