package org.smart.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import flexjson.JSON;
import flexjson.JSONSerializer;

@Entity
@Table(name="edge_nodes")
public class EdgeNode {
	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	
	@Column(name="name")
	private String name;
	@Column(name="url")
	private String url;
	@Column(name="last_update")
	private Timestamp lastUpdate;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nodeId")
	private List<Node> nodes;
	
	/**
	 * @return the id
	 */
	@JSON
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
	 * @return the name
	 */
	@JSON
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
	 * @return the url
	 */
	@JSON
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the lastUpdate
	 */
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	/**
	 * @return the nodes
	 */
	public List<Node> getNodes() {
		return nodes;
	}
	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
    public String toJsonString(){
    	JSONSerializer serializer=new JSONSerializer();
    	return serializer.serialize(this);
    }
}
