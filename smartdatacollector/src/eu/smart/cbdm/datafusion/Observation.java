/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.datafusion;

import java.sql.Timestamp;

/**
 *
 * @author albert
 */
public class Observation {
    public String id_location = null;
    public String id_observation = null;
    public String multimedia = null;
    public Timestamp activity = null;
    public Double crowd_score = 0.0;
    public Double crowd_density = 0.0;
    public Double sound_level = 0.0;
    public Double plause_score = 0.0;
    public Double traffic_score = 0.0;
    public Double music_score = 0.0;
    public Double temperature = 0.0;
    public String behaviour = null;
	public String getId_location() {
		return id_location;
	}
	public void setId_location(String id_location) {
		this.id_location = id_location;
	}
	public String getId_observation() {
		return id_observation;
	}
	public void setId_observation(String id_observation) {
		this.id_observation = id_observation;
	}
	public String getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(String multimedia) {
		this.multimedia = multimedia;
	}
	public Timestamp getActivity() {
		return activity;
	}
	public void setActivity(Timestamp activity) {
		this.activity = activity;
	}
	public Double getCrowd_score() {
		return crowd_score;
	}
	public void setCrowd_score(Double crowd_score) {
		this.crowd_score = crowd_score;
	}
	public Double getCrowd_density() {
		return crowd_density;
	}
	public void setCrowd_density(Double crowd_density) {
		this.crowd_density = crowd_density;
	}
	public Double getSound_level() {
		return sound_level;
	}
	public void setSound_level(Double sound_level) {
		this.sound_level = sound_level;
	}
	public Double getPlause_score() {
		return plause_score;
	}
	public void setPlause_score(Double plause_score) {
		this.plause_score = plause_score;
	}
	public Double getTraffic_score() {
		return traffic_score;
	}
	public void setTraffic_score(Double traffic_score) {
		this.traffic_score = traffic_score;
	}
	public Double getMusic_score() {
		return music_score;
	}
	public void setMusic_score(Double music_score) {
		this.music_score = music_score;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public String getBehaviour() {
		return behaviour;
	}
	public void setBehaviour(String behaviour) {
		this.behaviour = behaviour;
	}

    @Override
    public String toString(){
        return "[crowd_score:"+crowd_score+"][crowd_density"+crowd_density+"][sound_level"+sound_level+
        		"][plause_score"+plause_score+"][traffic_score"+traffic_score+"][music_score"+music_score+
        		"][temperature"+temperature+"]";
    }

    
}
