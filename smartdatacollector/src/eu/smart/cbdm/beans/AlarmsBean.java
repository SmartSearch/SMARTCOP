package eu.smart.cbdm.beans;

import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.smart.cbdm.alarms.Alarm;
import eu.smart.cbdm.datafusion.Observation;
import eu.smart.cdbm.dao.AlarmDAO;

@SessionScoped
@ManagedBean
public class AlarmsBean {
	
	
	
	private List<Alarm> alarms;
	private List<Observation> observations;
	private String location_id;
	private List<Alarm> alarms_per_location;
	private String media_to_be_shown="http://opensoftware.smartfp7.eu/video/iphone/s1417560164.mp4";
	
	public List<Alarm> getAlarms() {
		AlarmDAO _dao = new AlarmDAO();
		alarms = _dao.getAlarms(20);
		return alarms;
	}
	
	
	public void showMedia(int alarm_id ){
		AlarmDAO _dao = new AlarmDAO();
		Iterator<Alarm> _iter = alarms.iterator();
		while(_iter.hasNext()){
			Alarm alarm = _iter.next();
			if(alarm.getId()==alarm_id) media_to_be_shown = alarm.getObservation().getMultimedia();
		}
	}
	

	public void setAlarms(List<Alarm> alarms) {
		this.alarms = alarms;
	}

	public List<Observation> getObservations() {
		return observations;
	}

	public void setObservations(List<Observation> observations) {
		this.observations = observations;
	}

	public String getLocation() {
		return location_id;
	}

	public void setLocation(String location_id) {
		this.location_id = location_id;
	}
	
	
	public void showLocation(String location_id){
		setLocation(location_id);
		
	}



	public String getLocation_id() {
		return location_id;
	}



	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}



	public List<Alarm> getAlarms_per_location() {
		
		AlarmDAO _dao = new AlarmDAO();
		alarms_per_location = _dao.getAlarmsByLocation(location_id,20);
		return alarms_per_location;
	}



	public void setAlarms_per_location(List<Alarm> alarms_per_location) {
		this.alarms_per_location = alarms_per_location;
	}


	public String getMedia_to_be_shown() {
		return media_to_be_shown;
	}


	public void setMedia_to_be_shown(String media_to_be_shown) {
		this.media_to_be_shown = media_to_be_shown;
	}
	
	

}
