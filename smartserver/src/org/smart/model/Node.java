package org.smart.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;
import org.smart.utils.Utils;

import flexjson.JSON;

@Entity
@Table(name="nodes")
public class Node {

	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;

	@Column(name="node_id")
	private Integer nodeId;
	@Column(name="json_string")
	private String text;
	@Column(name="date")
	private Timestamp date;
	@Column(name="type")
	private String type;
	@Column(name="latitude")
	private Double latitude;
	@Column(name="longitude")
	private Double longitude;
	@Column(name="battery")
	private Double battery;
	@Column(name="temperature")
	private Double temperature;
	@Column(name="noise")
	private Double noise;
	@Column(name="light")
	private Double light;
	@ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "edge_node_id")
	private EdgeNode edgeNode;

	public Node(){}

	public Node(JSONObject item) throws JSONException{
		this.setDataFromJsonObject(item);
	}

	public void setDataFromJsonObject(JSONObject item) throws JSONException {
		this.text=item.toString();
		this.nodeId=item.getInt("nodeId");
		this.latitude=item.getDouble("latitude");
		this.longitude=item.getDouble("longitude");
		this.date=Timestamp.valueOf(item.getString("date"));
		this.type=item.getString("type");
		this.battery=item.getDouble("battery");
		//These values might be null
		try {
			this.temperature=item.getDouble("temperature");
		} catch(JSONException e){
			this.temperature=null;
		}

		try {
			this.noise=item.getDouble("noise");
		} catch(JSONException e){
			this.noise=null;
		}

		try {
			this.light=item.getDouble("light");
		} catch(JSONException e){
			this.light=null;
		}		
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

	/**
	 * @return the type
	 */
	@JSON
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the battery
	 */
	@JSON
	public Double getBattery() {
		return battery;
	}

	/**
	 * @param battery the battery to set
	 */
	public void setBattery(Double battery) {
		this.battery = battery;
	}

	/**
	 * @return the temperature
	 */
	@JSON
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the noise
	 */
	@JSON
	public Double getNoise() {
		return noise;
	}

	/**
	 * @param noise the noise to set
	 */
	public void setNoise(Double noise) {
		this.noise = noise;
	}

	/**
	 * @return the light
	 */
	@JSON
	public Double getLight() {
		return light;
	}

	/**
	 * @param light the light to set
	 */
	public void setLight(Double light) {
		this.light = light;
	}

	/**
	 * @return the latitude
	 */
	@JSON
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	@JSON
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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
	 * @return the text
	 */
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
	 * @return the edgeNode
	 */
	public EdgeNode getEdgeNode() {
		return edgeNode;
	}

	/**
	 * @param edgeNode the edgeNode to set
	 */
	public void setEdgeNode(EdgeNode edgeNode) {
		this.edgeNode = edgeNode;
	}

	/**
	 * @return the date
	 */
	@JSON
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	@JSON
	public String getDateString(){
		return Utils.formatDate(date, false);
	}

	public String toString(){
		return text;
	}

	public boolean coompareJson(String jsonString) {
		return this.getText().equals(jsonString);
	}

	public String getTextWithId() throws JSONException {
		JSONObject jsonNode = new JSONObject(this.text);
		jsonNode.put("edgeNodeId", this.getEdgeNode().getId());
		return jsonNode.toString();
	}
}
