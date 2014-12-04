package eu.smart.cdbm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.smart.cbdm.datafusion.DataFusionHandler;
import eu.smart.cbdm.datafusion.Location;
import eu.smart.cbdm.server.GetLocations;

public class LocationsDAO extends Dao {
	
	
	
	public Location getLocation(String loc_id){
		Location results = null;
		String query_locations = "SELECT loc_id, latitude, longitude, loc_name, loc_address FROM locations where loc_id=?";
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        try {

            conn = this.getConnection();
            stat = conn.prepareStatement(query_locations);
            stat.setString(1, loc_id);
            rs = stat.executeQuery();

            while (rs.next()) {
                Location _loc = new Location();
                _loc.id = rs.getString(1);
                _loc.latitude = rs.getDouble(2);
                _loc.longitude = rs.getDouble(3);
                _loc.name = rs.getString(4);
                _loc.address = rs.getString(5);
                results = _loc;

            }
        } catch (SQLException ex) {
            Logger.getLogger(GetLocations.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (Exception e) {
        	Logger.getLogger(GetLocations.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		} finally {
        	DBUtil.close(rs);
			DBUtil.close(stat);
			DBUtil.close(conn);
        }
		
		return results;
	}
	
	public List<Location> getLocations(){
		List<Location> results = new ArrayList();
		String query_locations = "SELECT loc_id, latitude, longitude, loc_name, loc_address FROM locations";
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        try {

            conn = this.getConnection();
            stat = conn.prepareStatement(query_locations);
            rs = stat.executeQuery();

            while (rs.next()) {
                Location _loc = new Location();
                _loc.id = rs.getString(1);
                _loc.latitude = rs.getDouble(2);
                _loc.longitude = rs.getDouble(3);
                _loc.name = rs.getString(4);
                _loc.address = rs.getString(5);
                results.add(_loc);

            }
        } catch (SQLException ex) {
            Logger.getLogger(GetLocations.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (Exception e) {
        	Logger.getLogger(GetLocations.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		} finally {
        	DBUtil.close(rs);
			DBUtil.close(stat);
			DBUtil.close(conn);
        }
		
		return results;
	}
	
	
	public void addLocation(Location _loc) {
		String insert_query = "INSERT INTO locations( loc_id, latitude, longitude, loc_name, loc_address) VALUES (?, ?, ?, ?, ?)";

		if (locationExists(_loc)) {
			return;
		}

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(insert_query);
			stat.setString(1, _loc.id);
			stat.setDouble(2, _loc.latitude);
			stat.setDouble(3, _loc.longitude);
			stat.setString(4, _loc.name);
			stat.setString(5, _loc.address);

			stat.executeUpdate();

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

			DBUtil.close(stat);
			DBUtil.close(conn);

		}

	}
	
	private boolean locationExists(Location _loc) {
		String checkquery = "SELECT loc_id FROM locations where loc_id=?";
		boolean exists = false;
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(checkquery);
			stat.setString(1, _loc.id);
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
