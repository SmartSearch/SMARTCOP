package org.smart.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Model Object representing a user in the database
 * @author Wladimir Totino
 *
 */
@Entity
@Table(name="users")
public class SecurityUser {
	
	@Id
    @Column(name="id")
	@GeneratedValue
	private Integer id;
	
	@Column(name="username")
	private String name;
	
	@Column(name="password")
	private String password;
	
	@Column(name="enabled")
	private boolean isActive;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = " rel_user_roles", joinColumns = { @JoinColumn(name = "id_user") }, inverseJoinColumns = { @JoinColumn(name = "id_role") })
	private Set<Role> roles=new HashSet<Role>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = " rel_user_groups", joinColumns = { @JoinColumn(name = "id_user") }, inverseJoinColumns = { @JoinColumn(name = "id_group") })
	private Set<Group> groups=new HashSet<Group>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Set<Role> getRoles(){
		return this.roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the groups
	 */
	public Set<Group> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

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

	public User getDetails() {
		String username = this.getName();
		String password = this.getPassword();
		boolean enabled = this.isActive();
		boolean accountNonExpired = this.isActive();
		boolean credentialsNonExpired = this.isActive();
		boolean accountNonLocked = this.isActive();

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : this.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		//Aggiunge i ruoli dei gruppi
		for (Group group : this.getGroups()) {
			Set<Role> roles=group.getRoles();
			for (Role role : roles) {
				authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
			}
		}

		User user = new User(username, password, enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		return user;
	}

}
