/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.datafusion;

import eu.smart.cbdm.alarms.Alarm;
import eu.smart.cbdm.alarms.AlarmManager;
import eu.smart.cbdm.linkeddata.queries.SMARTCrowdRequest;
import eu.smart.cbdm.logs.LogManager;
import eu.smart.cbdm.logs.LogMessage;
import eu.smart.cdbm.dao.AlarmDAO;
import eu.smart.cdbm.dao.LocationsDAO;
import eu.smart.cdbm.dao.ObservationsDAO;
import eu.smart.cdbm.dao.TrendDAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author albert
 */
public class DataFusionHandler implements Runnable {

	private long _timeout = 30000;
	private boolean _is_active = true;
	private AlarmDAO _alarmdao = new AlarmDAO();
	private LocationsDAO _locationsdao = new LocationsDAO();
	private ObservationsDAO _observations = new ObservationsDAO();
	private TrendDAO _trenddao = new TrendDAO();

	public void run() {


		LogMessage _log = new LogMessage();
		_log.severity = LogMessage.SEVERITY_INFO;
		_log.message = "Data Collector Started";
		_log.description = "Data Collector is started";
		LogManager.getInstance().addLogMessage(_log);

		while (_is_active) {

			try {
				Thread.sleep(_timeout);
			} catch (InterruptedException ex) {
				// Logger.getLogger(DataFusionHandler.class.getName()).log(Level.SEVERE,
				// null, ex);
			}
			_log = new LogMessage();
			_log.severity = LogMessage.SEVERITY_INFO;
			_log.message = "Data Collector: "
					+ new SMARTCrowdRequest().toString();
			_log.description = "Data Collector is connecting to SMART to query for crowd";
			LogManager.getInstance().addLogMessage(_log);

			try {

				URL url = new URL(new SMARTCrowdRequest().toString());
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoOutput(true);
				connection.setInstanceFollowRedirects(false);
				connection.setRequestMethod("GET");
				// connection.setRequestProperty("Content-Type",
				// "application/json");

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(connection.getInputStream()))); // Getting the response
															// from the
															// webservice

				String output = null;
				String json_string = new String();
				// System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					// System.out.println(output); // Instead of this, you could
					// append all your response to a StringBuffer and use
					// `toString()` to get the entire JSON response as a String.
					// This string json response can be parsed using any json
					// library. Eg. GSON from Google.
					json_string = json_string + output;
				}
				// how do I get json object and print it as string
				JSONObject json_object = new JSONObject(json_string);

				JSONArray appo = json_object.getJSONArray("results");
				for (int i = 0; i < appo.length(); i++) {
					JSONObject _obj = appo.getJSONObject(i);
					Iterator _iter = _obj.keys();

					Location loc = new Location();

					loc.address = (String) _obj.get("locationAddress");
					loc.id = (String) _obj.get("locationId");
					loc.latitude = (Double) _obj.get("lat");
					loc.name = (String) _obj.get("locationName");
					loc.longitude = (Double) _obj.get("lon");
					_locationsdao.addLocation(loc);

					JSONObject observations = (JSONObject) _obj
							.get("observations");
					JSONObject latestobservations = (JSONObject) _obj
							.get("latestObservations");
					Observation _obs = new Observation();

					try {

						_obs.crowd_density = observations
								.getDouble("crowd_density");

						_obs.crowd_score = latestobservations
								.getDouble("crowd_score");
						_obs.plause_score = latestobservations
								.getDouble("applause_score");
						_obs.music_score = latestobservations
								.getDouble("music_score");
						_obs.sound_level = latestobservations
								.getDouble("sound_level");
						_obs.traffic_score = latestobservations
								.getDouble("traffic_score");
						try {
							_obs.temperature = latestobservations
									.getDouble("temperature");
						} catch (Exception e) {
							_obs.temperature = -1.0;
							_log = new LogMessage();
							_log.severity = LogMessage.SEVERITY_ERROR;
							_log.message = "Data Collector: "
									+ new SMARTCrowdRequest().toString();
							_log.description = "No temperature from "
									+ loc.name;
							LogManager.getInstance().addLogMessage(_log);
						}
						_obs.id_observation = (String) _obj.get("id");
						_obs.id_location = (String) _obj.get("locationId");
						_obs.multimedia = (String) _obj.get("media");

					} catch (Exception e) {
						continue;
					}
					String start_time = (String) _obj.get("startTime");
					String date = start_time.substring(0, 10);
					String time = start_time.substring(11, 19);
					String timezone = start_time.substring(20, 23);
					start_time = date + " " + time + " " + timezone;

					SimpleDateFormat format1 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss"); // first example
					// 2013-11-14T08:47:20.150Z
					try {
						Date d1 = format1.parse(start_time);
						_obs.activity = new java.sql.Timestamp(d1.getTime());

					} catch (ParseException ex) {
						Logger.getLogger(TestDateTime.class.getName()).log(
								Level.SEVERE, null, ex);
					}

					

					int stored = _observations.storeObservation(_obs);

					if (stored > 0) {
						DataAnalyzer _data = new DataAnalyzer(_obs);

						Alarm alarm = new Alarm();
						alarm.observation = _data.observation;
						alarm.location = loc;
						alarm.severity = _data.alarm_serverity;
						alarm.message = "New event detected in: " + loc.name
								+ " " + loc.address;
						alarm.description = _data.crowd_behaviour;
						alarm.score = _data.score;
						alarm.plauseDetected = _data.plauseDetected;
						alarm.mayBeMusic = _data.mayBeMusic;
						System.out.println(alarm);
						AlarmManager.getInstance().addAlarm(alarm);
						_alarmdao.storeAlarm(alarm);
						

					}

				}

				connection.getResponseCode();
				connection.disconnect();

			} catch (Exception e) {
				Logger.getLogger(DataFusionHandler.class.getName()).log(
						Level.SEVERE, null, e);
			}

		}
	}

	public void setActive(boolean active) {

		this._is_active = false;

	}






}
