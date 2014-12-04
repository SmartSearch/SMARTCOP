package eu.smart.smartcop.ui.gmap;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import eu.smart.cbdm.datafusion.Location;
import eu.smart.cdbm.dao.LocationsDAO;

@SessionScoped
@ManagedBean
public class MapBean {

	private double latitude;
	private double longitude;
	private int zoom = 13;
	private static final long serialVersionUID = 1L;
	private MapModel circleModel;
	private MapModel simpleModel;

	@PostConstruct
	public void init() {

		List<Location> locations = locations();
		latitude = locations.get(0).latitude;
		longitude = locations.get(0).longitude;

	}

	public MapModel getCircleModel() {
		return circleModel;
	}

	public void onCircleSelect(OverlaySelectEvent event) {
		Circle marker = (Circle) event.getOverlay();

		Location _location = (Location) marker.getData();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, _location
						.getName(), null));

	}

	public List<Location> locations() {
		LocationsDAO _locations = new LocationsDAO();
		return _locations.getLocations();
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void onStateChange(StateChangeEvent event) {
		LatLngBounds bounds = event.getBounds();
		zoom = event.getZoomLevel();

	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public MapModel getSimpleModel() {
		simpleModel = new DefaultMapModel();
		List<Location> _locations = this.locations();
		for (int i = 0; i < _locations.size(); i++) {
			Location element = _locations.get(i);
			LatLng coord = new LatLng(element.latitude, element.longitude);

			// Basic marker
			Marker _marker = new Marker(coord, element.name);
			_marker.setData(element);
			// simpleModel.addOverlay(_marker);

			Circle circle1 = new Circle(coord, 38.50*zoom);
			circle1.setStrokeColor("#d93c3c");
			
			
			circle1.setFillColor("#FFFF00");
			if(element.getId().equals("smart_0002"))
				circle1.setFillColor("green");
			circle1.setFillOpacity(0.5);
			circle1.setData(element);
			simpleModel.addOverlay(circle1);
		}
		return simpleModel;
	}

	public void setSimpleModel(MapModel simpleModel) {
		this.simpleModel = simpleModel;
	}

	public void setCircleModel(MapModel circleModel) {
		this.circleModel = circleModel;
	}

	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
