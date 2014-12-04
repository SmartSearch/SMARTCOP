package eu.smart.cbdm.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.smart.cbdm.datafusion.Location;
import eu.smart.cdbm.dao.LocationsDAO;
@SessionScoped
@ManagedBean
public class LocationsBean {
	

	public List<Location> locations() {
		LocationsDAO _locations = new LocationsDAO();
		return _locations.getLocations();
	}



}
