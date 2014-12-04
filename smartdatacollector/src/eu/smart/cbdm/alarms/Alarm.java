/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.alarms;

import eu.smart.cbdm.datafusion.Location;
import eu.smart.cbdm.datafusion.Observation;
import eu.smart.cbdm.logs.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author albert
 */
public class Alarm implements Comparable<Alarm> {
    
    public static String SEVERITY_INFO = "INFO";
    public static String SEVERITY_ALERT = "ALERT";
    public static String SEVERITY_CRITICAL = "CRITICAL";
    public static String SEVERITY_ERROR = "ERROR";
    
    
    public static String ALARM_NEW_EVENT = "New event detected in: ";
    

    public Location location = null;
    public Observation observation = null;
    
    public int id;
    public int color = -1;
    public String severity = null;
    public String datetime = null;
    public Date raised = null;
    public String message = null;
    public String description = null;
    public long time_in_mills = 0;
    public int score = 0;
    public boolean mayBeMusic = false;
    public boolean plauseDetected = false;
    public String trend_analysys="no relevat data from trend analysys";

    public Alarm() {
        time_in_mills = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat dt = new SimpleDateFormat("[dd-MM-yyyy hh:mm:ss]");
        raised = new Date(time_in_mills);
        datetime = dt.format(raised);
    }
    
    

    @Override
    public int compareTo(Alarm t) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        int ret_val = 0;

        if (t.time_in_mills < this.time_in_mills) {
            ret_val = AFTER;
        }
        if (t.time_in_mills > this.time_in_mills) {
            ret_val = BEFORE;
        }


        return ret_val;

    }
    
    
    @Override
    public String toString(){
        return "["+datetime+"]["+severity+"]"+message;
    }



	public Location getLocation() {
		return location;
	}



	public void setLocation(Location location) {
		this.location = location;
	}



	public Observation getObservation() {
		return observation;
	}



	public void setObservation(Observation observation) {
		this.observation = observation;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getSeverity() {
		return severity;
	}



	public void setSeverity(String severity) {
		this.severity = severity;
		if(severity.equals(SEVERITY_INFO)) color = 0;
		if(severity.equals(SEVERITY_ALERT)) color = 1;
		if(severity.equals(SEVERITY_ERROR)) color = 2;
		if(severity.equals(SEVERITY_CRITICAL)) color = 3;
	}



	public String getDatetime() {
		return datetime;
	}



	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}



	public Date getRaised() {
		return raised;
	}



	public void setRaised(Date raised) {
		this.raised = raised;
		time_in_mills = raised.getTime();
        SimpleDateFormat dt = new SimpleDateFormat("[dd-mm-yyyy hh:mm:ss]");
        datetime = dt.format(raised);
		
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}



	public boolean isMayBeMusic() {
		return mayBeMusic;
	}



	public void setMayBeMusic(boolean mayBeMusic) {
		this.mayBeMusic = mayBeMusic;
	}



	public boolean isPlauseDetected() {
		return plauseDetected;
	}



	public void setPlauseDetected(boolean plauseDetected) {
		this.plauseDetected = plauseDetected;
	}



	public int getColor() {
		return color;
	}



	public void setColor(int color) {
		this.color = color;
	}



	public String getTrend_analysys() {
		return trend_analysys;
	}



	public void setTrend_analysys(String trend_analysys) {
		this.trend_analysys = trend_analysys;
	}
	

	
}
