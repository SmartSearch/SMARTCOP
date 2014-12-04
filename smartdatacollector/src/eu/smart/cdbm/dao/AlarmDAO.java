package eu.smart.cdbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.smart.cbdm.alarms.Alarm;
import eu.smart.cbdm.datafusion.DataFusionHandler;

public class AlarmDAO extends Dao {
	
	
	public List<Alarm> getAlarmsByLocation(String location_id, int rows) {
		List<Alarm> results = new ArrayList();
		if(location_id==null) return results;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		LocationsDAO _loc_dao = new LocationsDAO();
		ObservationsDAO _obs_dao = new ObservationsDAO();

		String sql = "SELECT id, severity, severity_types_id, locations_loc_id, observations_id_oservation, "
				+ "messagge, raised, description, scores FROM smartcop.alarms where locations_loc_id=? order by id desc LIMIT ?";
		
		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(sql);
			stat.setString(1, location_id);
			stat.setInt(2, rows);
			
			rs = stat.executeQuery();
			while(rs.next()){
				Alarm _alarm = new Alarm();
				_alarm.setRaised(rs.getDate("raised"));
				_alarm.setSeverity(rs.getString("severity"));
				_alarm.setDescription(rs.getString("description"));
				_alarm.setId(rs.getInt("id"));
				_alarm.setLocation(_loc_dao.getLocation(rs.getString("locations_loc_id")));
				_alarm.setMessage(rs.getString("messagge"));
				_alarm.setScore(rs.getInt("scores"));
				_alarm.setObservation(_obs_dao.getObservation(rs.getString("observations_id_oservation")));
				results.add(_alarm);
				
			}


		} catch (SQLException ex) {
			Logger.getLogger(DataFusionHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(stat);
			DBUtil.close(conn);
		}

		return results;
	}
	

	public List<Alarm> getAlarms(int list_size) {
		List<Alarm> results = new ArrayList();
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		LocationsDAO _loc_dao = new LocationsDAO();
		ObservationsDAO _obs_dao = new ObservationsDAO();

		String sql = "SELECT id, severity, severity_types_id, locations_loc_id, observations_id_oservation, "
				+ "messagge, raised, description, scores FROM smartcop.alarms order by id desc LIMIT ?";
		
		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(sql);
			stat.setInt(1, list_size);
			rs = stat.executeQuery();
			while(rs.next()){
				Alarm _alarm = new Alarm();
				_alarm.setRaised(rs.getDate("raised"));
				_alarm.setSeverity(rs.getString("severity"));
				_alarm.setDescription(rs.getString("description"));
				_alarm.setId(rs.getInt("id"));
				_alarm.setLocation(_loc_dao.getLocation(rs.getString("locations_loc_id")));
				_alarm.setMessage(rs.getString("messagge"));
				_alarm.setScore(rs.getInt("scores"));
				_alarm.setObservation(_obs_dao.getObservation(rs.getString("observations_id_oservation")));
				results.add(_alarm);
				
			}


		} catch (SQLException ex) {
			Logger.getLogger(DataFusionHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(stat);
			DBUtil.close(conn);
		}

		return results;
	}

	public void storeAlarm(Alarm _alarm) {
		String sql = "INSERT INTO alarms "
				+ "( severity, severity_types_id, locations_loc_id, "
				+ "observations_id_oservation, messagge, raised, "
				+ "description, scores) VALUES (?,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(sql);
			stat.setString(1, _alarm.severity);
			stat.setString(2, _alarm.severity);
			stat.setString(3, _alarm.location.id);
			stat.setString(4, _alarm.observation.id_observation);
			stat.setString(5, _alarm.message);
			stat.setDate(6, new java.sql.Date(_alarm.raised.getTime()));
			stat.setString(7, _alarm.description);
			stat.setInt(8, _alarm.score);
			stat.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DataFusionHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(stat);
			DBUtil.close(conn);
		}

	}

}
