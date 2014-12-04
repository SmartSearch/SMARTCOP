package eu.smart.cdbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.smart.cbdm.datafusion.DataFusionHandler;
import eu.smart.cbdm.datafusion.Observation;

public class ObservationsDAO extends Dao {

	public int storeObservation(Observation _obs) {
		int stored = 0;

		String insert_query = "INSERT INTO observations( loc_id, crowd_score, plause_score, activity, sound_level, id_oservation,  traffic_score, music_score, crowd_density, media, temperature, behaviour) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		if (this.observationStored(_obs)) {
			return stored;
		}

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(insert_query);
			stat.setString(1, _obs.id_location);
			stat.setDouble(2, _obs.crowd_score);
			stat.setDouble(3, _obs.plause_score);
			stat.setTimestamp(4, _obs.activity);
			stat.setDouble(5, _obs.sound_level);
			stat.setString(6, _obs.id_observation);
			stat.setDouble(7, _obs.traffic_score);
			stat.setDouble(8, _obs.music_score);
			stat.setDouble(9, _obs.crowd_density);
			stat.setString(10, _obs.multimedia);
			stat.setDouble(11, _obs.temperature);
			stat.setString(12, _obs.behaviour);

			stat.executeUpdate();
			stored++;

			stat.close();
			stat = null;
			conn.close();
			conn = null;

		} catch (SQLException ex) {
			Logger.getLogger(DataFusionHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(DataFusionHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stat);
			DBUtil.close(conn);

		}

		return stored;

	}

	public Observation getObservation(String id) {
		String checkquery = "SELECT id_oservation, "
							+"loc_id, crowd_score, plause_score, activity, "
							+"sound_level, traffic_score, music_score, "
							+"crowd_density, media, temperature, behaviour FROM observations where id_oservation  =  ?";
		
		Observation result = null;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(checkquery);
			stat.setString(1, id);
			rs = stat.executeQuery();
			while(rs.next()){
				result = new Observation();
				result.setId_observation(rs.getString("id_oservation"));
				result.setActivity(rs.getTimestamp("activity"));
				result.setId_location(rs.getString("loc_id"));
				result.setCrowd_score(rs.getDouble("crowd_score"));
				result.setPlause_score(rs.getDouble("plause_score"));
				result.setSound_level(rs.getDouble("sound_level"));
				result.setTraffic_score(rs.getDouble("traffic_score"));
				result.setMusic_score(rs.getDouble("music_score"));
				result.setCrowd_density(rs.getDouble("crowd_density"));
				result.setMultimedia(rs.getString("media"));
				result.setTemperature(rs.getDouble("temperature"));
				result.setBehaviour(rs.getString("behaviour"));
				
			}


		} catch (SQLException ex) {
			Logger.getLogger(DataFusionHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stat);
			DBUtil.close(conn);

		}

		return result;

	}

	private boolean observationStored(Observation _obs) {
		String checkquery = "SELECT id_oservation FROM observations where id_oservation  =  ?";
		boolean exists = false;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(checkquery);
			stat.setString(1, _obs.id_observation);
			rs = stat.executeQuery();
			exists = rs.next();

			rs.close();
			rs = null;
			stat.close();
			stat = null;
			conn.close();
			conn = null;

		} catch (SQLException ex) {
			Logger.getLogger(DataFusionHandler.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stat);
			DBUtil.close(conn);

		}

		return exists;

	}

}
