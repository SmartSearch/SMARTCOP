/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.datafusion;

import eu.smart.cbdm.alarms.Alarm;
import eu.smart.cdbm.dao.DBException;
import eu.smart.cdbm.dao.TrendDAO;

/**
 *
 * @author albert
 */
public class DataAnalyzer {

	public String alarm_serverity = null;
	public String crowd_behaviour = null;
	public Observation observation = null;
	public int score = 0;
    public boolean mayBeMusic = false;
    public boolean plauseDetected = false;

	private double crowd_density_threshold = 0.8;
	private double temperature_threshold = 28;
	private double traffic_score_threshold = 0.3;
	private double sound_level_threshold = 0.5;
	private double crowd_score_threshold = 0.5;
	private double music_score_threshold = 0.1;
	private double plause_score_threshold= 0.1;

	private final int CROWD_DENSITY_SCORE = 8;
	private final int CROWD_SOUND_SCORE = 16;
	private final int SOUND_LEVEL = 128;
	private final int TRAFFIC_SOUND_SCORE = 256;
	private final int TEMPERATURE_LEVEL = 512;
	private final int TRAFFIC_SCORE = 512;

	public DataAnalyzer(Observation _obs) {
		observation = _obs;
		parseObservation();

	}

	private void parseObservation() {

		score = 0;

		if (observation.crowd_density > crowd_density_threshold)
			score = score + CROWD_DENSITY_SCORE;
		if (observation.temperature > temperature_threshold)
			score = score + TEMPERATURE_LEVEL;
		if (observation.traffic_score > traffic_score_threshold)
			score = score + TRAFFIC_SOUND_SCORE;
		if (observation.sound_level > sound_level_threshold)
			score = score + SOUND_LEVEL;
		if (observation.crowd_score > crowd_score_threshold)
			score = score + CROWD_SOUND_SCORE;

		mayBeMusic = observation.music_score >= music_score_threshold;
		plauseDetected= observation.plause_score >= plause_score_threshold;
		
		
		TrendDAO _trenddao = new TrendDAO();

		switch (score) {



		case CROWD_DENSITY_SCORE:
			alarm_serverity = Alarm.SEVERITY_ALERT;
			observation.behaviour = "Crowd pressure is over threshold";
			try {
				_trenddao.insertTrend(true, false, false, false, false, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case SOUND_LEVEL:
			alarm_serverity = Alarm.SEVERITY_ALERT;
			observation.behaviour = "Sound Level is over threshold";
			try {
				_trenddao.insertTrend(false, false, false, false, true, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case TEMPERATURE_LEVEL:
			alarm_serverity = Alarm.SEVERITY_INFO;
			observation.behaviour = "Temperature in the area is very high but no crowd detected";
			try {
				_trenddao.insertTrend(false, true, false, false, false, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case TRAFFIC_SOUND_SCORE:
			alarm_serverity = Alarm.SEVERITY_ALERT;
			observation.behaviour = "Noisy traffic nearby the area";
			try {
				_trenddao.insertTrend(false, false, true, false, false, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CROWD_DENSITY_SCORE + TEMPERATURE_LEVEL:
			alarm_serverity = Alarm.SEVERITY_ALERT;
			observation.behaviour = "Crowd pressure is over threshold and temperature is very high";
			try {
				_trenddao.insertTrend(true, true, false, false, false, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CROWD_DENSITY_SCORE + TEMPERATURE_LEVEL + SOUND_LEVEL:
			alarm_serverity = Alarm.SEVERITY_ALERT;
			observation.behaviour = "Crowd pressure is over threshold and temperature is very high. Sound is over threshold";
			try {
				_trenddao.insertTrend(true, true, false, false, true, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CROWD_SOUND_SCORE + SOUND_LEVEL:
			alarm_serverity = Alarm.SEVERITY_ALERT;
			observation.behaviour = "Noisy crowd detectd nearby the area ";
			try {
				_trenddao.insertTrend(false, false, false, true, true, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CROWD_SOUND_SCORE:
			alarm_serverity = Alarm.SEVERITY_ALERT;
			observation.behaviour = "Noisy crowd detectd nearby the area ";
			try {
				_trenddao.insertTrend(false, false, false, true, false, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CROWD_SOUND_SCORE + CROWD_DENSITY_SCORE:
			alarm_serverity = Alarm.SEVERITY_ALERT;
			observation.behaviour = "Noisy crowd detectd. Crowd pressure over threshold";
			try {
				_trenddao.insertTrend(true, false, false, true, false, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CROWD_SOUND_SCORE + CROWD_DENSITY_SCORE + SOUND_LEVEL:
			alarm_serverity = Alarm.SEVERITY_ALERT;
			observation.behaviour = "Noisy crowd detectd. Crowd pressure over threshold";
			try {
				_trenddao.insertTrend(true, false, false, true, true, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CROWD_SOUND_SCORE + CROWD_DENSITY_SCORE + TEMPERATURE_LEVEL
				+ SOUND_LEVEL:
			alarm_serverity = Alarm.SEVERITY_CRITICAL;
			observation.behaviour = "Crowd pressure is over threshold. Crowd is noisy and temperature too high";
			try {
				_trenddao.insertTrend(true, true, false, true, true, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CROWD_SOUND_SCORE + CROWD_DENSITY_SCORE + TEMPERATURE_LEVEL:
			alarm_serverity = Alarm.SEVERITY_CRITICAL;
			observation.behaviour = "Crowd pressure is over threshold. Crowd is noisy and temperature too high";
			try {
				_trenddao.insertTrend(true, true, false, true, false, this.observation.activity, this.observation.id_location);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			alarm_serverity = Alarm.SEVERITY_INFO;
			observation.behaviour = "Nothing to be reported";
			break;

		}
		
		crowd_behaviour = observation.behaviour ;

	}

}
