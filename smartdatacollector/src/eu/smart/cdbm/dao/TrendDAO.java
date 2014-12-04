package eu.smart.cdbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrendDAO extends Dao {

	public final static String CROWD_DENSITY_SCORE_ALARM = "CROWD DENSITY SCORE";
	public final static String TEMPERATURE_LEVEL = "TEMPERATURE LEVEL";
	public final static String TRAFFIC_SOUND_SCORE = "TRAFFIC SOUND SCOREE";
	public final static String CROWD_SOUND_SCORE = "CROWD SOUND SCORE";
	public final static String SOUND_LEVEL = "SOUND LEVEL";

	String sql_insert_trend = "INSERT INTO trend ( hour_of_day, day, day_of_week, "
			+ "month, year, locations_loc_id, CROWD_DENSITY_SCORE, "
			+ "TEMPERATURE_LEVEL, TRAFFIC_SOUND_SCORE, CROWD_SOUND_SCORE, "
			+ "SOUND_LEVEL) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	
	
	public List<WeeklyTrendByHour>weeklyTrendsByHour(String location_id) throws DBException {
		List<WeeklyTrendByHour> results = new ArrayList();
		for(int i=0; i<=23; i++){
			//results.add(weeklyTrendByHour(CROWD_DENSITY_SCORE_ALARM,i));
			//results.add(weeklyTrendByHour(TEMPERATURE_LEVEL,i));
			results.add(weeklyTrendByHour(TRAFFIC_SOUND_SCORE,i, location_id));
			//results.add(weeklyTrendByHour(CROWD_SOUND_SCORE,i));
			//results.add(weeklyTrendByHour(SOUND_LEVEL,i));
		}
		return results;
	}
	
	

	public WeeklyTrendByHour weeklyTrendByHour(String alarm, int hour, String lcation_id) throws DBException {
		WeeklyTrendByHour result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		switch (alarm) {
		case CROWD_DENSITY_SCORE_ALARM:
			sql = WeeklyTrendByHourQueries.sql_crowd_density_score;
			break;
		case TEMPERATURE_LEVEL:
			sql = WeeklyTrendByHourQueries.sql_temperature_level;
			break;
		case TRAFFIC_SOUND_SCORE:
			sql = WeeklyTrendByHourQueries.sql_traffic_sound_score;
			break;
		case CROWD_SOUND_SCORE:
			sql = WeeklyTrendByHourQueries.sql_crowd_sound_score;
			break;
		case SOUND_LEVEL:
			sql = WeeklyTrendByHourQueries.sql_sound_level;
			break;

		}
		
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, hour);
			stmt.setString(2, lcation_id);
			rs = stmt.executeQuery();
			if(rs.next()){
				result = new WeeklyTrendByHour();
				result.setAlarm(alarm);
				result.setHour(hour);
				int j=1;
				result.setMonday(rs.getInt(j++));
				result.setTuesday(rs.getInt(j++));
				result.setWednesday(rs.getInt(j++));
				result.setThursday(rs.getInt(j++));
				result.setFriday(rs.getInt(j++));
				result.setSaturday(rs.getInt(j++));
				result.setSunday(rs.getInt(j++));				
			}
			
			
		} catch (Exception _e) {
			log.error(_e);
			throw new DBException(_e);

		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}

		return result;
	}
	
	
	public List<WeeklyTrend>weeklyTrends(String location_id) throws DBException {
		List<WeeklyTrend> results = new ArrayList();
		results.add(weeklyTrend(CROWD_DENSITY_SCORE_ALARM,location_id));
		results.add(weeklyTrend(TEMPERATURE_LEVEL,location_id));
		results.add(weeklyTrend(TRAFFIC_SOUND_SCORE,location_id));
		results.add(weeklyTrend(CROWD_SOUND_SCORE,location_id));
		results.add(weeklyTrend(SOUND_LEVEL,location_id));
		return results;
	}
	

	public WeeklyTrend weeklyTrend(String alarm, String location_id) throws DBException {
		WeeklyTrend result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		switch (alarm) {
		case CROWD_DENSITY_SCORE_ALARM:
			sql = WeeklyTrendQueries.sql_crowd_density_score;
			break;
		case TEMPERATURE_LEVEL:
			sql = WeeklyTrendQueries.sql_temperature_level;
			break;
		case TRAFFIC_SOUND_SCORE:
			sql = WeeklyTrendQueries.sql_traffic_sound_score;
			break;
		case CROWD_SOUND_SCORE:
			sql = WeeklyTrendQueries.sql_crowd_sound_score;
			break;
		case SOUND_LEVEL:
			sql = WeeklyTrendQueries.sql_sound_level;
			break;

		}
		
		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, location_id);
			rs = stmt.executeQuery();
			if(rs.next()){
				result = new WeeklyTrend();
				result.setAlarm(alarm);
				int j=1;
				result.setMonday(rs.getInt(j++));
				result.setTuesday(rs.getInt(j++));
				result.setWednesday(rs.getInt(j++));
				result.setThursday(rs.getInt(j++));
				result.setFriday(rs.getInt(j++));
				result.setSaturday(rs.getInt(j++));
				result.setSunday(rs.getInt(j++));				
			}
			
			
		} catch (Exception _e) {
			log.error(_e);
			throw new DBException(_e);

		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}

		return result;
	}

	public void insertTrend(boolean CROWD_DENSITY_SCORE,
			boolean TEMPERATURE_LEVEL, boolean TRAFFIC_SOUND_SCORE,
			boolean CROWD_SOUND_SCORE, boolean SOUND_LEVEL, Date raised,
			String location_id) throws DBException {

		int year = raised.getYear() + 1900;
		int month = raised.getMonth() + 1;
		int day = raised.getDate();
		int day_of_week = raised.getDay(); // 0 is sunday
		int hour_of_day = raised.getHours();

		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = sql_insert_trend;

		try {
			conn = this.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, hour_of_day);
			stmt.setInt(2, day);
			stmt.setInt(3, day_of_week);
			stmt.setInt(4, month);
			stmt.setInt(5, year);
			stmt.setString(6, location_id);
			stmt.setBoolean(7, CROWD_DENSITY_SCORE);
			stmt.setBoolean(8, TEMPERATURE_LEVEL);
			stmt.setBoolean(9, TRAFFIC_SOUND_SCORE);
			stmt.setBoolean(10, CROWD_SOUND_SCORE);
			stmt.setBoolean(11, SOUND_LEVEL);

			stmt.executeUpdate();

		} catch (Exception _e) {
			log.error(_e);
			throw new DBException(_e);

		} finally {

			DBUtil.close(stmt);
			DBUtil.close(conn);
		}

	}

}
