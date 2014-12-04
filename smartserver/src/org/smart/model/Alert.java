package org.smart.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.smart.utils.Utils;

import flexjson.JSON;
import flexjson.JSONSerializer;
 
@Entity
@Table(name="alerts")
public class Alert {
     
    @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
    @Column(name="nodeId")
    private Integer nodeId;
    @Column(name="priority")
    private Integer priority;
    @Column(name="status")
    private Integer status;
    @Column(name="text")
    private String text;
    @Column(name="created_at")
	private Timestamp createdAt;
    
    @JSON
	public Integer getId() {
        return id;
    }
	
    public void setId(Integer id) {
        this.id = id;
    }

	/**
	 * @return the nodeId
	 */
    @JSON
	public Integer getNodeId() {
		return nodeId;
	}
	
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	
	/**
	 * @return the priority
	 */
    @JSON
	public Integer getPriority() {
		return priority;
	}
	
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	/**
	 * @return the status
	 */
    @JSON
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * @return the text
	 */
    @JSON
	public String getText() {
		return text;
	}
	
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}    

    /**
	 * @return the createdAt
	 */
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	@JSON
	public String getCreatedAtString(){
		return Utils.formatDate(createdAt, false);
	}
	    
    public String toJsonString(){
    	JSONSerializer serializer=new JSONSerializer();
    	return serializer.serialize(this);
    }
}